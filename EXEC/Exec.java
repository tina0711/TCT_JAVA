package test1.com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Exec {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Exec exec = new Exec();
		
		String msg = "ABCDEFG10";
		exec.execCommandStdOut(msg);
		exec.execCommandStdIn();
		
	}
	
	public String execCommandStdIn() {
		
		Process process = null;
		Scanner scan = new Scanner(System.in);
		// /c 명령어 처리가 끝나면 cmd를 종료하라
		
		try {
			process = new ProcessBuilder("cmd", "/c", "C:\\ssp_workspace\\ssp_java_test\\SUB3\\SIGNAGE.EXE").start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedWriter stdIn = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
	
		while(true) {
			String input = scan.nextLine();
			if(input.length() <= 0) {
				break;
			}
			
			input += "\n";
			
			try {
				stdIn.write(input);
				stdIn.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		try {
			stdIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return null;
		
	}
	
	public String execCommandStdOut(String msg) {
		
		Process process = null;
		// /c 명령어 처리가 끝나면 cmd를 종료하라
		
		String command = "C:\\ssp_workspace\\ssp_java_test\\SUB3\\CODECONV.EXE " + msg;
		String s = ""; 
		String result = "";
				
		try {
			process = new ProcessBuilder("cmd", "/c", command).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
	
		try {
			while((s = stdOut.readLine()) != null) {
				System.out.println(s);
				result = s;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
}
