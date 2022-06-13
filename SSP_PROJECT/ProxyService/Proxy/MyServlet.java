package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class MyServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		System.out.println("-----------GET----------------");
		
		String prefix = "";
		String url = "";
		String uri = req.getRequestURI().toString();
		String param = req.getQueryString();
		
		//MyClient client = new MyClient();
		
		String configStr = (String)getServletContext().getAttribute("config");
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(configStr);
		JsonArray jsonArray = element.getAsJsonObject().get("roots").getAsJsonArray();
		
		for(int i=0; i<jsonArray.size(); i++) {
			JsonObject jo = (JsonObject) jsonArray.get(i); // 인덱스 번호로 접근해서 가져온다. 
			prefix = jo.get("prefix").getAsString();
			
			if(uri.equals(prefix)) {
				url = jo.get("url").getAsString()+prefix+param;
			}
		}
		
		HttpClient httpClient = new HttpClient();
		
		try {
			httpClient.start();
			ContentResponse contentRes = httpClient.newRequest(url).method(HttpMethod.GET).send();
			
			res.setStatus(200);
			res.getWriter().write(contentRes.getContentAsString());
			
			//httpClient.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("-----------POST----------------");
		
		String prefix = "";
		String url = "";
		String uri = req.getRequestURI().toString();
		
		String configStr = (String)getServletContext().getAttribute("config");
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(configStr);
		JsonArray jsonArray = element.getAsJsonObject().get("roots").getAsJsonArray();
		
		for(int i=0; i<jsonArray.size(); i++) {
			JsonObject jo = (JsonObject) jsonArray.get(i); // 인덱스 번호로 접근해서 가져온다. 
			prefix = jo.get("prefix").getAsString();
			
			if(uri.equals(prefix)) {
				url = jo.get("url").getAsString()+prefix;
			}
		}
		
		BufferedReader input = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String buffer;
		StringBuilder sb = new StringBuilder();
		while((buffer = input.readLine())!=null) {
			sb.append(buffer + "\n");
		}
		input.close();
		
		HttpClient httpClient = new HttpClient();
		
		try {
			httpClient.start();
			Request request = httpClient.POST(url);
			request.header(HttpHeader.CONTENT_TYPE, "application/json");
			String json = sb.toString();
			request.content(new StringContentProvider(json, "utf-8"));
			
			
			ContentResponse contentResPost = request.send();
			
			res.setStatus(200);
			res.getWriter().write(contentResPost.getContentAsString());
			//httpClient.stop();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		res.setStatus(200);
		
	}
}
