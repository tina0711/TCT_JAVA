package test.gate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class GATE {

	TreeMap<String, ArrayList<String>> empMap = new TreeMap<String, ArrayList<String>>();
	TreeMap<String, EMPGATE> empGateMap = new TreeMap<String, EMPGATE>();
	
	public GATE() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GATE gate = new GATE();
		
		Scanner sc = new Scanner(System.in);
		
		gate.subDirListAll("./ORGFILE");
//		gate.printMap();
		gate.makeGateList();
		gate.printGateMap();
		
		while(true) {
			String input = sc.nextLine();
			String []tmp = input.split("#");
			gate.writeFile(tmp[0], tmp[1]);
		}

	}
	
	public void printMap() {
		for(String key : empGateMap.keySet()) {
			System.out.print(key + " : ");
			System.out.println(empGateMap.get(key).getOrgList());
		}
	}
	
	public void printGateMap() {
		for(String key : empGateMap.keySet()) {
			System.out.print(key + " : ");
			System.out.println(empGateMap.get(key).getGateList());
		}
	}
	
	public void subDirList(String source, String directory, String empNo){
		File dir = new File(source); 
		File[] fileList = dir.listFiles(); 
		try{
			for(int i = 0 ; i < fileList.length ; i++){
				File file = fileList[i]; 
				if(file.isFile()){
//					System.out.println("\t 파일 = " + file.getName());
				}else if(file.isDirectory()){
//					System.out.println("디렉토리 = " + file.getName());
					if(file.getName().equals(directory)) {
						String filePath = file.getCanonicalPath().toString() + "\\ORGGATE.TXT";
						readFile(filePath, empNo);
					}else {
						subDirList(file.getCanonicalPath().toString(), directory, empNo); 
					}
				}
			}
		}catch(IOException e){
			
		}
	}

	public void subDirListAll(String source){
		
		File dir = new File(source); 
		File[] fileList = dir.listFiles(); 
		String empNo = "";
		
		EMPGATE empGate = null;
		ArrayList<String> orgList  = null;
		
		try{
			for(int i = 0 ; i < fileList.length ; i++){
				File file = fileList[i]; 
				if(file.isFile()){
					if(file.getName().endsWith("TXT") && !file.getName().equals("ORGGATE.TXT")) {
						empNo = file.getName().substring(0, file.getName().indexOf("."));
						int pos = file.getParent().indexOf("ORGFILE");
						String [] tmp = file.getParent().substring(pos).split("\\\\");
						
						if(empGateMap.containsKey(empNo)) {
							
							empGate = empGateMap.get(empNo);
							
							orgList = empGate.getOrgList();
							
							for(int j=0; j<tmp.length; j++) {
								orgList.add(tmp[j]);
							}
							TreeSet<String> distinctData = new TreeSet<String>(orgList);
							orgList = new ArrayList<String>(distinctData);
							empGate.setOrgList(orgList);
							
				    	}else{
				    		
				    		orgList = new ArrayList<String>();
							for(int j=0; j<tmp.length; j++) {
								
								orgList.add(tmp[j]);
							}
							TreeSet<String> distinctData = new TreeSet<String>(orgList);
							orgList = new ArrayList<String>(distinctData);
				    		
				    		empGate = new EMPGATE(empNo, orgList);
				    		
				    		empGateMap.put(empNo, empGate);
				    		
				    	}
						
					}
				}else if(file.isDirectory()){
//					System.out.println("디렉토리 = " + file.getName());
					subDirListAll(file.getCanonicalPath().toString()); 
				}
			}
		}catch(IOException e){
			
		}
	}
	
	public void makeGateList() {
		
		for(String key : empGateMap.keySet()){
	
			for(int i=0; i<empGateMap.get(key).getOrgList().size(); i++) {
				String directory = empGateMap.get(key).getOrgList().get(i);
				subDirList("./", empGateMap.get(key).getOrgList().get(i), key);
			}
		}
	}
	
	public void readFile(String fileName, String empNo)
	{
		BufferedReader br = null;
		ArrayList<String> list = null;
		File f = new File(fileName);
		
		if(f.exists()) {
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
//		    	System.out.println(line);
		    	
		    	if(empGateMap.containsKey(empNo)) {
		    	 	if(empGateMap.get(empNo).gateList != null) {
		    			list = empGateMap.get(empNo).gateList;
		    	 	}else {
		    	 	 	list = new ArrayList<String>();
		    	 	}
		    	}else{
		    	  	list = new ArrayList<String>();
		    	}
//		    	
		    	String []tmp2 = line.split("-");
		    	
		    	int start = Integer.parseInt(tmp2[0]);
		    	int end = Integer.parseInt(tmp2[1]);
		    	
		    	for(int i=start; i<=end; i++) {
		    		list.add(String.format("%02d", i));
		    	}
		  	
		    	/* 중복 제거 */
				TreeSet<String> distinctData = new TreeSet<String>(list);
				list = new ArrayList<String>(distinctData);
		    	
		  		Collections.sort(list);
		  		empGateMap.get(empNo).setGateList(list);
		    	
	        }
		        
	        try {
	        	br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		} else {
		      //System.out.println("파일 없음");            
		}
		

	}
	
	public void writeFile(String empNo, String gate)
	{
		FileWriter fw = null;
	    String data = "";
	    String gateStr = "";
	    boolean flag = false;
	    
	    try {
			fw = new FileWriter("./RESULT.TXT", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    for(String key : empGateMap.keySet()) {
	    	
	    	if(key.equals(empNo)) {
	    	    ArrayList<String> list = new ArrayList<String>();
	    	    list = empGateMap.get(key).getGateList();
	    		for(int i=0; i<list.size(); i++) {
	    	 		if(gate.equals(list.get(i))){
	    	 			flag = true;
	    	 		}
	    	 		
	    	 		if(i == list.size()-1) {
	    	 			gateStr += list.get(i);
	    	 		}else {
	    	 			gateStr += list.get(i) + ",";
	    	 		}
	    		}
	   
	    	}
	    }
	    
	    if(flag == true) {
	    	data = empNo + "#" + gate + "#" + "Y" + "#"+ gateStr +"\r\n";
	    }else {
	    	if(gateStr.length() != 0) {
	    		data = empNo + "#" + gate + "#" + "N" + "#"+ gateStr+"\r\n";
	    	}else {
	    		data = empNo + "#" + gate + "#" + "N" + "\r\n";
	    	}
	    	
	    }
	    
	    flag = false;
	    
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
	
	class EMPGATE{
		
		String empNo;
		String openYN;
		String inputGate;
		ArrayList<String> orgList;
		ArrayList<String> gateList;
		
		public EMPGATE() {
		}
		
		public EMPGATE(String empNo, ArrayList<String> orgList) {
			this.empNo = empNo;
			this.orgList = orgList;
		}

		public ArrayList<String> getOrgList() {
			return orgList;
		}

		public void setOrgList(ArrayList<String> orgList) {
			this.orgList = orgList;
		}

		public ArrayList<String> getGateList() {
			return gateList;
		}

		public void setGateList(ArrayList<String> gateList) {
			this.gateList = gateList;
		}
	}
	
	

}
