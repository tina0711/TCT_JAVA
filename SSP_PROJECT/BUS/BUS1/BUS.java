package test.bus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BUS {

	ArrayList<BMS> bmslist = new ArrayList<BMS>();
	ArrayList<BUSINFO> lastbuslist = new ArrayList<BUSINFO>();
	ArrayList<BUSRESULT> busresultlist = new ArrayList<BUSRESULT>();
	int bmsCount = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BUS bus = new BUS();
		bus.readFile();
		bus.calcDistance();
		bus.writeFile();
	}
	
	public void readFile()
	{
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader("./BUS.TXT"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        while(true) {
        	
			String line = null;
			BMS bms = null;
	
			
			try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (line==null) break;
//			System.out.println(line);
			
			if(line.equals("PRINT")){
				
				lastbuslist = bmslist.get(bmsCount-1).getBuslist();
//				printbuslist(lastbuslist);
			
				break;
				
			}else {
				
				String time = line.substring(0, line.indexOf("#"));
				String busStr = line.substring(line.indexOf("#")+1);
				ArrayList<BUSINFO> buslist = new ArrayList<BUSINFO>();
				
//				System.out.println(time);
//				System.out.println(busStr);
//				
				buslist = makeBusList(busStr, buslist);
				
				bms = new BMS(time, buslist);
				bmslist.add(bms);
				bmsCount++;
			}
        }
        try {
        	br.close();
        } catch (IOException e) {
		// TODO Auto-generated catch block
        	e.printStackTrace();
        }

	}
	
	// 오름차순
		void listSort()
		{
			Collections.sort(lastbuslist, new Comparator<BUSINFO>() {

				public int compare(BUSINFO p1, BUSINFO p2) {
					// TODO Auto-generated method stub
					if(Integer.parseInt(p1.getBusPos()) > Integer.parseInt(p2.getBusPos()))
					{
						return 1;
					}
					else if(Integer.parseInt(p1.getBusPos()) < Integer.parseInt(p2.getBusPos()))
					{
						return -1;
					}
					else
					{
						return 0;
					}
				}
				
			});
		}
		

	
	public void calcDistance() {
		
		int prevGap = 0;
		int nextGap = 0;
		BUSRESULT result = null;
		
		//위치로 sort
//		System.out.println("위치 sort");
		
		listSort();
		
//		printbuslist(lastbuslist);
		
		for(int i=0; i<lastbuslist.size(); i++) {
			
			if(i==0) {
				
				nextGap = Integer.parseInt(lastbuslist.get(i+1).getBusPos()) - Integer.parseInt(lastbuslist.get(i).getBusPos());
				result = new BUSRESULT(lastbuslist.get(i).getBusNo(), "NOBUS", "00000", lastbuslist.get(i+1).getBusNo(), String.format("%05d", nextGap));
				
			}else if(i==lastbuslist.size()-1) {
				prevGap = Integer.parseInt(lastbuslist.get(i).getBusPos()) - Integer.parseInt(lastbuslist.get(i-1).getBusPos());
				result = new BUSRESULT(lastbuslist.get(i).getBusNo(), lastbuslist.get(i-1).getBusNo(), String.format("%05d", prevGap),  "NOBUS", "00000");
				
			}else {
				prevGap = Integer.parseInt(lastbuslist.get(i).getBusPos()) - Integer.parseInt(lastbuslist.get(i-1).getBusPos());
				nextGap = Integer.parseInt(lastbuslist.get(i+1).getBusPos()) - Integer.parseInt(lastbuslist.get(i).getBusPos());
				result = new BUSRESULT(lastbuslist.get(i).getBusNo(), lastbuslist.get(i-1).getBusNo(), String.format("%05d", prevGap), lastbuslist.get(i+1).getBusNo(), String.format("%05d", nextGap));
			}
			
			busresultlist.add(result);
		}
		
		// 버스 번호로 sort
//		System.out.println("버스 번호로 sort");
		busNoListSort();
	}
	
	public void busNoListSort() {
		
		Collections.sort(busresultlist, new Comparator<BUSRESULT>() {

			public int compare(BUSRESULT p1, BUSRESULT p2) {
				// TODO Auto-generated method stub
				if(p1.getBusNo().compareTo(p2.getBusNo())>0)
				{
					return 1;
				}
				else if(p1.getBusNo().compareTo(p2.getBusNo()) < 0)
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
			
		});
		
	}
	
	public void writeFile(){
		
		FileWriter fw = null;
		try {
			fw = new FileWriter("./RESULT.TXT");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<busresultlist.size(); i++) {
            String data = busresultlist.get(i).getBusNo() + "#"+ busresultlist.get(i).getPrevBusNo() + "#" + busresultlist.get(i).getPrevBusDist() + "#" +  busresultlist.get(i).getNextBusNo() + "#" + busresultlist.get(i).getNextBusDist() + "\r\n";
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

	
	public void printbuslist(ArrayList<BUSINFO> lastbuslist) {
		
		
		System.out.println("lastbuslist");
		for(int i=0; i<lastbuslist.size(); i++) {
			System.out.println(lastbuslist.get(i).getBusNo() + " " + lastbuslist.get(i).getBusPos());
		}
	}
	
	public ArrayList<BUSINFO> makeBusList(String line, ArrayList<BUSINFO> buslist) {
		
		String []tmp = line.split("#");
		
		for(int i=0; i<tmp.length-1; i+=2) {
	
			String busNo = tmp[i];
			String busPos = tmp[i+1];

			BUSINFO info = new BUSINFO(busNo, busPos);
			buslist.add(info);
			
		}
		
		return buslist;
		
	}

}

class BUSRESULT{
	
	String time;
	String busNo;
	String prevBusNo;
	String prevBusDist;
	String nextBusNo;
	String nextBusDist;
	
	BUSRESULT(String busNo, String prevBusNo, String prevBusDist, String nextBusNo, String nextBusDist){
		this.busNo = busNo;
		this.prevBusNo = prevBusNo;
		this.prevBusDist = prevBusDist;
		this.nextBusNo = nextBusNo;
		this.nextBusDist = nextBusDist;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBusNo() {
		return busNo;
	}
	public void setBusNo(String busNo) {
		this.busNo = busNo;
	}
	public String getPrevBusNo() {
		return prevBusNo;
	}
	public void setPrevBusNo(String prevBusNo) {
		this.prevBusNo = prevBusNo;
	}
	public String getPrevBusDist() {
		return prevBusDist;
	}
	public void setPrevBusDist(String prevBusDist) {
		this.prevBusDist = prevBusDist;
	}
	public String getNextBusNo() {
		return nextBusNo;
	}
	public void setNextBusNo(String nextBusNo) {
		this.nextBusNo = nextBusNo;
	}
	public String getNextBusDist() {
		return nextBusDist;
	}
	public void setNextBusDist(String nextBusDist) {
		this.nextBusDist = nextBusDist;
	}
	
	
}

class BMS{
	
	String time;
	String busNo;
	ArrayList<BUSINFO> buslist;
	
	BMS(String time, ArrayList<BUSINFO> buslist){
		this.time = time;
		this.buslist = buslist;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public ArrayList<BUSINFO> getBuslist() {
		return buslist;
	}
	public void setBuslist(ArrayList<BUSINFO> buslist) {
		this.buslist = buslist;
	}

}

class BUSINFO{
	
	String busNo;
	String busPos;
	
	BUSINFO(String busNo, String busPos){
		this.busNo = busNo;
		this.busPos = busPos;
	}
	
	public String getBusNo() {
		return busNo;
	}
	
	public void setBusNo(String busNo) {
		this.busNo = busNo;
	}
	
	public String getBusPos() {
		return busPos;
	}
	
	public void setBusPos(String busPos) {
		this.busPos = busPos;
	}
	
}
