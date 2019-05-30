package test.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		String s = "AB C1245UE HJTLE 5356 djklgjlds , ASEKJ;L G";
		s = s.toLowerCase();
		s = s.replaceAll("[^a-zA-Z\\s]", "");
		
		String []arr = s.split(" ");
		for(int i=0; i<arr.length; i++) {
			if(map.containsKey(arr[i])) {
				map.put(arr[i], map.get(arr[i]+1));
				
			}else {
				map.put(arr[i], 1);
			}
		}
		
		List keys = new ArrayList(map.keySet());
		Collections.sort(keys);
		for(int inx=0; inx<keys.size(); inx++) {
			int count = map.get(keys.get(inx));
			System.out.println(keys.get(inx) + " : " + count);
		}
		
	}

}
