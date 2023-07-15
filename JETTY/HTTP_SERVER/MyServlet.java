package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;


public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

		System.out.println("doGet : " + req.getRequestURI().toString());
		
		String url = req.getScheme()+"://"+req.getRemoteAddr()+":"+req.getLocalPort()+req.getRequestURI();
		
		//1. Header 모두 출력
		Enumeration<String> headerNames = req.getHeaderNames();
	
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = req.getHeader(headerName);
			System.out.println(headerName + " : " +   headerValue);
			
			if(headerName.toLowerCase().equals("x-api-key")) {
				String key = req.getHeader(headerName);
			}
		}
		
		
		
		Gson gson = new Gson();
		JsonObject object = new JsonObject();
		object.addProperty("name", "park");
		object.addProperty("age", 20);
		object.addProperty("success", true);
		String json = gson.toJson(object);
		System.out.println(json);

		String result = "Ok";
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("Result", result);
		String jsonStr = gson.toJson(resultMap);

		res.setStatus(200);
		res.getWriter().write(jsonStr);
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("doPost : " + req.getRequestURI().toString());
		
		String uri = req.getRequestURI().toString();
		
		BufferedReader input = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String buffer;
		StringBuilder sb = new StringBuilder();
		while((buffer = input.readLine())!=null) {
			sb.append(buffer + "\n");
		}
		System.out.println(sb.toString());
		input.close();	
		
		String body = sb.toString();
		
		JsonObject jsonObject = new JsonObject();
		
		if(body.isEmpty()== false) {
			if(body != null && body.trim().equals("null")==false) {
				JsonElement jsonElement = JsonParser.parseString(body);
				jsonObject = jsonElement.getAsJsonObject();
			}
		}
		
		String message = jsonObject.get("Message").getAsString();		
		
		res.setStatus(200);
		res.getWriter().write("Hello!");
	}	
	
}
