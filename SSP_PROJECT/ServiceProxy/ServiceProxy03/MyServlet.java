package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		System.out.println("doGet : " + req.getRequestURI().toString());
		
		String url = "";
		String uri = req.getRequestURI().toString();
		String param = req.getQueryString();
		
		Map<String, String> map = (Map<String, String>)getServletContext().getAttribute("map");
		
		String path = uri.split("/")[1];
		if(param != null) {
			url = map.get("/"+path)+uri+"?"+param;
		}else {
			url = map.get("/"+path)+uri;
		}
		
		//url : http://127.0.0.1:8081/front
		
		HttpClient httpClient = new HttpClient();
		
		try {
			httpClient.start();
			ContentResponse contentRes = httpClient.newRequest(url).method(HttpMethod.GET).send();
			
			res.setStatus(200);
			res.getWriter().write(contentRes.getContentAsString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("doPost : " + req.getRequestURI().toString());
		
		String url = "";
		String uri = req.getRequestURI().toString();
		
		Map<String, String> map = (Map<String, String>)getServletContext().getAttribute("map");
		
		url = map.get(uri)+uri;
		
		BufferedReader input = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String buffer;
		StringBuilder sb = new StringBuilder();
		while((buffer = input.readLine())!=null) {
			sb.append(buffer + "\n");
		}
//		System.out.println(sb.toString());
		input.close();	
		
		HttpClient httpClient = new HttpClient();
		
		try {
			httpClient.start();
			Request request = httpClient.POST(url);
			request.header(HttpHeader.CONTENT_TYPE, req.getContentType());
			String json = sb.toString();
			request.content(new StringContentProvider(json, "utf-8"));
			
			ContentResponse contentResPost = request.send();
			
			res.setStatus(200);
			res.getWriter().write(contentResPost.getContentAsString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.setStatus(200);
		
	}
}
