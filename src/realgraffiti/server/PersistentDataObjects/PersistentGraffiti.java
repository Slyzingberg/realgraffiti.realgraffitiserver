package realgraffiti.server.PersistentDataObjects;
import javax.jdo.annotations.*;

import realgraffiti.common.dataObjects.Graffiti;
import realgraffiti.common.dataObjects.GraffitiLocationParameters;

import com.google.appengine.api.datastore.Blob;

/**
 * A Persistent capable class duplicate of the realgraffiti.common.dataObjects.Graffiti.
 * The _imageData field in the regular Graffiti object is of type byte[] which can not be persisted as we want.
 */
@PersistenceCapable
public class PersistentGraffiti {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long _key;
	
	@Persistent
	private GraffitiLocationParameters _graffitiLocationParameters;
	
	@Persistent
	private Blob _imageData;

	public PersistentGraffiti(Graffiti commonGraffiti){
		_key = commonGraffiti.getKey();
		_graffitiLocationParameters = commonGraffiti.getLocationParameters();
		_imageData = new Blob(commonGraffiti.getImageData());
	}
	
	public Graffiti toCommonGraffiti(){
		Graffiti commonGraffiti = new Graffiti(_graffitiLocationParameters, _imageData.getBytes());
		
		commonGraffiti.setKey(_key);
		
		return commonGraffiti;
	}
}