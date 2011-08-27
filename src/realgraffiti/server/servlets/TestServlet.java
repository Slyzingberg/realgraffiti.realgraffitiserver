package realgraffiti.server.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.*;

import realgraffiti.common.dataObjects.*;
import realgraffiti.server.data.RealGraffitiDataStore;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//resp.setContentType("text/plain");
		
		resp.getWriter().println("<html><head></head><body>");
		
		resp.getWriter().println("Real graffiti test page <br/>");
		RealGraffitiDataStore graffitiData = new RealGraffitiDataStore();
		
		List siftDescriptors = new ArrayList();
		siftDescriptors.add(1);
		siftDescriptors.add(2);
		siftDescriptors.add(3);
		
		Coordinates coordinates = new Coordinates(123, 321);
		Orientation orienation = new Orientation(new float[]{1,2,3});
		GraffitiLocationParameters lp = new GraffitiLocationParameters(coordinates, orienation);
		lp.setOrientation(new Orientation(new float[]{1,3,4}));
		byte[] imageData = new byte[] {
				1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,
				1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5
		};
		Graffiti graffiti = new Graffiti(lp, imageData);
		graffitiData.addNewGraffiti(graffiti);
		
		Collection<Graffiti> graffities = graffitiData.getNearByGraffiti(lp);
		for(Graffiti g: graffities){
			resp.getWriter().write("key: " + g.getKey() + " imageData: " + convertToString(g.getImageData()) + "<br/>") ;
		}
		resp.getWriter().println("</body></html>");
	}
	
	private String convertToString(byte[] array){
		if(array == null)
			return "null";
		
		String output = ""; 
			
		for(byte b : array){
			output += b + ",";
		}
		
		return output;
	}
}
