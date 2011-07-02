package realgraffiti.server.servlets;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ServletHelper {
	public static String extractParameter(HttpServletRequest request, String paramName){
		Gson gson = new Gson();
		String paramJson = request.getParameter(paramName);

		String param = gson.fromJson(paramJson, String.class);
		
		return param;
	}
	
	public static Object extractParameter(HttpServletRequest request, String paramName, Type type){
		Gson gson = new Gson();
		String paramJson = request.getParameter(paramName);

		Object param = gson.fromJson(paramJson, type);
		
		return param;
	}

	public static String SerializeParameter(
			Object object) {
		
		Gson gson = new Gson();
		
		return gson.toJson(object);
		
	}

	public static void setResponseObject(HttpServletResponse resp,
			Object object) throws IOException {
		
		Gson gson = new Gson();
		String json = gson.toJson(object);
		
		resp.getWriter().write(json);
	}
	
}
