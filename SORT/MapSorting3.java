package com.git.test.mapsort3;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapSorting3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeMap<String,PERSON> map = new TreeMap<String,PERSON>();
		
		map.put("01", new PERSON("PENSOO", 50, "SEOUL"));
		map.put("05", new PERSON("LION", 100, "JEJU"));
		map.put("02", new PERSON("APEACH", 100, "DAEJUN"));
		map.put("03", new PERSON("JOODOL", 50, "HAWAI"));
		map.put("04", new PERSON("HANDORI", 200, "BUSAN"));
		
		for(String key : map.keySet()) {
			map.get(key).print();
		}
		
		List<Map.Entry<String, PERSON>> list = new LinkedList<>(map.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<String, PERSON>>() {
            @Override
            public int compare(Map.Entry<String, PERSON> o1, Map.Entry<String, PERSON> o2) {
            	if(o1.getValue().getEffect() > o2.getValue().getEffect()) {
            		return -1;
            	}else if(o1.getValue().getEffect() < o2.getValue().getEffect()) {
            		return 1;
            	}else
            	{
            		if(o1.getValue().getPlace().compareTo(o2.getValue().getPlace())> 0) {
            			return 1;
	        		}else {
	        			return -1;
	        		}
            	}
            }
        });
		
        Map<String, PERSON> sortedMap = new LinkedHashMap<>(); 
        for(Iterator<Map.Entry<String, PERSON>> iter = list.iterator(); iter.hasNext();){
            Map.Entry<String, PERSON> entry = iter.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        System.out.println("=================================");
    	for(String key : sortedMap.keySet()) {
    		sortedMap.get(key).print();
		}
		
	}
}

class PERSON{
	
	String name;
	int effect;
	String place;
	
	PERSON(String name, int effect, String place){
		this.name = name;
		this.effect = effect;
		this.place = place;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEffect() {
		return effect;
	}
	public void setEffect(int effect) {
		this.effect = effect;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	void print() {
		System.out.println(this.name + " " + this.effect + " " + this.place);
	}
}