package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RunManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RunManager rm = new RunManager();

		Scanner sc = new Scanner(System.in);
		
		String input = sc.nextLine();
		
		String[] arr = input.split(" ");
		
		String fileName = "./"+input+".txt";
		
		String fname = rm.readFile(fileName);
		String result = rm.readFile(fname);
				
		System.out.println(result);
			
	}
	

	public String readFile(String fileName)
	{
		BufferedReader br = null;
		String str = "";
		StringBuilder buffer = new StringBuilder();
		
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        while(true) {
			String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (line==null) break;
        	buffer.append(line);
        	//System.out.println(line);
        }
        
        try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        str = buffer.toString();
        return str;

	}
	
}
