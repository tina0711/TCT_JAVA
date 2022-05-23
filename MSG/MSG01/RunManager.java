package test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RunManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Queue<String> que = new LinkedList<String>();
		while(true) {
			Scanner sc = new Scanner(System.in);
			
			String input = sc.nextLine();
			
			String[] arr = input.split(" ");
			
			String api = arr[0];
			
			if(api.equals("SEND")) {
				String message = arr[1];
				que.add(message);
			}else if(api.equals("RECEIVE")) {
				
				String str = que.poll();
				if(str != null) {
					System.out.println(str);
				}
				
			}
			
		}
	}

}
