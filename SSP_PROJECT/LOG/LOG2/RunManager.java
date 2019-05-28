package test.com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RunManager { 

	List<String> list = new ArrayList<String>();
	Map<String, Integer> log = new TreeMap<String, Integer>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RunManager runManager = new RunManager();
		
		runManager.readFile();
		runManager.writeFile();
	}

	public void readFile()
	{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("./LOGFILE_A.TXT"));
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
//	        	System.out.println(line);
	        	
	        	String[]str = line.split("#");
	        	
	        	if(log.containsKey(str[1])) {
	        		log.put(str[1], log.get(str[1])+1);
	        	}else {
	        		log.put(str[1], 1);
	        	}
	        }
	        try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void writeFile()
	{
		FileWriter fw = null;
		String data = "";
		try {
			fw = new FileWriter("./REPORT_A.TXT");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        for(String str : log.keySet()) {
        	data = str + "#" + log.get(str) + "\r\n";
            try {
				fw.write(data);
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
        }
        
        try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
