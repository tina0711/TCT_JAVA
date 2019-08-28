package ss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class P02 {

    class BUS {
        
        int time;
        String line;
        int pos;
        int speed;
        
        String prevLine;
        String nextLine;
        int prevDist;
        int nextDist;
        
        public BUS(int time, String line, int pos, int speed) {
            this.time = time;
            this.line = line;
            this.pos = pos;
            this.speed = speed;
        }
        public String getLine() {
            return this.line;
        }
        
        public int getPos() {
            return this.pos;
        }
        public int getTime() {
            return this.time;
        }
        public void setMaxSpeed(int maxSpeed) {
            this.speed = maxSpeed;
        }
        public int getSpeed() {
            return this.speed;
        }
        
        public int getNextPos(int nextTime) {
            return this.pos + this.speed*(nextTime - this.time);
        }
        
        public void setDist(String prevLine, int prevDist,String nextLine, int nextDist) {
            this.prevLine = prevLine;
            this.nextLine = nextLine;
            this.prevDist = prevDist;
            this.nextDist = nextDist;
        }
        
        public void printBus() {
            System.out.println("시간:"+time+" 버스:"+line+" 위치:"+pos+" 속도:"+speed+" 앞차:"+prevLine+" 앞차거리:"+prevDist+"뒷차:"+nextLine+" 뒷차거리:"+nextDist);
        }
        
        public String setWriteData() {
            return line+"#"+prevLine+"#"+String.format("%05d", prevDist)+"#"+nextLine+"#"+String.format("%05d", nextDist);
        }
    }
    
    class STATION {
        
        String station;
        int pos;
        int speedLimit;
        String nextBusLine;
        int nextBusLineDist;
        String arrTime;
        
        public STATION(String station, int pos, int speedLimit) {
            this.station = station;
            this.pos = pos;
            this.speedLimit = speedLimit;
            this.nextBusLine = "NOBUS";
            this.nextBusLineDist = 0;
            this.arrTime = "00:00:00";
        }
        public String getStation() {
            return this.station;
        }
        
        public int getPos() {
            return this.pos;
        }
        public int getSpeedLimit() {
            return this.speedLimit;
        }
        public void printStation() {
            System.out.println("정류장:"+station+" 위치:"+pos+" 제한속도:"+speedLimit+" 도착예정버스노선:"+nextBusLine+" 거리:"+nextBusLineDist);
        }
        public void setNextBus(String nextBusLine, int nextBusLineDist, String arrTime) {
            if(nextBusLineDist <= 0) {                
                this.nextBusLine = "NOBUS";
                this.nextBusLineDist = 0;
                this.arrTime = "00:00:00";
            }else {
                this.nextBusLine = nextBusLine;
                this.nextBusLineDist = nextBusLineDist;
                this.arrTime = arrTime;
            }
        }
        
        public String setWriteData() {
            System.out.println(station+"#"+nextBusLine+"#"+String.format("%05d", nextBusLineDist)+"#"+arrTime);
            return station+"#"+nextBusLine+"#"+String.format("%05d", nextBusLineDist)+"#"+arrTime;
        }
    }

    public static void main(String[] args) {
        
        ArrayList<STATION> staList = new ArrayList<STATION>();
        
        String rootPath = new File(".").getAbsolutePath();
        ArrayList<String> busFileData = setData(rootPath+"\\src\\ss\\BUS3.TXT");
        ArrayList<String> staFileData = setData(rootPath+"\\src\\ss\\STATION.TXT");

        for(String str: staFileData) {
            String[] data = str.split("#");
            staList.add(new P02().new STATION(data[0],Integer.parseInt(data[1]),Integer.parseInt(data[2])));
        }

        //정류장정보 위치기준 오름차순 sort
        Comparator<STATION> staPosComparator = new Comparator<STATION>() {
            @Override
            public int compare(STATION o1, STATION o2) {
                return o1.getPos() - o2.getPos();
            }
        };
        Collections.sort(staList,staPosComparator);
        
        ArrayList<BUS> prevBusList = null;
        int idx = 0;
        for(String str: busFileData)
        {
            ArrayList<BUS> busList = new ArrayList<BUS>();
            String[] data = str.split("#");
            int speed = 0;
            int time = Integer.parseInt(data[0].split(":")[0])*3600+Integer.parseInt(data[0].split(":")[1])*60+Integer.parseInt(data[0].split(":")[2]);
            
            for(int i = 0;i<data.length/2;i++) {
                if(prevBusList != null && time != prevBusList.get(i).getTime()) {
                    speed = (Integer.parseInt(data[2*i+2]) - prevBusList.get(i).getPos())/(time - prevBusList.get(i).getTime());
                }
                busList.add(new P02().new BUS(time,data[2*i+1],Integer.parseInt(data[2*i+2]), speed));
            }
            if(busList.size()>0) {
                prevBusList = busList;
            }else {
                for(BUS b : prevBusList) {
                    busList.add(new P02().new BUS(time,b.getLine(), b.getNextPos(time), b.getSpeed()));                        
                }
            }
            Comparator<BUS> posComparator = new Comparator<BUS>() {
                @Override
                public int compare(BUS o1, BUS o2) {
                    return o1.getPos() - o2.getPos();
                }
            };
            
            Collections.sort(busList, posComparator);
            
            //앞뒤차 정보 세팅
            for(int j = 0;j<busList.size();j++) {
                if(j==0) {
                    busList.get(j).setDist("NOBUS", 0, busList.get(j+1).line, busList.get(j+1).pos - busList.get(j).pos);
                }else if(j==busList.size()-1) {
                    busList.get(j).setDist(busList.get(j-1).line, busList.get(j).pos - busList.get(j-1).pos,"NOBUS", 0);
                }else {
                    busList.get(j).setDist(busList.get(j-1).line, busList.get(j).pos - busList.get(j-1).pos, busList.get(j+1).line, busList.get(j+1).pos - busList.get(j).pos);
                }
                for(STATION s: staList) {
                    //다음버스 정보 세팅
                    String arrTime = "00:00:00";
                    if(busList.get(j).getPos() <= s.getPos()) {
                        if(busList.get(j).getSpeed()>0) {
                            //최대 속도 제한
                            int maxSpeed = Math.min(busList.get(j).getSpeed(),s.getSpeedLimit()*1000/3600);
                            busList.get(j).setMaxSpeed(maxSpeed);
                            
                            arrTime = String.format("%02d", (time + (int)Math.ceil((double)(s.getPos() - busList.get(j).getPos())/maxSpeed))/3600)     +":"+
                                      String.format("%02d",((time + (int)Math.ceil((double)(s.getPos() - busList.get(j).getPos())/maxSpeed))%3600)/60) +":"+
                                      String.format("%02d",((time + (int)Math.ceil((double)(s.getPos() - busList.get(j).getPos())/maxSpeed))%3600)%60) ;
                        }
                        s.setNextBus(busList.get(j).getLine(), s.getPos() - busList.get(j).getPos(), arrTime);
                    }
                }
            }
            
            Comparator<BUS> lineComparator = new Comparator<BUS>() {
                @Override
                public int compare(BUS o1, BUS o2) {
                    return o1.getLine().compareTo(o2.getLine());
                }
            };
            Collections.sort(busList,lineComparator);
            
            Comparator<BUS> timeComparator = new Comparator<BUS>() {
                @Override
                public int compare(BUS o1, BUS o2) {
                    return o1.getTime() - o2.getTime();
                }
            };
            Collections.sort(busList,timeComparator);
            
            //최종 출력
            if(idx++ == busFileData.size()-1) {
                for(BUS b :busList) {
                    b.printBus();
                }
                for(STATION s :staList) {
                    s.printStation();
                }
                writeBusData(busList, rootPath+"\\src\\ss\\RESULT3.TXT");
                writeStaData(staList, rootPath+"\\src\\ss\\STATIONRESULT3.TXT");
            }
        }
    }
    
    public static ArrayList<String> setData(String path) {
        ArrayList<String> readData = new ArrayList<String>();
        try {
            // 파일 객체 생성
            File file = new File(path);
            // 입력 스트림 생성
            FileReader filereader = new FileReader(file);
            // 입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                if(line.equals("PRINT")) {
                    break;
                }
                readData.add(line);
            }
            // .readLine()은 끝에 개행문자를 읽지 않는다.
            bufReader.close();
    
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return readData;
    }

    public static void writeBusData(ArrayList<BUS> busList, String path) {
       
        try{
            //파일 객체 생성
            File file = new File(path);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            
            if(file.isFile() && file.canWrite()){
                for(BUS b : busList) {
                    bufferedWriter.write(b.setWriteData());
                    //개행문자쓰기
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
        }catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void writeStaData(ArrayList<STATION> staList, String path) {
        
        try{
            //파일 객체 생성
            File file = new File(path);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            
            if(file.isFile() && file.canWrite()){
                for(STATION s : staList) {
                    bufferedWriter.write(s.setWriteData());
                    //개행문자쓰기
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
        }catch (IOException e) {
            System.out.println(e);
        }
    }
}