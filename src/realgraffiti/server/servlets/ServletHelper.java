package realgraffiti.server.servlets;

import java.lang.reflect.Type;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import realgraffiti.common.dto.GraffitiDto;

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
	
}
