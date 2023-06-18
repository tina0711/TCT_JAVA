package test;

import java.util.List;
import java.util.Scanner;

public class RunManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		TextFileReader fileReader = new TextFileReader();

		try {
			new MyHttpServer().start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void readServiceFile(TextFileReader reader, String fileName, String path)
	{
		List<String> readStrArray = reader.readFile(fileName);
		for(String pathDetail : readStrArray) {
			String pathDetailArray[] = pathDetail.split("#");
			if(path.equals(pathDetailArray[0])) {
				String nextfileName = pathDetailArray[1];
				if(nextfileName.startsWith("Proxy-")) {
					readServiceFile(reader, nextfileName, path);
				}else {
					List<String> readFileStrArray = reader.readFile(nextfileName);
					System.out.println(readFileStrArray.get(0));
				}
				break;
			}
		}
	}
	
}
