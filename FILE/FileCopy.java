package test.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File directory = new File("./INPUT");
		File[] fList = directory.listFiles();
		for(File file : fList) {
			System.out.println(file.getName() + ": " + file.length()+"bytes");
			 if(file.length() > 1024) {
				 
				 MyCopyFile(file.getName());
			 }
		}
	}

	static void MyCopyFile(String filename) {
		final int BUFFER_SIZE = 512;
		int readLen;
		
		try {
		
		File destFolder = new File("./OUTPUT");
		if(!destFolder.exists()) {
			System.out.println("MKDIR");
			destFolder.mkdir();
		}
		
		InputStream inputStream = new FileInputStream("./INPUT/"+filename);
		OutputStream outputStream = new FileOutputStream("./OUTPUT/"+filename);

		byte [] buffer = new byte[BUFFER_SIZE];
		
		while((readLen = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, readLen);
		}
	
		inputStream.close();
		outputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
