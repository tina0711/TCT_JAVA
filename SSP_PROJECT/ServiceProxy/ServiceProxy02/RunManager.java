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
		String fname = "";
		String path ="";
		
		String[] arr = input.split(" ");
		path = arr[1];
		
		String fileName = "./"+arr[0]+".txt";
		
		while(true) {
			fileName = rm.readFile(fileName, path);
			String []str = fileName.split("#");
			
			fileName = str[1];
			
			if(fileName.contains("Service-")) {
				break;
			}
		}
		
		rm.readServiceFile(fileName);
			
	}
	
	public String readFile(String fileName, String path)
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
//        	buffer.append(line);
        	if(line.contains(path)) {
        		str = line;
        		break;
        	}
        	//System.out.println(line);
        }
        
        try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        str = buffer.toString();
        return str;

	}
	
	public String readServiceFile(String fileName)
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
//        	buffer.append(line);
        	str = line;
        	System.out.println(str);
        }
        
        try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        str = buffer.toString();
        return str;

	}


}
