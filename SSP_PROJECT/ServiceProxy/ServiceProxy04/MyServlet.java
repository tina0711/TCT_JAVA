package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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


public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		System.out.println("doGet : " + req.getRequestURI().toString());
		
		Gson gson = new Gson();
		
		String url = "";
		String uri = req.getRequestURI().toString();
		String param = req.getQueryString();
		
		Map<String, String> map = (Map<String, String>)getServletContext().getAttribute("map");
		
		String reqId = req.getHeader("x-requestId");
		
		// 헤더 전체정보 보기
		HashMap<Object, Object> header = new HashMap<Object, Object>();
		Enumeration<String> em = req.getHeaderNames();
		
		while(em.hasMoreElements()) {
			String name = em.nextElement();
			String val = req.getHeader(name);
			System.out.println(name + " : " + val);
			header.put(name, req.getHeader(name));
		}
		
		String path = uri.split("/")[1];
		if(param != null) {
			url = map.get("/"+path)+uri+"?"+param;
		}else {
			url = map.get("/"+path)+uri;
		}
		//url : http://127.0.0.1:8081/front
		String inputTarget = map.get("/"+path)+uri;
		
		
		if(path.equals("trace")) {
			res.setStatus(200);
			res.getWriter().write("trace json~~");
		}
		
		HttpClient httpClient = new HttpClient();
		
		try {
			httpClient.start();
			ContentResponse contentRes = httpClient
					.newRequest(url)
					.header("x-requestId", reqId)
					.header("x-lastcall", inputTarget)
					.method(HttpMethod.GET).send();			
			
			res.getWriter().write(contentRes.getContentAsString());
			res.setStatus(contentRes.getStatus());
			
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
