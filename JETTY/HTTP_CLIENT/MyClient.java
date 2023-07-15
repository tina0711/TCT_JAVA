package test;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MyClient {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Gson gson = new Gson();
		HttpClient httpClient = new HttpClient();
		httpClient.start();

		// GET 방식
		ContentResponse contentRes = httpClient.newRequest("http://127.0.0.1:8080/mypath").method(HttpMethod.GET).send();
		System.out.println(contentRes.getContentAsString());

                // GET 방식 - HEADER 추가
		ContentResponse contentRes1 = httpClient.newRequest("http://127.0.0.1:8080/account/3/param?name=kim&age=5").header("x-api-key", "APIKEY1").method(HttpMethod.GET).send();	
		System.out.println(contentRes1.getContentAsString());

		//POST 방식 - Body 추가
		Request request = httpClient.newRequest("http://127.0.0.1:8080/CREATE/PLAY").method(HttpMethod.POST);
		request.header(HttpHeader.CONTENT_TYPE, "application/json");
		
		JsonObject object = new JsonObject();
		object.addProperty("QueueSize", 2);
		String str = object.toString();
		
		request.content(new StringContentProvider(str, "utf-8"));
		ContentResponse contentRes2 = request.send();
		System.out.println(contentRes2.getContentAsString());

		// 4-1 POST 방식 - Body 없음
		Request request3 = httpClient.newRequest("http://127.0.0.1:8080/ACK/PLAY/0").method(HttpMethod.POST);
		request3.header(HttpHeader.CONTENT_TYPE, "application/json");
		
		ContentResponse contentRes3 = request3.send();
		System.out.println(contentRes3.getContentAsString());
		
		
	}

}
