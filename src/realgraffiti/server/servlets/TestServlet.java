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
		resp.setContentType("text/plain");
		resp.getWriter().println("Real graffiti test page <br/>");
		RealGraffitiDataStore graffitiData = new RealGraffitiDataStore();
		
		List siftDescriptors = new ArrayList();
		siftDescriptors.add(1);
		siftDescriptors.add(2);
		siftDescriptors.add(3);
		
		Coordinates coordinates = new Coordinates(123, 321);
		
		GraffitiLocationParameters lp = new GraffitiLocationParameters(coordinates, 34, siftDescriptors);
		Graffiti graffiti = new Graffiti(lp, "image key");
		graffitiData.addNewGraffiti(graffiti);
		
		Collection<Graffiti> graffities = graffitiData.getNearByGraffiti(lp);
		for(Graffiti g: graffities){
			resp.getWriter().write("key: " + g.getKey() + "imageKey: " + g.getImageKey() + "<br/>") ;
		}
	}
}
