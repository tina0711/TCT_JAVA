package test.bus3;


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
	ArrayList<STATION> stationlist = new ArrayList<STATION>();
	int bmsCount = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BUS bus = new BUS();
		
		bus.readStationFile();
		bus.readFile();
		bus.calcDistance();
		bus.writeFile();
		bus.stationBmsInfo();
		bus.writeStationFile();
	}
	
	public void readFile()
	{
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader("./BUS3.TXT"));
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
			System.out.println(line);
			
			if(line.contains("#")) {
				String time = line.substring(0, line.indexOf("#"));
				String busStr = line.substring(line.indexOf("#")+1);
				ArrayList<BUSINFO> buslist = new ArrayList<BUSINFO>();
				
				buslist = makeBusList(busStr, buslist);
				bms = new BMS(time, buslist);
				bmslist.add(bms);
				
			}else {
				predictBusSchedule(line);
			}
			
			bmsCount++;
        }
        
    	lastbuslist = bmslist.get(bmsCount-1).getBuslist();
        
        try {
        	br.close();
        } catch (IOException e) {
		// TODO Auto-generated catch block
        	e.printStackTrace();
        }

	}
	
	public void predictBusSchedule(String time) {
		
		int i = bmsCount-1;
		int prevPos = 0;
		int currPos = 0;
		int finalSpeed = 0;
		BUSINFO businfo = null;
		BMS bms = null;
		ArrayList<BUSINFO> buslist =  new ArrayList<BUSINFO>();
		
		for(int j=0; j<bmslist.get(i).buslist.size(); j++) {
	
			prevPos = Integer.parseInt(bmslist.get(i-1).getBuslist().get(j).getBusPos());
			currPos = Integer.parseInt(bmslist.get(i).getBuslist().get(j).getBusPos());
		
			int gap = currPos - prevPos; // 초속
			
			for(int k =1; k<stationlist.size(); k++) {
				
				
				int sta1 = Integer.parseInt(stationlist.get(k-1).getStationPos());
				int sta2 = Integer.parseInt(stationlist.get(k).getStationPos());
				
				if(sta1 < currPos && sta2 > currPos) {
					int limitSpeed = Integer.parseInt(stationlist.get(k).getStaLimitSpeed())*10/36;
					if(limitSpeed > gap) {
						finalSpeed = gap;
					}else {
						finalSpeed = limitSpeed;
					}
					
					break;
				}
			}
			
			businfo = new BUSINFO(bmslist.get(i).getBuslist().get(j).getBusNo(), String.format("%05d", currPos  + finalSpeed));
			buslist.add(businfo);
		}
		bms = new BMS(time, buslist);
		bmslist.add(bms);
		printbuslist(bmslist.get(bmsCount).getBuslist());
	}
	
	public void readStationFile() {
		BufferedReader br = null;
		STATION sta = null;
		
		try {
			br = new BufferedReader(new FileReader("./STATION.TXT"));
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
			
			String []tmp = line.split("#");
			
			sta = new STATION(tmp[0], tmp[1], tmp[2]);
			stationlist.add(sta);
			
	   }
	   
	   try {
       	br.close();
       } catch (IOException e) {
		// TODO Auto-generated catch block
       	e.printStackTrace();
       }
		
	}
	
	
	public void stationBmsInfo() {
		int minDist = 100000;
		int dist = 0;
		String minBusNo ="";
		int flag = 0;
		
		for(int i=0; i<stationlist.size(); i++) {
			minDist = 100000;
			
			for(int j=0; j<lastbuslist.size(); j++) {
				
				if(Integer.parseInt(stationlist.get(i).getStationPos()) < Integer.parseInt(lastbuslist.get(j).getBusPos())) {
					continue;
				}else {
					dist = Integer.parseInt(stationlist.get(i).getStationPos()) - Integer.parseInt(lastbuslist.get(j).getBusPos());
					if(minDist > dist) {
						
						minDist = dist;
						minBusNo = lastbuslist.get(j).getBusNo();
						flag = 1;
					}
				}
			}
			
			if(flag == 0) {
				stationlist.get(i).setMinArrivalBusNo("NOBUS");
				stationlist.get(i).setMinDistance("00000");
				
			}else{
				stationlist.get(i).setMinArrivalBusNo(minBusNo);
				stationlist.get(i).setMinDistance(String.format("%05d", minDist));
				flag = 0;
			}
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
			fw = new FileWriter("./RESULT3.TXT");
		} catch (IOException e) {
			// TODO Auto-generated catch bloc
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
	
public void writeStationFile(){
		
		FileWriter fw = null;
		try {
			fw = new FileWriter("./STATIONRESULT3.TXT");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<stationlist.size(); i++) {
			String data = stationlist.get(i).getStationNo() + "#" + stationlist.get(i).getMinArrivalBusNo() + "#" + stationlist.get(i).getMinDistance() + "\r\n";
            
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
class STATION{
	String stationNo;
	String stationPos;
	String staLimitSpeed;
	String minArrivalBusNo;
	String minDistance;
	
	STATION(String stationNo, String stationPos, String staLimitSpeed){
		this.stationNo = stationNo;
		this.stationPos = stationPos;
		this.staLimitSpeed = staLimitSpeed;
	}
	
	STATION(String stationNo, String stationPos, String staLimitSpeed, String minArrivalBusNo, String minDistance){
		this.stationNo = stationNo;
		this.stationPos = stationPos;
		this.staLimitSpeed = staLimitSpeed;
		this.minArrivalBusNo = minArrivalBusNo;
		this.minDistance = minDistance;
	}
	
	public String getStationNo() {
		return stationNo;
	}
	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}
	public String getStationPos() {
		return stationPos;
	}
	public void setStationPos(String stationPos) {
		this.stationPos = stationPos;
	}
	
	public String getStaLimitSpeed() {
		return staLimitSpeed;
	}
	public void setStaLimitSpeed(String staLimitSpeed) {
		this.staLimitSpeed = staLimitSpeed;
	}
	
	public String getMinArrivalBusNo() {
		return minArrivalBusNo;
	}

	public void setMinArrivalBusNo(String minArrivalBusNo) {
		this.minArrivalBusNo = minArrivalBusNo;
	}

	public String getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(String minDistance) {
		this.minDistance = minDistance;
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
