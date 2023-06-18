package test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RunManager {
	
	static ArrayList<String> uriList = new ArrayList<String>();
	static HttpClient httpClient = new HttpClient();
	
	static String outputQueueURI ="";
	static ArrayList<Worker> workerList = new ArrayList<Worker>();

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		RunManager rm = new RunManager();

		httpClient.start();
		ContentResponse contentRes = httpClient.newRequest("http://127.0.0.1:8080/queueInfo").method(HttpMethod.GET).send();
		System.out.println(contentRes.getContentAsString());
		
		String inputStr = contentRes.getContentAsString();
		JsonArray jsonArray = new JsonArray();
		JsonElement element = JsonParser.parseString(inputStr);
		
		int inputQueueCount = element.getAsJsonObject().get("inputQueueCount").getAsInt();
		outputQueueURI = element.getAsJsonObject().get("outputQueueURI").getAsString();
		jsonArray = element.getAsJsonObject().get("inputQueueURIs").getAsJsonArray();
		
		for(int i=0; i<jsonArray.size(); i++) {
			uriList.add(jsonArray.get(i).getAsString());
		}
		
		for(int i=0; i<inputQueueCount; i++) {
			Worker worker = new Worker(i);
			workerList.add(Worker);
		}
		
		for(int i=0; i<inputQueueCount; i++) {
			Thread test = new Thread(new MyThread(i));
			//System.out.println(i);
			test.start();
			
			threadList.add(test);
		}
		
		for(Thread t : threadList) {
			t.join();
		}
	}
	
	public void inputQueue(int i) throws InterruptedException, TimeoutException, ExecutionException{
		
		while(true) {
			ContentResponse contentRes = httpClient.newRequest(uriList.get(i)).method(HttpMethod.GET).send();
			System.out.println("[res]" + contentRes.getContentAsString());
			
			if(contentRes.getContentAsString() == null) {
				break;
			}
			
			Request request = httpClient.newRequest(outputQueueURI).method(HttpMethod.POST);
			request.header(HttpHeader.CONTENT_TYPE, "application/json");
			
			JsonElement element = JsonParser.parseString(contentRes.getContentAsString());
			JsonObject object = element.getAsJsonObject();
			Long timestamp = object.get("timestamp").getAsLong();
			String value = object.get("value").getAsString();
			
			String workerRes = workerList.get(i).run(timestamp, value);
			
			if(workerRes != null) {
				JsonObject object1 = new JsonObject();
				object1.addProperty("result", workerRes);
				
				request.content(new StringContentProvider(object1.toString(), "utf-8"));
				ContentResponse contentRes1 = request.send();
			}else {
				System.out.println("empty");
			}
		}
	}
}

class MyThread implements Runnable{
	private int index =0;
	public MyThread(int index) {
		this.index = index;
	}
	
	RunManager rm = new RunManager();
	
	public void run() {
		synchronized(this) {
			try {
				rm.inputQueue(index);
			}catch (InterruptedException | TimeoutException | ExecutionException e) {
				e.printStackTrace();
			}
			index++;
		}
	}
}
