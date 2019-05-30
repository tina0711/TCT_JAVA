package test.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RunManager {

	static Sender sender = new Sender(9876);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RunManager runManager = new RunManager();
		
		sender.initSender();
		
		String cmd = sender.recvMsg();
		System.out.println("[" + cmd + "]");
	
		
		runManager.subDirList("./BIGFILE", cmd);
		
		sender.destroySender();
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
	
	public String changeChar(String input) {
		String changeStr = "";
		
		char[] arr = input.toCharArray();
		char[] tmp = new char[arr.length];
		
		for(int i=0; i<arr.length; i++) {
			if(arr[i] >= 'A' && arr[i] <= 'E') {
				tmp[i] = (char)(arr[i] + 26-5);
			}else {
				tmp[i] = (char)(arr[i]-5);
			}
		}
		changeStr = String.valueOf(tmp);
		return changeStr;
	}
	
	public String compressChar(String input) {
		String compressChar="";
		int count =1;
		char prevCh = 0;
		char[]arr = input.toCharArray();
		for(int i=0; i<arr.length; i++) {
			if(i!= 0) {
				if(arr[i] == prevCh) {
					count++;
				}else {
					if(count>=3) {
						compressChar += String.valueOf(count) + String.valueOf(prevCh);
					}else if(count ==2) {
						compressChar += String.valueOf(prevCh) + String.valueOf(prevCh);
					}else {
						compressChar += String.valueOf(prevCh);
					}
					
					count =1;
				}
			}
			
			prevCh = arr[i];
		}
		
		if(count>=3) {
			compressChar += String.valueOf(count) + String.valueOf(prevCh);
		}else if(count ==2) {
			compressChar += String.valueOf(prevCh) + String.valueOf(prevCh);
		}else {
			compressChar += String.valueOf(prevCh);
		}
		
		System.out.println(compressChar);
		
		return compressChar;
		
	}
	
	public void compressLine(String fileName) {
		BufferedReader br = null;
		FileWriter fw = null;
		String data = "";
		String prevLine = "";
		String compressCh ="";
		String changeStr = "";
		String response = "";
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
			changeStr = changeChar(line);
			compressCh = compressChar(changeStr);

			if(totalCount != 0) {
				if(compressCh.equals(prevLine)) {
					lineCount++;
				}else {
					if(lineCount > 1) {
						data = String.valueOf(lineCount) + "#" + prevLine + "\r\n";
					}else {
						data = prevLine + "\r\n";
					}
					lineCount = 1;
					
					sender.sendMsg(data);
					response = sender.recvMsg();
					System.out.println("[recv] " + response);
					
					if(response.length() > 0) {
						if(response.equals("ACK")) {
							System.out.println("ACK RECEIVE");
						}else if(response.equals("ERR")){
							System.out.println("ERR RECEIVE");
							
							while(true) {
								System.out.println("[recv] " + data);
								sender.sendMsg(data);
								
								response = sender.recvMsg();
								if(response.length() > 0) {
									if(response.equals("ACK")) {
										System.out.println("[ERR] ACK RECEIVE");
										break;
									}
								}
							}
						}else {
						String newLine = "";
						lineCount =1;
						totalCount = -1;
						int recvNum = Integer.parseInt(response);
						System.out.println("recvNum : " + recvNum);
						
						try {
							br.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							br = new BufferedReader(new FileReader(fileName));
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
							for(int j=0; j<recvNum-1; j++) {
								try {
									newLine = br.readLine();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								System.out.println("newLine [" + j+"]" + newLine);
							}
							compressCh = newLine;
						}
					}
				}
			}
			
			prevLine = compressCh;
			totalCount++;
		}
		
		if(lineCount > 1) {
			data = String.valueOf(lineCount) + "#" + prevLine + "\r\n";
		}else {
			data = prevLine + "\r\n";
		}
		
		sender.sendMsg(data);
		response = sender.recvMsg();
		System.out.println("[recv] "+ response);
        
        try {
        	br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
}
