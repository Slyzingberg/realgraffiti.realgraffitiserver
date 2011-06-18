package realgraffiti.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class RealGraffitiDataServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.getWriter().write("this is get");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.getWriter().write("this is post");
	}
	
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.getWriter().write("this is put");
	}
}
