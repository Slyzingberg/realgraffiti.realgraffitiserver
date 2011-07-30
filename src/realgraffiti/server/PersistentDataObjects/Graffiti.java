package realgraffiti.server.PersistentDataObjects;
import javax.jdo.annotations.*;
import realgraffiti.common.dataObjects.GraffitiLocationParameters;

import com.google.appengine.api.datastore.Blob;


@PersistenceCapable
public class Graffiti {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long _key;
	
	@Persistent
	private GraffitiLocationParameters _graffitiLocationParameters;
	
	@Persistent
	private Blob _imageData;

	public Graffiti(){
	
	}
	
	public Graffiti(GraffitiLocationParameters graffitiLocationParameters, String imageKey) {
		_graffitiLocationParameters = graffitiLocationParameters;
	}
	
	public Graffiti(GraffitiLocationParameters graffitiLocationParameters, String imageKey, byte[] imageData) {
		_graffitiLocationParameters = graffitiLocationParameters;
	}
	
	public Graffiti(GraffitiLocationParameters graffitiLocationParameters) {
		_graffitiLocationParameters = graffitiLocationParameters;
	}

	public GraffitiLocationParameters getLocationParameters() {
		return _graffitiLocationParameters;
	}

	public void setLocationParameters(GraffitiLocationParameters descriptorsVector) {
		this._graffitiLocationParameters = descriptorsVector;
	}

	public void setKey(Long key){
		_key = key;
	}
	
	public Long getKey() {
		return _key;
	}
	
	public Blob get_imageData() {
		return _imageData;
	}

	public void set_imageData(Blob _imageData) {
		this._imageData = _imageData;
	}
	
	public String toString(){
		return getKey().toString();
	}
	
	public boolean equals(Graffiti other){
		return _key == other._key;
	}
}
