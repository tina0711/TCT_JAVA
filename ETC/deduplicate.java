package com.git.test.deduplicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class Deduplicate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deduplicate dedu = new Deduplicate();
		
		String []tmp = {"01", "95", "96", "97", "04", "05", "45", "52", "03", "04", "53", "02", "05", "45", "52","54", "55", "53", "97", "02"};
		ArrayList<String> list = new ArrayList<String>();
		
		for(int i=0; i<tmp.length; i++) {
			list.add(tmp[i]);
		}
		System.out.println(list.toString());
		
		list = dedu.deduplicateData(list);
		System.out.println("중복 제거 및 정렬");
		System.out.println(list.toString());
		
	}

	public ArrayList<String> deduplicateData(ArrayList<String> list){
		
    	/* 중복 제거 및 정렬 */
		TreeSet<String> distinctData = new TreeSet<String>(list);
		list = new ArrayList<String>(distinctData);
    	
  		Collections.sort(list);
  		
		return list;
	}
	
}
