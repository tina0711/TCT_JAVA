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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
			
			//thread pool 최대개수 설정
			ExecutorService executorService = Executors.newFixedThreadPool(5);
			
			while((line = bufReader.readLine()) != null) {
				type = line.split("#");
				if(tm.containsKey(type[1])) {
					tm.put(type[1], tm.get(type[1])+1);
				}else {
					tm.put(type[1], 1);
					// thread 작업 queue에 추가
					// thread pool 최대 개수까지만 생성하며 이후는 기존 작업 완료 후 수행
					executorService.execute(new MyThread(type[1]));
				}
			}
			
			executorService.shutdown();
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
	
	public void fileReadWrite(String threadName) {
		String readStr = "";
		try {
			// 파일 객체 생성
			File rfile = new File(logFilePath);
			File wfile = new File(typeFilePathPre + threadName + ".TXT");
			// 입/출력 버퍼 생성
			BufferedReader br = new BufferedReader(new FileReader(rfile));
			BufferedWriter bw = new BufferedWriter(new FileWriter(wfile));
			
			System.out.println("Thread :" + Thread.currentThread().getId() + "/" + threadName+ "시작");
			
			while((readStr = br.readLine()) != null) {
				if(readStr.split("#")[1].equals(threadName)) {
					bw.write(convertLog(readStr));
					bw.newLine();
				}
			}
			System.out.println("Thread :" + Thread.currentThread().getId() + "/" + threadName+ "완료");
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
		
		//실행 cmd 배열로 전달, String 으로도 전달 가능
		String[] cmd = {convExe, type[2]};
		StringBuffer rtn = new StringBuffer();
		try {
			pc = rt.exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(pc.getInputStream()));
			String tmp = "";
			
			while((tmp = br.readLine()) != null) {
				rtn.append(tmp);
			}
			outStr = type[0] + "#"+type[1]+"#"+rtn.toString();
		}catch(IOException e) {
			System.out.println(e);
		}
		return outStr;
	}

}

class MyThread implements Runnable{
	
	private final String threadName;
	
	public MyThread(String threadName) {
		this.threadName = threadName;
	}
	RunManager rm = new RunManager();
	
	public void run() {
		synchronized (this) {
			rm.fileReadWrite(threadName);
		}
	}
}
