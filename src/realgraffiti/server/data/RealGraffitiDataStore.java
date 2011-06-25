package realgraffiti.server.data;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Key;

import javax.jdo.Query;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import realgraffiti.common.dto.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RealGraffitiDataStore implements realgraffiti.common.data.RealGraffitiData {
	
	public boolean addNewGraffiti(GraffitiDto GraffitiDto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

        try {
            pm.makePersistent(GraffitiDto);
        } finally {
            pm.close();
        }
        
        return true;
	}
	
	public Collection<GraffitiDto> getNearByGraffiti(GraffitiLocationParametersDto graffitiLocationParameters){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(GraffitiDto.class);

	    List<GraffitiDto> results;
	    try {
	        results = (List<GraffitiDto>)query.execute();
	    } finally {
	        query.closeAll();
	    }
	    
	    return results;
	}

	@Override
	public byte[] getGraffitiImage(Long graffitiKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(GraffitiDto.class);
		query.setFilter("_key == key");
		query.declareParameters("Long key");
		
		List<GraffitiDto> graffities = (List<GraffitiDto>)query.execute(graffitiKey);
		
		if(graffities.size() == 0){
			throw new IllegalArgumentException("Graffiti key not found: " + graffitiKey);
		}
		
		GraffitiDto graffiti = graffities.get(0);
		
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		byte[] imageData = blobstoreService.fetchData(new BlobKey(graffiti.getImageKey().toString()), 0, BlobstoreService.MAX_BLOB_FETCH_SIZE - 1);
		
		return imageData;
	}
}
