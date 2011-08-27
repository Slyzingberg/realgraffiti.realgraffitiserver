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
	@Persistent
	private Blob _wallImageData;
	
	public PersistentGraffiti(Graffiti commonGraffiti){
		_key = commonGraffiti.getKey();
		_graffitiLocationParameters = commonGraffiti.getLocationParameters();
		_imageData = new Blob(commonGraffiti.getImageData());
		_wallImageData = new Blob(commonGraffiti.getWallImageData());
	}
	
	public Graffiti toCommonGraffiti(){
		byte[] imageData = null;
		byte[] wallImageData = null;
		
		if(_imageData != null)
			imageData = _imageData.getBytes();
		
		if(_wallImageData != null)
			wallImageData = _wallImageData.getBytes();
				
		Graffiti commonGraffiti = new Graffiti(_graffitiLocationParameters, imageData, wallImageData);		
		commonGraffiti.setKey(_key);
		return commonGraffiti;
	}
}