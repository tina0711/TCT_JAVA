package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader {
	
	public List<String> readFileToList(String fileName)
	{
		BufferedReader br = null;
		List<String> strArray = new ArrayList<String>();
		
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        while(true) {
			String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (line==null) 
        		break;
        	strArray.add(line);
//        	System.out.println(line);
        }
        
        try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return strArray;
	}
	
	public String readFileContents(String fileName) {
		String fileContent = String.join("\n", this.readFileToList(fileName));
		return fileContent;
	}
	
	public void readFile()
	{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("./LOGFILE_A.TXT"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        while(true) {
			String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (line==null) break;
        	System.out.println(line);
        	
        }
        
        try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String readFileToString(String fileName)
	{
		BufferedReader br = null;
		String str ="";
		StringBuilder buffer = new StringBuilder();
		
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	        while(true) {
				String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if (line==null) break;
	        	System.out.println(line);
	        	buffer.append(line);
	        	
	        }
	        try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
	    str = buffer.toString();    
	    return str;

	}
	
	public void writeFile()
	{
		FileWriter fw = null;
		String data = "";
		try {
			fw = new FileWriter("./REPORT_A.TXT");
			// fw = new FileWriter("./REPORT_A.TXT", true); // append할 때
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	data = "text" + "\r\n";
    	
        try {
			fw.write(data);
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
