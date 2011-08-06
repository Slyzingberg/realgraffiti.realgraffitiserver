package realgraffiti.server.servlets;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realgraffiti.common.data.JsonConverter;



public class ServletHelper {
	public static String extractParameter(HttpServletRequest request, String paramName){
		
		String paramJson = request.getParameter(paramName);

		String param = (String)JsonConverter.fromJson(paramJson, String.class);
		
		return param;
	}
	
	public static Object extractParameter(HttpServletRequest request, String paramName, Type type){
		String paramJson = request.getParameter(paramName);

		Object param = JsonConverter.fromJson(paramJson, type);
		
		return param;
	}

	public static String SerializeParameter(
			Object object) {	
	
		return JsonConverter.toJson(object);
	}

	public static void setResponseObject(HttpServletResponse resp,
			Object object) throws IOException {
		String json = JsonConverter.toJson(object);
		
		resp.getWriter().write(json);
	}
	
}
