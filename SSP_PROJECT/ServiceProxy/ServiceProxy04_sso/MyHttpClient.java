package test;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpMethod;

public class MyHttpClient {
	private HttpClient httpClient;
	
	public MyHttpClient() {
		httpClient = new HttpClient();
		
		try {
			httpClient.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String sendRequest(String url, String method, HashMap<Object, Object> headers, String body) {
		
		ContentResponse contentRes;
		HttpMethod httpMethod = HttpMethod.fromString(method);
		String requestID="";
		
		try {
			Request request = httpClient.newRequest(url).method(httpMethod);
			
			for(Entry<Object, Object> o: headers.entrySet()) {
				request.header(o.getKey().toString(), o.getKey().toString());
			}
			contentRes = request.send();
			String result = contentRes.getContentAsString();
			
			return result;
		}catch(InterruptedException e) {
			e.printStackTrace();
		}catch(TimeoutException e) {
			e.printStackTrace();
		}catch(ExecutionException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
