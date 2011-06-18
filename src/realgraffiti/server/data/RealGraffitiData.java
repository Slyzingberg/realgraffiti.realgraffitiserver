package realgraffiti.server.data;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Key;

import javax.jdo.Query;

import realgraffiti.common.dto.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RealGraffitiData implements realgraffiti.common.data.RealGraffitiData {
	
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
}
