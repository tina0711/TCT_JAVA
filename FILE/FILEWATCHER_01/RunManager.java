package test.com;

import java.io.File;

public class RunManager { 

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		RunManager runManager = new RunManager();
		File file = new File("./LOGFILE_A.TXT");
		
		System.out.println(file);
		FileLineWatcher watcher = new FileLineWatcher(file);
	    Thread thread = new Thread(watcher);
//	    thread.setDaemon(true);
	    thread.start();
	    
	    thread.join();

	    //만약 종료하고 싶다면
//	    watcher.stop();
	}

}
