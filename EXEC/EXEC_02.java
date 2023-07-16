package test.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunManager { 

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		RunManager runManager = new RunManager();
		
		String line = "";
		String result = "";
		
		Process process;
		BufferedReader reader;
		
		String cmd = "C:\\ssp_workspace\\ssp_java_test\\SUB3\\CODECONV.EXE ";
		cmd += "MESSAGE";
		process = Runtime.getRuntime().exec(cmd);
		reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		while((line=reader.readLine())!=null) {
			result = line + "\r\n";
		}
		System.out.println(result);

	}

}
