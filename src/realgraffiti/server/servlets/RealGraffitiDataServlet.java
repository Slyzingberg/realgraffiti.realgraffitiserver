package realgraffiti.server.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realgraffiti.common.data.RealGraffitiData;
import realgraffiti.common.dto.GraffitiDto;
import realgraffiti.common.dto.GraffitiLocationParametersDto;
import realgraffiti.server.data.RealGraffitiDataStore;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gson.Gson;

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
		GraffitiLocationParametersDto graffitiLocationParameters = 
			(GraffitiLocationParametersDto)ServletHelper.extractParameter(
					req, ACTION_PARAMETER_KEY, GraffitiLocationParametersDto.class);
		
		Collection<GraffitiDto> nearByGraffities = data.getNearByGraffiti(graffitiLocationParameters);
		
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
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("file0");
        
        GraffitiDto graffiti = (GraffitiDto) ServletHelper.extractParameter(req, "object", GraffitiDto.class);
        graffiti.setImage(blobKey.getKeyString());
        RealGraffitiData data = new RealGraffitiDataStore();
        
        data.addNewGraffiti(graffiti);
        
        if (blobKey == null) {
        	resp.sendRedirect("/ServerInfo?action=error");
        } else {
        	resp.sendRedirect("/ServerInfo?action=blobkey&blobkey=" + URLEncoder.encode(blobKey.getKeyString(),"UTF-8"));
        }
	}
	

	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.getWriter().write("this is put");
	}
}
