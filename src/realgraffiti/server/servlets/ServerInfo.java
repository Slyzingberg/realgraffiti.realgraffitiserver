package realgraffiti.server.servlets;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;


public class ServerInfo extends HttpServlet {
	public final String GET_UPLOAD_URL_ACTION = "getUploadUrl";
	public final String RETURN_ERROR_ACTION = "error";
	public final String SHOW_BLOB_KEY_ACION = "blobkey";
	public final String ADD_GRAFFITI_SERVLET_PATH = "/RealGraffitiData";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		
		//String action = req.getParameter("action");
		String action = ServletHelper.extractParameter(req, "action");
		if(action == null){
			resp.getWriter().write("no action was givven");
			return;
		}

		if(action.equals(GET_UPLOAD_URL_ACTION)){
			String uploadUrl = getUploadUrl();
			resp.getWriter().write(uploadUrl);
		} else if(action.equals(RETURN_ERROR_ACTION)){
			resp.getWriter().write("Upload error");
		} else if(action.equals(SHOW_BLOB_KEY_ACION)){
			String blobkey = req.getParameter("blobkey");
			resp.getWriter().write(blobkey);
		}
	}

	private String getUploadUrl() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		String uploadUrl = blobstoreService.createUploadUrl(ADD_GRAFFITI_SERVLET_PATH);
		
		return uploadUrl;
	}
}

