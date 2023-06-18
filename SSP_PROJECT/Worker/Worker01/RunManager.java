package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RunManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RunManager rm = new RunManager();

		int queue = 0;
		Scanner sc = new Scanner(System.in);
		
		ArrayList<Worker> list = new ArrayList<Worker>();
		
		for(int i=0; i<2; i++) {
			Worker worker = new Worker(i);
			list.add(worker);
		}
		
		while(true) {
			String input = sc.nextLine();
			
			String outputQueue ="";
			String[] inputArr = input.split(" ");
			String value = inputArr[1];
			
			queue = Integer.parseInt(inputArr[0]);
			
			outputQueue = list.get(queue).run(value);
			
			if(outputQueue != null) {
				System.out.println(outputQueue);
			}else {
//				System.out.println("empty");
			}
		}
	}
}
