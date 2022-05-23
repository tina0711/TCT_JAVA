package test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RunManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HashMap<String, MSG> map = new HashMap<String, MSG>();
		
		while(true) {
			Scanner sc = new Scanner(System.in);
			
			String input = sc.nextLine();
			
			String[] arr = input.split(" ");
			
			String api = arr[0];
			String queueName = arr[1];
			
			if(api.equals("CREATE")) {
				int size = Integer.parseInt(arr[2]);
				if(map.containsKey(queueName)) {
					System.out.println("Queue Exist");
				}else {
					MSG q = new MSG(size, queueName);
					map.put(queueName, q);
				}
				
			}else if(api.equals("SEND")) {
				
				MSG msg = map.get(queueName);
				String message = arr[2];
				Queue<String> sendQ = msg.getMsgQue();
				if(msg.checkQueueFullYn()) {
					System.out.println("Queue Full");
				}else {
					sendQ.add(message);
				}
				
			}else if(api.equals("RECEIVE")) {
				MSG msg = map.get(queueName);
				Queue<String> recvQ = msg.getMsgQue();
				String str = recvQ.poll();
				if(str != null) {
					System.out.println(str);
				}
				
			}
			
		}
	}

}

class MSG{
	String name;
	int queueMaxSize;
	Queue<String> msgQue;
	
	public MSG(int queueMaxSize, String name) {
		this.queueMaxSize = queueMaxSize;
		this.name = name;
		msgQue = new LinkedList<String>();
		
	}

	public int getQueueMaxSize() {
		return queueMaxSize;
	}

	public void setueueMaxSize(int queueMaxSize) {
		this.queueMaxSize = queueMaxSize;
	}

	public Queue<String> getMsgQue() {
		return msgQue;
	}

	public void setMsgQue(Queue<String> msgQue) {
		this.msgQue = msgQue;
	}
	
	public boolean checkQueueFullYn() {
		if(this.queueMaxSize == msgQue.size()) {
			return true;
		}
		return false;
	}
	
}
