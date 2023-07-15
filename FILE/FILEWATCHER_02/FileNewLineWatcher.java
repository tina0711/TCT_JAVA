package test.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileNewLineWatcher {
	private static final int DELAY_MILLIS = 1000;

	private boolean isRun;
	// 대상 파일
	private final File file;

	public FileNewLineWatcher(File file) {
		this.file = file;
	}

	public void readFile() {
		BufferedReader br = null;
		
	    System.out.println("Start to tail a file - " + file.getName());

	    isRun = true;
	    if (!file.exists()) {
	      System.out.println("Failed to find a file - " + file.getPath());
	    }
		
		try {
			br = new BufferedReader(new FileReader("./LOGFILE_A.TXT"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (isRun) {
			String line = null;
			try {
				line = br.readLine();
				if(line != null) {
					System.out.println("New Line Added - " + line);
					
				}else {
					Thread.sleep(DELAY_MILLIS);
				}
				
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 스레드 중지 기능.
	 */
	public void stop() {
		isRun = false;
	}
}
