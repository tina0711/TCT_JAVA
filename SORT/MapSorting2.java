package com.git.test.mapsort2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapSorting2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map<String, String> map = new LinkedHashMap<>();
		map.put("Apple", "500");
		map.put("Watermelon", "1000");
		map.put("Banana", "1000");
		map.put("Melon", "200");
		map.put("Lemon", "1500");
		map.put("Grape", "1200");
		map.put("Lime", "1500");

			
		/* Sort by Key (Ascending) */
		List<String> keyList = new ArrayList<>(map.keySet());
		keyList.sort((s1, s2)->s1.compareTo(s2));
		for (String key : keyList) {
		    System.out.println("Key: " + key + " / " + "value : " + map.get(key));
		}
		
		System.out.println("================================================");
		
		/* Sort by Key (Descending) */
		keyList.sort((s1, s2)->s2.compareTo(s1));
		for (String key : keyList) {
			System.out.println("Key: " + key +  " / " + "value : " + map.get(key));
		}
		
		System.out.println("================================================");
		
		Map<String, Integer> map2 = new HashMap<String, Integer>();
		map2.put("Apple", 500);
		map2.put("Watermelon", 1000);
		map2.put("Banana", 1000);
		map2.put("Melon", 200);
		map2.put("Lemon", 1500);
		map2.put("Grape", 1200);
		map2.put("Lime", 1500);
		
		/* Sort by Value (Ascending) */
		List<String> keySetList = new ArrayList<String>(map2.keySet());
		
		Collections.sort(keySetList, (o1, o2) -> (map2.get(o1).compareTo(map2.get(o2))));
		
		for(String key : keySetList) {
			System.out.println("key : " + key + " / " + "value : " + map.get(key));
		}
		
		/* Sort by Value (Descending) */
		Collections.sort(keySetList, new Comparator<String>() {
	            @Override
	            public int compare(String o1, String o2) {
	                return map2.get(o2).compareTo(map2.get(o1)); // 내림차순
	            }
	     });
		
		System.out.println("================================================");
		for(String key : keySetList) {
			System.out.println("key : " + key + " / " + "value : " + map.get(key));
		}
		
		
		
	}
}
