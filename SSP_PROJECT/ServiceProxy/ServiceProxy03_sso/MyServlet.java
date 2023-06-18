package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.*;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;


public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private JsonArray jsonRoutes;
	private MyHttpClient myClient;
	private String proxyName;
	@Override
	public void init() throws ServletException{
		this.jsonRoutes = (JsonArray)getServletContext().getAttribute("proxy.routes");
		this.proxyName = (String)getServletContext().getAttribute("proxy.name");
		this.myClient = new MyHttpClient();
		System.out.println(this.jsonRoutes);
	}
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		System.out.println("doGet : " + req.getRequestURI().toString());
		res.setStatus(200);
		
		if(!req.getRequestURI().contains("favicon.ico")) {
			for(JsonElement route : jsonRoutes) {
				String path = route.getAsJsonObject().get("pathPrefix").getAsString();
				if(req.getRequestURI().startsWith(path)) {
					String url = route.getAsJsonObject().get("url").getAsString();
					String redirectUrl = url+req.getRequestURI();
					String query = req.getQueryString();
					if(query!=null) {
						redirectUrl = redirectUrl+"?" + query;
					}
					String resContents = myClient.sendRequest(redirectUrl, "GET", null);
					System.out.println(resContents);
					res.getWriter().write(resContents);
					break;
				}
			}
		}
	
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("doPost : " + req.getRequestURI().toString());
		res.setStatus(200);
		
		for(JsonElement route : jsonRoutes) {
			String path = route.getAsJsonObject().get("pathPrefix").getAsString();
			if(req.getRequestURI().startsWith(path)) {
				String url = route.getAsJsonObject().get("url").getAsString();
				String redirectUrl = url+path;
				System.out.println(redirectUrl);
				StringBuilder buffer = new StringBuilder();
				BufferedReader reader = req.getReader();
				String line;
				while((line = reader.readLine()) != null) {
					buffer.append(line);
					buffer.append(System.lineSeparator());
				}
				String data = buffer.toString();
				
				String resContents = myClient.sendRequest(redirectUrl, "POST", data);
				System.out.println(resContents);
				res.getWriter().write(resContents);
				break;
			}
		}
	}
}
