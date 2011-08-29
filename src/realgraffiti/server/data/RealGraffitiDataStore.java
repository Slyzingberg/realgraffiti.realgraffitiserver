package realgraffiti.server.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import realgraffiti.common.data.RealGraffitiData;
import realgraffiti.common.dataObjects.*;
import realgraffiti.server.PersistentDataObjects.PersistentGraffiti;

public class RealGraffitiDataStore implements RealGraffitiData {
	
	public boolean addNewGraffiti(Graffiti graffiti) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		PersistentGraffiti persistantGraffiti = new	PersistentGraffiti(graffiti);
			
        try {
            pm.makePersistent(persistantGraffiti);
        } finally {
            pm.close();
        }
        
        return true;
	}
	
	public Collection<Graffiti> getNearByGraffiti(GraffitiLocationParameters graffitiLocationParameters, int rangeInMeters){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(PersistentGraffiti.class);

	    List<PersistentGraffiti> results;
	    try {
	        results = (List<PersistentGraffiti>)query.execute();
	    } finally {
	        query.closeAll();
	    }
	    
	    List<Graffiti> commonGraffitiResult = convertToCommonGraffitiList(results);
	    
	    return commonGraffitiResult;
	}

	private List<Graffiti> convertToCommonGraffitiList(
			List<PersistentGraffiti> results) {
		
		List<Graffiti> commonGraffitiResult = new ArrayList<Graffiti>();
		
	    for(PersistentGraffiti persistentGraffiti : results){
	    	Graffiti commonGraffiti = persistentGraffiti.toCommonGraffiti();
	    	commonGraffitiResult.add(commonGraffiti);
	    }
		return commonGraffitiResult;
	}

	@Override
	public byte[] getGraffitiImage(Long graffitiKey) {
		Graffiti graffiti = getGraffitiByKey(graffitiKey);
		
		return graffiti.getImageData();
	}

	@Override
	public byte[] getGraffitiWallImage(Long graffitiKey) {
		Graffiti graffiti = getGraffitiByKey(graffitiKey);
		
		return graffiti.getWallImageData();
	}
	
	private Graffiti getGraffitiByKey(Long graffitiKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(PersistentGraffiti.class);
		query.setFilter("_key == key");
		query.declareParameters("Long key");
		
		List<PersistentGraffiti> graffities = (List<PersistentGraffiti>)query.execute(graffitiKey);
		
		if(graffities.size() == 0){
			throw new IllegalArgumentException("Graffiti key not found: " + graffitiKey);
		}
		
		PersistentGraffiti graffiti = (PersistentGraffiti)graffities.get(0);
		return graffiti.toCommonGraffiti();
	}	

}
