package test.gate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class GATE {

	
	TreeMap<String, ArrayList<String>> empMap = new TreeMap<String, ArrayList<String>>();
	
	public GATE() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GATE gate = new GATE();
		
		Scanner sc = new Scanner(System.in);
		
		gate.readFile();
		gate.printMap();

		while(true) {
			String input = sc.nextLine();
			String []tmp = input.split("#");
			gate.writeFile(tmp[0], tmp[1]);
		}

	}
	
	public void printMap() {
		for(String key : empMap.keySet()) {
			System.out.print(key + " : ");
			System.out.println(empMap.get(key).toString());
		}
	}
	
	public void readFile()
	{
		BufferedReader br = null;
		ArrayList<String> list = null;
		File f = new File("./EMPGATE.TXT");
		
		if(f.exists()) {
	
			try {
				br = new BufferedReader(new FileReader("./EMPGATE.TXT"));
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
	//	    	System.out.println(line);
		    	
		    	String []tmp = line.split("#");
		    	
		    	if(empMap.containsKey(tmp[0])) {
		    		list = empMap.get(tmp[0]);
		    	}else{
		    	  	list = new ArrayList<String>();
		    	}
		    	
		    	
		  		list.add(tmp[1]);
		  		
		  		TreeSet<String> distinctData = new TreeSet<String>(list);
				list = new ArrayList<String>(distinctData);
				
		  		Collections.sort(list);
	    		empMap.put(tmp[0], list);
		    	
	        }
		        
	        try {
	        	br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		} else {
		      System.out.println("파일 없음");            
		}
		

	}
	
	public void writeFile(String empNo, String gate)
	{
		FileWriter fw = null;
	    String data = "";
	    boolean flag = false;
	    
	    try {
			fw = new FileWriter("./RESULT.TXT", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    for(String key : empMap.keySet()) {
	    	if(key.equals(empNo)) {
	    	    ArrayList<String> list = new ArrayList<String>();
	    		list = empMap.get(key);
	     	    
	    		for(int i=0; i<list.size(); i++) {
	    			
	    	 		if(gate.equals(list.get(i))){
	    	 	
	    	 			flag = true;
	    	 		}
	    		}
	   
	    	}
	    }
	    
	    if(flag == true) {
	    	data = empNo + "#" + gate + "#" + "Y" + "\r\n";
	    }else {
	    	data = empNo + "#" + gate + "#" + "N" + "\r\n";
	    }
	    
	    flag = false;
	    
      	try {
        	fw.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
