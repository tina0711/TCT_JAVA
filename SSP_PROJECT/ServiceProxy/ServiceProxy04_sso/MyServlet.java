package test;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private JsonArray jsonRoutes;
	private MyHttpClient myClient;
	private String proxyName;
	private TextFileReader reader;
	
	public MyServlet() {
		
	}
	@Override
	public void init() throws ServletException{
		this.jsonRoutes = (JsonArray)getServletContext().getAttribute("proxy.routes");
		this.proxyName = (String)getServletContext().getAttribute("proxy.name");
		this.myClient = new MyHttpClient();
		//System.out.println(this.jsonRoutes);
	}
	
	private void search(String prevUrl, String targetUrl, Map<Object, Object> search) {
		if(!search.containsKey("target")) {
			return;
		}
		
		String target =(String)search.get("target");
		List<Object> services;
		Gson gson = new Gson();
		
		if(target.equals(prevUrl)) {
			if(search.containsKey("services"))
				services = (List<Object>)(search.get("services"));
			else
				services = new ArrayList<Object>();
			HashMap<Object, Object> service = new HashMap<Object, Object>();
			service.put("target", targetUrl);
			service.put("status", "200");
			services.add(service);
			search.put("services", services);
			return;
		}
		
		if(search.containsKey("services")) {
			HashMap<Object, Object> test;
			services = (List<Object>)(search.get("services"));
			for(Object o:services) {
				search(prevUrl, targetUrl, (Map)o);
			}
		}
	}
	
	private void trace(HttpServletRequest req,String requestID, HashMap<Object, Object> headers, String targetUrl) {
		HashMap<Object, Object> callStack = new HashMap<Object, Object>();
		Gson gson = new Gson();
		
		String url = req.getScheme()+"://"+req.getRemoteAddr()+":"+req.getLocalPort()+req.getRequestURI();
		
		try {
			
			String prevUrl="";
			if(headers.containsKey("x-lastcall")) {
				prevUrl = (String)headers.get("x-lastcall");
				System.out.println("**last: "+headers.get("x-lastcall"));
				
				String callStackFromFile = reader.readFile(requestID+".txt");
				callStack = gson.fromJson(callStackFromFile,  HashMap.class);
				
				for(Entry<Object, Object> entry:callStack.entrySet()) {
					System.out.println(entry.getKey().toString() + ": " + entry.getValue().toString());
				}
				
				HashMap<Object, Object> search = callStack;
				
				if(!prevUrl.equals(url)) {
					search(prevUrl, url, callStack);
				}else {
					search(url, targetUrl, callStack);
				}
				
			}else {
			
				callStack.put("target", url);
				HashMap<Object, Object> service = new HashMap<Object, Object>();
				service.put("target", targetUrl);
				service.put("status", "200");
				List<Object> services = new ArrayList<Object>();
				services.add(service);
				callStack.put("services", services);
			}
			callStack.put("status", "200");
			headers.put("x-lastcall", targetUrl);
			
			FileWriter writer = new FileWriter(requestID+".txt");
			writer.append(gson.toJson(callStack));
			
			writer.flush();
			writer.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		System.out.println("doGet : " + req.getRequestURI().toString());
		res.setStatus(200);
		
		if(req.getRequestURI().startsWith("/trace/")) {
			String fileId = req.getRequestURI().substring("/trace/".length());
			String fileContents = reader.readFile(fileId+".txt");
			res.getWriter().write(fileContents);
			return;
		}
		
		String requestID = req.getHeader("x-requestId").toString();
		HashMap<Object, Object> header = new HashMap<>();
		Enumeration<String> headerNames = req.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			header.put(key, req.getHeader(key));
		}
		
		if(!req.getRequestURI().contains("favicon.ico")) {
			for(JsonElement route : jsonRoutes) {
				String path = route.getAsJsonObject().get("pathPrefix").getAsString();
				if(req.getRequestURI().startsWith(path)) {
					String url = route.getAsJsonObject().get("url").getAsString();
					String redirectUrl = url+req.getRequestURI();
					String query = req.getQueryString();
					
					this.trace(req, requestID, header, redirectUrl);
					
					if(query!=null) {
						redirectUrl = redirectUrl+"?" + query;
					}
					String resContents = myClient.sendRequest(redirectUrl, "GET", header, null);
					//System.out.println(resContents);
					res.getWriter().write(resContents);
					break;
				}
			}
		}
	
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("doPost : " + req.getRequestURI().toString());
		res.setStatus(200);
		String requestID = req.getHeader("x-requestId").toString();
		HashMap<Object, Object> header = new HashMap<>();
		Enumeration<String> headerNames = req.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			header.put(key, req.getHeader(key));
		}
		
		for(JsonElement route : jsonRoutes) {
			String path = route.getAsJsonObject().get("pathPrefix").getAsString();
			if(req.getRequestURI().startsWith(path)) {
				String url = route.getAsJsonObject().get("url").getAsString();
				String redirectUrl = url+path;
				
				this.trace(req, requestID, header, redirectUrl);
//				System.out.println(redirectUrl);
				StringBuilder buffer = new StringBuilder();
				BufferedReader reader = req.getReader();
				String line;
				while((line = reader.readLine()) != null) {
					buffer.append(line);
					buffer.append(System.lineSeparator());
				}
				String data = buffer.toString();
				
				String resContents = myClient.sendRequest(redirectUrl, "POST", header, data);
				System.out.println(resContents);
				res.getWriter().write(resContents);
				break;
			}
		}
	}
}
