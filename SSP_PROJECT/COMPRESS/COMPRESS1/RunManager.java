package test.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RunManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RunManager runManager = new RunManager();
		
		//Scanner sc = new Scanner(System.in);
//		String input = sc.nextLine();
		runManager.subDirList("./BIGFILE", "ABCDFILE.TXT");
	}

	public void subDirList(String source, String fileName){
		File dir = new File(source); 
		File[] fileList = dir.listFiles(); 
		try{
			for(int i = 0 ; i < fileList.length ; i++){
				File file = fileList[i]; 
				if(file.isFile()){
					
					if(file.getName().equals(fileName)){
						compressLine(file.getCanonicalPath().toString());
					}
					System.out.println("\t 파일이름 = " + file.getName());
				}else if(file.isDirectory()){
					System.out.println("디렉토리 이름= " + file.getName());
					subDirList(file.getCanonicalPath().toString(), fileName); 
				}
			}
		}catch(IOException e){
			
		}
	}
	
	public void compressLine(String fileName) {
		BufferedReader br = null;
		FileWriter fw = null;
		String data = "";
		String prevLine = "";
		int lineCount =1;
		int totalCount = 0;
		
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			fw = new FileWriter("./ABCDFILE.TXT");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(true) {
			String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(line == null) break;
			
			if(totalCount != 0) {
				if(line.equals(prevLine)) {
					lineCount++;
				}else {
					if(lineCount > 1) {
						data = String.valueOf(lineCount) + "#" + prevLine + "\r\n";
					}else {
						data = prevLine + "\r\n";
					}
					lineCount = 1;
					
		            try {
		            	fw.write(data);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			prevLine = line;
			totalCount++;
		}
		
		if(lineCount > 1) {
			data = String.valueOf(lineCount) + "#" + prevLine + "\r\n";
		}else {
			data = prevLine + "\r\n";
		}
		lineCount = 1;
		
        try {
        	fw.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
        	br.close();
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
