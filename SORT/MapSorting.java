package com.git.test.mapsort;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapSorting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map<String, Integer> map = new LinkedHashMap<>();
		map.put("Apple", 500);
		map.put("Watermelon", 1000);
		map.put("Banana", 1000);
		map.put("Melon", 200);
		map.put("Lemon", 1500);
		map.put("Grape", 1200);
		map.put("Lime", 1500);
		
		/* Sort by Key (ascending) */
		Map<String, Integer> result = sortMapByKey(map);
		for (Map.Entry<String, Integer> entry : result.entrySet()) {
		    System.out.println("Key: " + entry.getKey() + "\t"
		            + "Value: " + entry.getValue());
		}
		
		System.out.println("================================================");
		
		/* Sort by Key (descending) Comparator */
		sortMapByKeyDescending(map);
		System.out.println("================================================");
	    
		/* Sort by Value (ascending) Comparator */
		Map<String, Integer> result2 = sortMapByValue(map);
		for (Map.Entry<String, Integer> entry : result2.entrySet()) {
		    System.out.println("Key: " + entry.getKey() + "\t"
		            + "Value: " + entry.getValue());
		}
		
		System.out.println("================================================");
		
		/* Sort By Key(Ascending) and Value(Descending) */
		Map<String, Integer> result3 = sortMapByKeyAndValue(map);
		for (Map.Entry<String, Integer> entry : result3.entrySet()) {
		    System.out.println("Key: " + entry.getKey() + "\t"
		            + "Value: " + entry.getValue());
		}
	}
	/* Sort by Key (ascending) */
	public static LinkedHashMap<String, Integer> sortMapByKey(Map<String, Integer> map) {
	    List<Map.Entry<String, Integer>> entries = new LinkedList<>(map.entrySet());
	    Collections.sort(entries, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));

	    LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
	    for (Map.Entry<String, Integer> entry : entries) {
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	}
	
	/* Sort by Key (descending) Comparator */
	public static void sortMapByKeyDescending(Map<String, Integer> map) {
	    List<Map.Entry<String, Integer>> entries = new LinkedList<>(map.entrySet());
	    Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>(){
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            	
            	if(o1.getKey().compareTo(o2.getKey()) < 0) {
            		return 1;
            	}else if(o1.getKey().compareTo(o2.getKey()) > 0){
            		return -1;
            	}else {
            		return 0;
            	}
            	
            }
	    });
	    
		for (Map.Entry<String, Integer> entry : entries) {
	    System.out.println("Key: " + entry.getKey() + "\t"
	            + "Value: " + entry.getValue());
		}
	}
	
	/* Sort by Value (ascending) Comparator */
	public static LinkedHashMap<String, Integer> sortMapByValue(Map<String, Integer> map) {
	    List<Map.Entry<String, Integer>> entries = new LinkedList<>(map.entrySet());
	    Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>(){
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            	
            	if(o1.getValue() > o2.getValue()) {
            		return 1;
            	}else if(o1.getValue() < o2.getValue()){
            		return -1;
            	}else {
            		return 0;
            	}
            	
            }
	    });

	    LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
	    for (Map.Entry<String, Integer> entry : entries) {
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	}
	
	/* Sort By Key(Ascending) and Value(Descending) */
	public static LinkedHashMap<String, Integer> sortMapByKeyAndValue(Map<String, Integer> map) {
	    List<Map.Entry<String, Integer>> entries = new LinkedList<>(map.entrySet());
	    
	    Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>(){
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            	
            	if(o1.getValue() < o2.getValue()) {
            		return 1;
            	}else if(o1.getValue() > o2.getValue()){
            		return -1;
            	}else {
            		if(o1.getKey().compareTo(o2.getKey()) > 0) {
            			return 1;
            		}else if(o1.getKey().compareTo(o2.getKey()) < 0){
            			return -1;
            		}else {
            			return 0;
            		}
            	}
            }
	    });

	    LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
	    for (Map.Entry<String, Integer> entry : entries) {
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	}

}
