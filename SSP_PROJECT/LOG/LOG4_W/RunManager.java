package test.com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class RunManager {

	static String logFilePath = ".\\LOGFILE_B.TXT";
	static String reportFilePath = ".\\REPORT_B.TXT";
	static String typeFilePathPre = ".\\TYPE_";
	static String convExe = ".\\CODECONV.EXE";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String line = "";
			String[] type;
			TreeMap<String, Integer> tm = new TreeMap<String, Integer>();
			
			//파일 객체 생성
			File rfile = new File(logFilePath);
			//입력 스트림 생성
			FileReader filereader = new FileReader(rfile);
			//입력 버퍼 생성
			BufferedReader bufReader = new BufferedReader(filereader);
			
			while((line = bufReader.readLine()) != null) {
				type = line.split("#");
				if(tm.containsKey(type[1])) {
					tm.put(type[1], tm.get(type[1])+1);
				}else {
					tm.put(type[1], 1);
					Thread th = new Thread(new MyThread(), type[1]);
					th.start();
					System.out.println("Thread : "+th.getId() + "시작");
				}
			}
			bufReader.close();
			
			//파일 객체 생성
			File wfile = new File(reportFilePath);
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(wfile));
			
			if(wfile.isFile() && wfile.canWrite()) {
				for(String key: tm.keySet()) {
					bufferedWriter.write(key + "#" + tm.get(key));
					bufferedWriter.newLine();
				}
				
				bufferedWriter.close();
			}
		}catch(FileNotFoundException e) {
			
		}catch(IOException e) {
			System.out.println(e);
		}
		
	}
	
	public void fileReadWrite() {
		String readStr = "";
		try {
			// 파일 객체 생성
			File rfile = new File(logFilePath);
			File wfile = new File(typeFilePathPre + Thread.currentThread().getName() + ".TXT");
			// 입/출력 버퍼 생성
			BufferedReader br = new BufferedReader(new FileReader(rfile));
			BufferedWriter bw = new BufferedWriter(new FileWriter(wfile));
			
			while((readStr = br.readLine()) != null) {
				if(readStr.split("#")[1].equals(Thread.currentThread().getName())) {
					bw.write(convertLog(readStr));
					bw.newLine();
				}
			}
			System.out.println("Thread :" + Thread.currentThread().getId() + "완료");
			bw.close();
			br.close();
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
	public String convertLog(String inStr) {
		String outStr = "";
		Runtime rt = Runtime.getRuntime();
		Process pc = null;
		String[] type = inStr.split("#");
		try {
			pc = rt.exec(convExe + " " + type[2] );
			BufferedReader br = new BufferedReader(new InputStreamReader(pc.getInputStream()));
			outStr = type[0] + "#"+type[1]+"#"+br.readLine();
		}catch(IOException e) {
			System.out.println(e);
		}
		return outStr;
	}

}

class MyThread implements Runnable{
	RunManager rm = new RunManager();
	
	public void run() {
		synchronized (this) {
			rm.fileReadWrite();
		}
	}
}
