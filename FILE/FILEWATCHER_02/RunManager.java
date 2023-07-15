package test.com;

import java.io.File;
import java.io.IOException;

public class RunManager { 

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		RunManager runManager = new RunManager();
		File file = new File("./LOGFILE_A.TXT");
		
		System.out.println(file);
//		FileNewLineWatcher watcher = new FileNewLineWatcher(file);
//		watcher.readFile();
		
		
		FileContentWatcher contentWatcher = new FileContentWatcher();
		contentWatcher.fileWatcher();

	    //만약 종료하고 싶다면
//	    watcher.stop();
	}

}
