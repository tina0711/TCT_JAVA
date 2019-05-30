package ssp.batch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Batch {
	
	static List<Command> list = new ArrayList<Command>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Batch batch = new Batch();
		String message = "";
		int i=0;
		Scanner sc = new Scanner(System.in);
	
		List<String> strlist = new ArrayList<String>();
		Command cmd;
		
		while(true){
			System.out.println("입력하세요");
			
			message = sc.nextLine();
//			System.out.println(message);
			if(message.equals("P")){
				System.out.println("종료");
				break;
			}else{
			
				String []arr = message.split("#");
				
				System.out.println(list.size());
				if(list.size() == 0) {
					if((arr[0].equals("U")) || (arr[0].equals("D"))){
						System.out.println("첫번째 cmd로 u, d가 올 수 없음");
						continue;
					}
				}
				
				if(arr[0].equals("D")){
					cmd = new Command(arr[0], arr[1]);
				}else {
					cmd = new Command(arr[0], arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
				}
				
				
				if(arr[0].equals("C")){
					int existFlag = 0;
					
					int result = batch.subDirList("./BATCH", cmd.getProgName());
					
					if(batch.subDirList("./BATCH", cmd.getProgName())==0){
						System.out.println("프로그램 존재 하지 않음");
						continue;
					}
					for(i=0; i<list.size(); i++) {
						if(cmd.getProgName().equals(list.get(i).getProgName())) {
							System.out.println("프로그램 이미 있음");
							existFlag = 1;
							break;
						}
					}
					
					if(existFlag != 0) {
						continue;
					}
					
					list.add(cmd);
					
				}else if(arr[0].equals("U")){
					for( i=0; i<list.size(); i++){
						if(list.get(i).getProgName().equals(cmd.getProgName())){
							if(list.get(i).getCmd().equals("C")){
								list.get(i).setCmd(cmd.getCmd());
								list.get(i).setGap(cmd.getGap());
								list.get(i).setProgTime(cmd.getProgTime());
							}
						}
					}
				}else if(arr[0].equals("D")){
					for(i=0; i<list.size(); i++){
						 if(list.get(i).getProgName().equals(cmd.getProgName())){
							 System.out.println("삭제");
							list.remove(i);
						}
					}
				}
			}
		}
		
		batch.printList(list);
		
		batch.listSort();
		System.out.println("=====================================");
		batch.printList(list);
		batch.writeLogFile(list);
		
		
	}
	
	public void writeLogFile(List<Command> list) {
		FileWriter fw = null;
		try {
			fw = new FileWriter("./result.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        for(int i=0; i<list.size(); i++) {
            String data = list.get(i).getProgName()+"#"+list.get(i).getGap() +"#" + list.get(i).getProgTime()+"\r\n";
            try {
            	fw.write(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public int subDirList(String source, String fileName){
		File dir = new File(source); 
		int result =0;
		File[] fileList = dir.listFiles(); 
		
		try{
			for(int i = 0 ; i < fileList.length ; i++){
				File file = fileList[i]; 
				
				if(file.isFile()){
					if(fileName.equals(file.getName())) {
						result = 1;
						break;
					}
				}else if(file.isDirectory()){
//					System.out.println("디렉토리이름= " + file.getName());
					subDirList(file.getCanonicalPath().toString(), fileName); 
				}
			}
		}catch(IOException e){
			
		}
		
		return result;
	}
	
	void listSort()
	{
		Collections.sort(list, new Comparator<Command>() {

			@Override
			public int compare(Command p1, Command p2) {
				// TODO Auto-generated method stub
				if(p1.getGap() > p2.getGap()){
					return 1;
				}
				else if(p1.getGap() < p2.getGap()){
					return -1;
				}else{
					return p1.getProgName().compareTo(p2.getProgName());
				}
			}
			
		});
	}
	
	public void printList(List<Command> list) {
		for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i).getProgName()  + " | " +list.get(i).getGap() + " | " +list.get(i).getProgTime());
		}
	}


}

class Command {
	private String cmd;
	private String progName;
	private int gap;
	private int progTime;
	
	public Command(String cmd, String progName, int gap, int progTime){
		this.cmd = cmd;
		this.progName = progName;
		this.gap = gap;
		this.progTime = progTime;
	}
	
	public Command(String cmd, String progName){
		this.cmd = cmd;
		this.progName = progName;
	}
	
	
	public String getCmd() {
		return cmd;
	}
	
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public String getProgName() {
		return progName;
	}
	
	public void setProgName(String progName) {
		this.progName = progName;
	}
	public int getGap() {
		return gap;
	}
	public void setGap(int gap) {
		this.gap = gap;
	}
	public int getProgTime() {
		return progTime;
	}
	public void setProgTime(int progTime) {
		this.progTime = progTime;
	}
	
	
	
}
