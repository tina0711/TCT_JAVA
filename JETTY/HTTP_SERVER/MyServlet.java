package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;


public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		res.setStatus(200);
		res.getWriter().write("Hello!");
		
//		Gson gson = new Gson();
//		JsonObject object = new JsonObject();
//		object.addProperty("name", "park");
//		object.addProperty("age", 20);
//		object.addProperty("success", true);
//		String json = gson.toJson(object);
//		System.out.println(json);
		
	}
}
