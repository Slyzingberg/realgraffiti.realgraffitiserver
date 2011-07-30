package realgraffiti.server.data;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Key;

import javax.jdo.Query;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import realgraffiti.common.data.RealGraffitiData;
import realgraffiti.common.dataObjects.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RealGraffitiDataStore implements RealGraffitiData {
	
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
		return null;
	}
}
