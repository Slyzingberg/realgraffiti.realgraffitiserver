package realgraffiti.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;


public class ServerInfo extends HttpServlet {
	public final String GET_UPLOAD_URL_ACTION = "getUploadUrl";
	public final String ADD_GRAFFITI_SERVLET_PATH = "RealGraffitiDataServlet";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String action = req.getParameter("action");
		
		if(action.equals(GET_UPLOAD_URL_ACTION)){
			String uploadUrl = getUploadUrl();
			resp.getWriter().write(uploadUrl);
		}
	}

	private String getUploadUrl() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		String uploadUrl = blobstoreService.createUploadUrl(ADD_GRAFFITI_SERVLET_PATH);
		
		return uploadUrl;
	}
}

