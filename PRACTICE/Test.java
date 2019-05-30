package test.com;

import java.util.HashMap;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		char[] arr= new char[255];
		
		arr = s1.toLowerCase().toCharArray();
		
		for(int i=0; i<s1.length(); i++) {
			if(map.containsKey(arr[i])) {
				map.put(arr[i], map.get(arr[i]+1));
			}else {
				map.put(arr[i], i);
			}
		}
		
		SortedSet <Character> sortedKey = new TreeSet<Character>(map.keySet());
		
		for(Character lan : sortedKey) {
			int count = map.get(lan);
		}
	}

}
