package ssp.batch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Batch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Batch batch = new Batch();
		String message = "";
		int i=0;
		Scanner sc = new Scanner(System.in);
		List<Command> list = new ArrayList<Command>();
		List<String> strlist = new ArrayList<String>();
		Command cmd;
		
		while(true){
			System.out.println("입력하세요");
			
			message = sc.nextLine();
			System.out.println(message);
			if(message.equals("P")){
				System.out.println("종료");
				break;
			}else{
			
				String []arr = message.split("#");
				
				if(arr[0].equals("D")){
					cmd = new Command(arr[0], arr[1]);
				}else {
					cmd = new Command(arr[0], arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
				}
				
				
				if(arr[0].equals("C")){
					int existFlag =0;
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
		
		for(i=0; i<list.size(); i++){
			System.out.println(list.get(i).getProgName()  + "/ " +list.get(i).getGap());
		}
		
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
