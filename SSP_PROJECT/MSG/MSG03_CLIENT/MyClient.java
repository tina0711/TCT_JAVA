package test;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class MyClient {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		
		Gson gson = new Gson();
		
		//1-1 POST 방식 - Queue 생성
		Request request = httpClient.newRequest("http://127.0.0.1:8080/CREATE/PLAY").method(HttpMethod.POST);
		request.header(HttpHeader.CONTENT_TYPE, "application/json");
		
		JsonObject object = new JsonObject();
		object.addProperty("QueueSize", 2);
		String str = object.toString();
		
		request.content(new StringContentProvider(str, "utf-8"));
		ContentResponse contentRes = request.send();
		System.out.println(contentRes.getContentAsString());
		
		// 2-1 POST 방식 - Message 송신
		Request request4 = httpClient.newRequest("http://127.0.0.1:8080/SEND/PLAY").method(HttpMethod.POST);
		request4.header(HttpHeader.CONTENT_TYPE, "application/json");
		
		JsonObject object4 = new JsonObject();
		object4.addProperty("Message", "Hello");
		String str4 = object4.toString();
		
		request4.content(new StringContentProvider(str4, "utf-8"));
		ContentResponse contentRes4 = request4.send();
		System.out.println(contentRes4.getContentAsString());
		
		// 3-1 GET 방식 - Message 수신
		ContentResponse contentRes5 = httpClient.newRequest("http://127.0.0.1:8080/RECEIVE/PLAY").method(HttpMethod.GET).send();
		String res = contentRes5.getContentAsString();
		JsonElement element = JsonParser.parseString(res);
		JsonObject object5 = element.getAsJsonObject();
		String msgID = object5.get("MessageID").getAsString();
		
		System.out.println(res);
		System.out.println("MessageID : " + msgID);
		
		// 4-1 POST 방식 - ACK 송신
		Request request6 = httpClient.newRequest("http://127.0.0.1:8080/ACK/PLAY/0").method(HttpMethod.POST);
		request6.header(HttpHeader.CONTENT_TYPE, "application/json");
		
		ContentResponse contentRes6 = request6.send();
		System.out.println(contentRes6.getContentAsString());
		
	}

}
