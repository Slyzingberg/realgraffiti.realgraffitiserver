package realgraffiti.server.servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realgraffiti.common.data.RealGraffitiData;
import realgraffiti.common.dto.GraffitiDto;
import realgraffiti.server.data.RealGraffitiDataStore;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class RealGraffitiDataServlet extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.getWriter().write("this is get");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("file0");
        
        GraffitiDto graffiti = getGraffitiDto(req);
        graffiti.setImage(blobKey.getKeyString());
        RealGraffitiData data = new RealGraffitiDataStore();
        
        data.addNewGraffiti(graffiti);
        if (blobKey == null) {
        	resp.sendRedirect("/ServerInfo?action=error");
        } else {
        	resp.sendRedirect("/ServerInfo?action=blobkey&blobkey=" + URLEncoder.encode(blobKey.getKeyString(),"UTF-8"));
        }

	}
	
	private GraffitiDto getGraffitiDto(HttpServletRequest req) {
		Gson gson = new Gson();
		String json = req.getParameter("object");
		GraffitiDto graffiti = gson.fromJson(json, GraffitiDto.class);
		
		return graffiti;
	}

	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.getWriter().write("this is put");
	}
}
