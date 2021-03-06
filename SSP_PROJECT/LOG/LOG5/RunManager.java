package test.com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunManager { 

	static List<String> list = new ArrayList<String>();
	Map<String, Integer> log = new TreeMap<String, Integer>();
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		RunManager runManager = new RunManager();
		
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		
		runManager.readFile();
		runManager.writeFile();
		
		for(int i=0; i<list.size(); i++)		{
			executorService.execute(new MyThread(i));
		}
	
		executorService.shutdown();
		
		System.out.println("End main method");
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
	        	list.add(line);
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
	
	public void writeEachLogFile(int i) {
		FileWriter fw = null;
		String data = ""; 
		String fileName = "";
		
		String listStr = list.get(i);
		String []tmp = listStr.split("#");
		fileName = "./TYPELOG_"+ tmp[1] + ".TXT";
		
		Process process;
		BufferedReader reader;
		String line;
		String result = "";
		
		try {
			fw = new FileWriter(fileName, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String cmd = "CODECONV.EXE";
			cmd += tmp[2];
			process = Runtime.getRuntime().exec(cmd);
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while((line=reader.readLine())!=null) {
				result = line + "\r\n";
			}
				
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		data = tmp[0] +"#"+tmp[1]+"#"+result;
		try {
			fw.write(data);
		}catch(IOException e) {
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

class MyThread implements Runnable{
	private int index =0;
	public MyThread(int index) {
		this.index = index;
	}
	
	RunManager rm = new RunManager();
	
	public void run() {
		synchronized(this) {
			rm.writeEachLogFile(index);
			index++;
		}
	}
}
