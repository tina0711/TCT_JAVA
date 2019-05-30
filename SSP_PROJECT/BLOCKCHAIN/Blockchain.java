package ssp.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
	
	List<Block> list = new ArrayList<Block>();
	
	Blockchain(){
		
	}
	public static void main(String[] args) {
		Blockchain blockchain = new Blockchain();
		blockchain.readFile();
		blockchain.printBlock();
		blockchain.findLastTran();
		
	}
	
	public void findLastTran() {
		
		int i=0; 
		int j=0;
		while(true) {
			for(j =0; j<list.size(); j++) {
				if(list.get(i).getCurrHash().equals(list.get(j).getPrevHash())) {
//					System.out.println("i = " + i + " j = " + j);
					i = j;
					break;
				}
			}
			
//			System.out.println(j + " " + list.size());
			if(j==list.size()){
				System.out.println("나가 " + i);
				break;
			}
		}
//		System.out.println("FINAL " + i);
//		System.out.println(list.get(i).getCurrHash() + " " + list.get(i).getPrevHash() + " " + list.get(i).getTranHash());
//		for(j=0; j<list.get(i).tranList.size(); j++) {
//			System.out.println("==" + list.get(i).getTranList().get(j).getSender() + " " + list.get(i).getTranList().get(j).getReceiver() + " " + list.get(i).getTranList().get(j).getFare());
//		}
		
		
	}
	
	
	
	public void readFile()
	{
		BufferedReader br = null;
	
		int lineCount = 0;
		int listLine = 0;
		try {
			br = new BufferedReader(new FileReader("chain.txt"));
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
//        	System.out.println(line);
        	
        	if(lineCount%2 ==0) {
        		String []arr = line.split("#");
        		Block block = new Block(arr[0], arr[1], arr[2]);
        		
        		list.add(listLine, block);
        		
//        		for(int i=0; i<list.size(); i++) {
//        			System.out.println("(1)" + list.get(i).getCurrHash() + " " + list.get(i).getPrevHash() + " " + list.get(i).getTranHash());
//        		}
        		
        		
        	}else{
        		
        		String []arr = line.split("#");
        		List<Tran> tr = new ArrayList<Tran>();
        		for(int i =0; i<arr.length; i++) {
        			String []arr2 = arr[i].split(",");
        			Tran tran = new Tran(arr2[0], arr2[1], arr2[2]);
        			tr.add(tran);
        		}
        		
           		list.get(listLine).setTranList(tr);
         		listLine++;
//        		for(int i=0; i<list.size(); i++) {
//        			System.out.println(list.get(i).getCurrHash() + " " + list.get(i).getPrevHash() + " " + list.get(i).getTranHash());
//        			for(int j=0; j<list.get(i).tranList.size(); j++) {
//        				System.out.println("==" + list.get(i).getTranList().get(j).getSender() + " " + list.get(i).getTranList().get(j).getReceiver() + " " + list.get(i).getTranList().get(j).getFare());
//        			}
//        		}
        	}
        	lineCount++;
        	
	    }
	    
        try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void printBlock() {
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i).getCurrHash() + " " + list.get(i).getPrevHash() + " " + list.get(i).getTranHash());
			for(int j=0; j<list.get(i).tranList.size(); j++) {
				System.out.println("  " + list.get(i).getTranList().get(j).getSender() + " " + list.get(i).getTranList().get(j).getReceiver() + " " + list.get(i).getTranList().get(j).getFare());
			}
		}
	}
}

class Tran{
	String sender;
	String receiver;
	String fare;
	
	Tran(String sender, String receiver, String fare){
		this.sender = sender;
		this.receiver = receiver;
		this.fare = fare;
		
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getFare() {
		return fare;
	}
	public void setFare(String fare) {
		this.fare = fare;
	}
}

class Block{
	String currHash;
	String prevHash;
	String tranHash;
	List<Tran> tranList;
	
	Block(String currHash, String prevHash, String tranHash, List<Tran> tranList){
		this.currHash = currHash;
		this.prevHash = prevHash;
		this.tranHash = tranHash;
		this.tranList = tranList;
	}
	
	Block(String currHash, String prevHash, String tranHash){
		this.currHash = currHash;
		this.prevHash = prevHash;
		this.tranHash = tranHash;
	}
	
	public String getCurrHash() {
		return currHash;
	}
	public void setCurrHash(String currHash) {
		this.currHash = currHash;
	}
	public String getPrevHash() {
		return prevHash;
	}
	public void setPrevHash(String prevHash) {
		this.prevHash = prevHash;
	}
	public String getTranHash() {
		return tranHash;
	}
	public void setTranHash(String tranHash) {
		this.tranHash = tranHash;
	}
	public List<Tran> getTranList() {
		return tranList;
	}
	public void setTranList(List<Tran> tranList) {
		this.tranList = tranList;
	}
	
}
