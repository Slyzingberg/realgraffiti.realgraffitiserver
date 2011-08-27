package realgraffiti.server.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realgraffiti.common.data.RealGraffitiData;
import realgraffiti.common.dataObjects.*;
import realgraffiti.server.data.RealGraffitiDataStore;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;


@SuppressWarnings("serial")
public class RealGraffitiDataServlet extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private final String ACTION_KEY = "action";
	private final String ACTION_PARAMETER_KEY = "object";
	
	private final String ADD_GRAFFITI_KEY = "addGraffiti";
	private final String GET_NEARBY_GRAFFITI_KEY = "getNearByGraffiti";
	private final String GET_GRAFFITI_IMAGE_KEY = "getGraffitiImageKey";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.getWriter().write("this is get");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String action = ServletHelper.extractParameter(req, ACTION_KEY);
		
		
		if(action.equals(ADD_GRAFFITI_KEY))
			addNewGraffiti(req, resp);
		else if(action.equals(GET_NEARBY_GRAFFITI_KEY))
			getNearByGraffiti(req, resp);
		else if(action.equals(GET_GRAFFITI_IMAGE_KEY))
			getGraffitiImage(req, resp);
		else
			throw new IllegalArgumentException("Illegal action: " + action);
	}

	private void getNearByGraffiti(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		RealGraffitiData data = new RealGraffitiDataStore();
		GraffitiLocationParameters graffitiLocationParameters = 
			(GraffitiLocationParameters)ServletHelper.extractParameter(
					req, ACTION_PARAMETER_KEY, GraffitiLocationParameters.class);
		
		Collection<Graffiti> nearByGraffities = data.getNearByGraffiti(graffitiLocationParameters);
		
		ServletHelper.setResponseObject(resp, nearByGraffities);
	}
	
	private void getGraffitiImage(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		RealGraffitiData data = new RealGraffitiDataStore();
		Long graffitiKey = (Long)ServletHelper.extractParameter(req, ACTION_PARAMETER_KEY, Long.class);
		
		byte[] imageData = data.getGraffitiImage(graffitiKey);
		ServletHelper.setResponseObject(resp, imageData);
	}

	private void addNewGraffiti(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, UnsupportedEncodingException {       
        Graffiti graffiti = (Graffiti) ServletHelper.extractParameter(req, "object", Graffiti.class);
        RealGraffitiData data = new RealGraffitiDataStore();
        
        data.addNewGraffiti(graffiti);
	}
	

	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.getWriter().write("this is put");
	}
}
