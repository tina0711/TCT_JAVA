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

	public String sendRequest(String url, String method, String body) {
		
		ContentResponse contentRes;
    String result = "";
		HttpMethod httpMethod = HttpMethod.fromString(method);
		
		try {
      if(method.equals("GET")){
  			contentRes = httpClient.newRequest(url).method(httpMethod).send();
  			result = contentRes.getContentAsString();
      }else if(method.equals("POST")){
  
        Request request = httpClient.newRequest(url).method(httpMethod);
        request.header(HttpHeader.CONTENT_TYPE, "application/json");

        request.content(new StringContentProvider(body, "utf-8"));
			  contentRes = request.send();
        result = contentRes.getContentAsString();
      }
			
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
