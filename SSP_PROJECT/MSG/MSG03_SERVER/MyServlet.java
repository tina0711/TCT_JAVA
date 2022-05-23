package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	HashMap<String, MSG> map = new HashMap<String, MSG>();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		System.out.println("doGet : " + req.getRequestURI().toString());
		
		String[] uri = req.getRequestURI().split("/");
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = req.getReader();
		String line;
		while((line = reader.readLine()) != null) {
			buffer.append(line);
		//	buffer.append(System.lineSeparator());
		}
		
		String body = buffer.toString();
		String api = uri[1];
		String qName = uri[2];
		
		Gson gson = new Gson();
		
		if(api.equals("RECEIVE")) {
			MSG msg = map.get(qName);
			
			HashMap<String, String> hm = msg.getMessage();
			if(hm != null) {
				msg.printQueue();
				JsonObject CreatjsonObject = new JsonObject();
				CreatjsonObject.addProperty("Result", "OK");
				CreatjsonObject.addProperty("MessageID", hm.get("messageId"));
				CreatjsonObject.addProperty("Message", hm.get("message"));
				String data = gson.toJson(CreatjsonObject);
				res.getWriter().write(data);
			}else {
				JsonObject CreatjsonObject2 = new JsonObject();
				CreatjsonObject2.addProperty("Result", "No Message");
				String data = gson.toJson(CreatjsonObject2);
				res.getWriter().write(data);
			}
		}
		res.setStatus(200);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("doPost : " + req.getRequestURI().toString());
		
		String[] uri = req.getRequestURI().split("/");
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = req.getReader();
		String line;
		while((line = reader.readLine()) != null) {
			buffer.append(line);
		//	buffer.append(System.lineSeparator());
		}
		
		String body = buffer.toString();
		String api = uri[1];
		String qName = uri[2];
		
//		System.out.println("body = " + body);
		
		Gson gson = new Gson();
		JsonElement jsonElement = JsonParser.parseString(body);
		
		if(api.equals("CREATE")) {
			
			JsonObject object= jsonElement.getAsJsonObject();
			int QueueSize = object.get("QueueSize").getAsInt();
			
			if(map.containsKey(qName)) {
				JsonObject CreatjsonObject1 = new JsonObject();
				CreatjsonObject1.addProperty("Result", "QueueExist");
				String data = gson.toJson(CreatjsonObject1);
				res.getWriter().write(data);
			}else {
				map.put(qName, new MSG(QueueSize, qName));
				
				JsonObject CreatjsonObject2 = new JsonObject();
				CreatjsonObject2.addProperty("Result", "OK");
				String data = gson.toJson(CreatjsonObject2);
				res.getWriter().write(data);
			}
			
		}else if(api.equals("SEND")) {
			
			JsonObject object= jsonElement.getAsJsonObject();
			String message = object.get("Message").getAsString();
			
			MSG msg = map.get(qName);
			
			if(msg.checkQueueFullYn()) {
				JsonObject CreatjsonObject3 = new JsonObject();
				CreatjsonObject3.addProperty("Result", "Queue Full");
				String data = gson.toJson(CreatjsonObject3);
				res.getWriter().write(data);
			}else {
				msg.addMessage(message);
				msg.printQueue();
				JsonObject CreatjsonObject4 = new JsonObject();
				CreatjsonObject4.addProperty("Result", "OK");
				String data = gson.toJson(CreatjsonObject4);
				res.getWriter().write(data);
			}
		}else if(api.equals("ACK")) {
			String ackQname = uri[2];
			String ackMID = uri[3];
			
			MSG msg = map.get(ackQname);
			System.out.println("ackMID = " + ackMID);
			msg.removeMessage(ackMID);
			msg.printQueue();
			JsonObject CreatjsonObject = new JsonObject();
			CreatjsonObject.addProperty("Result", "OK");
			String data = gson.toJson(CreatjsonObject);
			res.getWriter().write(data);
			
		}else if(api.equals("FAIL")) {
			String ackQname = uri[2];
			String ackMID = uri[3];
			
			MSG msg = map.get(ackQname);
			msg.rollbackMessage(ackMID);
			msg.printQueue();
			JsonObject CreatjsonObject5 = new JsonObject();
			CreatjsonObject5.addProperty("Result", "OK");
			String data = gson.toJson(CreatjsonObject5);
			res.getWriter().write(data);
		}
		
		res.setStatus(200);
		
	}
}

class MSG{
	String name;
	int queueMaxSize;
	int processTimeout = 5000;
	int maxFailCount = 2;
	int waitTime;
	int seq = 0;
	Queue<HashMap<String, String>> msgQue;
	Queue<HashMap<String, String>> dlQue;
	
	Timer m_timer = new Timer();
	
	public MSG(int queueMaxSize, String name) {
		this.queueMaxSize = queueMaxSize;
		this.name = name;
		msgQue = new LinkedList<HashMap<String, String>>();
		dlQue = new LinkedList<HashMap<String, String>>();
	}

	public int getQueueMaxSize() {
		return queueMaxSize;
	}

	public void setQueueMaxSize(int queueMaxSize) {
		this.queueMaxSize = queueMaxSize;
	}

	public Queue<HashMap<String, String>> getMsgQue() {
		return msgQue;
	}

	public void setMsgQue(Queue<HashMap<String, String>> msgQue) {
		this.msgQue = msgQue;
	}
	
	public boolean checkQueueFullYn() {
		if(this.queueMaxSize == msgQue.size()) {
			return true;
		}
		return false;
	}
	
	public void addMessage(String message) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("message", message);
		hm.put("messageId", String.valueOf(getMessageId()));
		hm.put("sendYn", "N");
		hm.put("failCnt", "0");
		this.msgQue.add(hm);
	}
	
	public HashMap<String, String> getMessage(){
		
		for(HashMap<String, String> hm : this.msgQue) {
			
			if(hm.get("sendYn").equals("N")) {
				if(Integer.parseInt(hm.get("failCnt")) >= this.maxFailCount) {
					removeMessage(hm.get("messageId"));
					this.dlQue.add(hm);
					return hm;
				}else {
					hm.put("sendYn", "Y");
					if(this.processTimeout > 0){
						this.registTimeout(hm);
					}
					return hm;
				}
			}
		}
		return null;
	}
	
	public void removeMessage(String messageId) {
		for(HashMap<String, String> m : this.msgQue) {
			if(m.get("messageId").equals(messageId)) {
				this.msgQue.remove(m);
				break;
			}
		}
	}
	
	public void rollbackMessage(String messageId) {
		for(HashMap<String, String> m : this.msgQue) {
			if(m.get("messageId").equals(messageId)) {
				m.put("sendYn", "N");
				m.put("failCnt", String.valueOf(Integer.parseInt(m.get("failCnt")) +1));
			}
		}
	}
	
	public int getMessageId() {
		return this.seq++;
	}
	
	public void registTimeout(HashMap<String, String> m) {
		TimerTask m_task = new TimerTask(){
			@Override
			public void run() {
				System.out.println("Timer Test");
				System.out.println(m.get("message"));
				if(m.get("sendYn").equals("Y")) {
					m.put("sendYn", "N");
					m.put("failCnt", String.valueOf(Integer.parseInt(m.get("failCnt"))+1));
					System.out.println("--->TimeOut");
					printQueue();
				}
			}
		};
		m_timer.schedule(m_task, this.processTimeout);
	}
	
	public void printQueue() {
		System.out.println("---------------------QUEUE(" + this.name + ")----------------------");
		for(HashMap<String, String> m : this.msgQue) {
			System.out.println(m);
		}
		System.out.println("---------------------QUEUE(" + this.name + ")----------------------");		
		
		if(dlQue.size()>0) {
			System.out.println("---------------------DL-QUEUE(" + this.name + ")----------------------");
			for(HashMap<String, String> m : this.dlQue) {
				System.out.println(m);
			}
			System.out.println("-------------------------------------------");	
		}
	}
}
