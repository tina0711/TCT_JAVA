package test.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TaskList {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String s;
		Process process = new ProcessBuilder("cmd", "/c", "tasklist").start();
		
		BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
		int memorySize = 0;
		
		while((s = stdOut.readLine()) != null) {
			System.out.println(s);
			if(s.contains(".exe")) {
				String[] token = s.split("\\s+");
				String arr = token[4].replace(",", "");
				
				memorySize = Integer.parseInt(arr);
				System.out.println(memorySize);
			}
		}
	}

}
