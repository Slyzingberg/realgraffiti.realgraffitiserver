package realgraffiti.server.data;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Key;

import javax.jdo.Query;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import realgraffiti.common.dataObjects.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RealGraffitiDataStore implements realgraffiti.common.data.RealGraffitiData {
	
	public boolean addNewGraffiti(Graffiti Graffiti) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

        try {
            pm.makePersistent(Graffiti);
        } finally {
            pm.close();
        }
        
        return true;
	}
	
	public Collection<Graffiti> getNearByGraffiti(GraffitiLocationParameters graffitiLocationParameters){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Graffiti.class);

	    List<Graffiti> results;
	    try {
	        results = (List<Graffiti>)query.execute();
	    } finally {
	        query.closeAll();
	    }
	    
	    return results;
	}

	@Override
	public byte[] getGraffitiImage(Long graffitiKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(Graffiti.class);
		query.setFilter("_key == key");
		query.declareParameters("Long key");
		
		List<Graffiti> graffities = (List<Graffiti>)query.execute(graffitiKey);
		
		if(graffities.size() == 0){
			throw new IllegalArgumentException("Graffiti key not found: " + graffitiKey);
		}
		
		Graffiti graffiti = graffities.get(0);
		
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		byte[] imageData = blobstoreService.fetchData(new BlobKey(graffiti.getImageKey().toString()), 0, BlobstoreService.MAX_BLOB_FETCH_SIZE - 1);
		
		return imageData;
	}
}
