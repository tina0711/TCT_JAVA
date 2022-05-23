package test;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

public class MyClient {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		ContentResponse contentRes = httpClient.newRequest("http://127.0.0.1:8080/account/3/param?name=kim&age=5").header("x-api-key", "APIKEY1").method(HttpMethod.GET).send();
		
		System.out.println(contentRes.getContentAsString());
		
	}

}
