package realgraffiti.server.servlets;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realgraffiti.common.data.RealGraffitiData;
import realgraffiti.common.dataObjects.Graffiti;
import realgraffiti.common.dataObjects.GraffitiLocationParameters;
import realgraffiti.server.data.RealGraffitiDataStore;


public class DataViewer extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.getWriter().write("<html><head></head><body>");
		RealGraffitiData data = new RealGraffitiDataStore();
		GraffitiLocationParameters graffitiLocationParameters = null;
		Collection<Graffiti> nearByGraffities = data.getNearByGraffiti(graffitiLocationParameters);
		
		for(Graffiti graffiti : nearByGraffities){
			String row = "Key: " + graffiti.getKey() + " ";
			GraffitiLocationParameters glp =  graffiti.getLocationParameters();
			if(glp == null)
				row += "location parameters null!";
			else{
				row += " Coor: ( " + glp.getCoordinates().getLatitude() + ", " + glp.getCoordinates().getLongitude() + " )";
				row += " image data: " + convertToString(graffiti.getImageData()) + " wall image: " + convertToString(graffiti.getWallImageData()) + "</br>"; 
					
			}
			
			resp.getWriter().write(row + "<br/>");
			
		}
		
		resp.getWriter().write("</body></html>");
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
