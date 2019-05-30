package test.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Merge {

	Map<String, Integer> sourceList = null;
	Map<String, Integer> targetList = null;
	String targetFilePath = "";
	String sourceFilePath = "";
	public Merge(){
		sourceList = new TreeMap<String, Integer>();
		targetList = new TreeMap<String, Integer>();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Merge merge = new Merge();
		
		merge.makeList();
		merge.writeList();
		merge.writeResultFile();

	}
	
	public void writeResultFile() {
		PrintWriter r_writer = null;
		String data = "";
		
		String cmd = "";
		BufferedReader reader;
		
		try {
			r_writer = new PrintWriter(new FileWriter("result.txt"),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Set<String> keys = sourceList.keySet();
		
		for(String key : keys) {
			if(targetList.containsKey(key)){
				
				if(sourceList.get(key) > targetList.get(key)) {
					// create
					data = key +"_" +"C";
					cmd = "cmd /c copy source\\"+key+" target\\";
					try {
						Runtime.getRuntime().exec(cmd);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					r_writer.println(data);
					
				}else{
					data = key +"_" +"U";
					r_writer.println(data);
				}
			}else{
				data = key +"_" +"C";
				
				cmd = "cmd /c copy source\\"+key+" target\\";
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				r_writer.println(data);
			}
			
		}
		
		Set<String> targetKeys = targetList.keySet();
		
		for(String key : targetKeys) {
			if(!sourceList.containsKey(key)){
				data = key +"_" +"D";
				cmd = "cmd /c del target\\"+key;
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				r_writer.println(data);
			}

		}

	}
	
	public void makeList() {
		
		File dirSource = new File("source"); 
		File dirTarget = new File("target"); 
		File[] sourcefileList = dirSource.listFiles(); 
		File[] targetfileList = dirTarget.listFiles(); 
		
		targetFilePath = dirTarget.getAbsolutePath();
		sourceFilePath = dirSource.getAbsolutePath();
		
		for(File file: sourcefileList) {
			
//			System.out.println(file.getName());
			if(file.getName().equals("file_list.txt")) {
				continue;
			}
			
			BufferedReader br = null;
			String str = null;
			
			try {
				br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				str = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sourceList.put(file.getName(), Integer.parseInt(str));
			
	        try {
			br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		for(File file: targetfileList) {
			
			if(file.getName().equals("file_list.txt")) {
				continue;
			}
			
			BufferedReader br = null;
			String str = null;
			
			try {
				br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				str = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			targetList.put(file.getName(), Integer.parseInt(str));
			
	        try {
			br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
				

	}
	
	public void writeList()
	{
		FileWriter fwSource = null;
		FileWriter fwTarget = null;
		
		//source 
		try {
			fwSource = new FileWriter("./source/file_list.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        for(String key : sourceList.keySet()) {
            String data = key + "_" + sourceList.get(key)+"\r\n";
            try {
            fwSource.write(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        try {
        	fwSource.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //target
        try {
        	fwTarget = new FileWriter("./target/file_list.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        for(String key : targetList.keySet()) {
            String data = key + "_" + targetList.get(key)+"\r\n";
            try {
            	fwTarget.write(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        try {
        	fwTarget.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
