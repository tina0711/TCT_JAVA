package com.lgcns.tct.sortString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class SortString {

	ArrayList<String []> arrList = new ArrayList<String []>();
	String [][] arr = new String[4][4];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] strList = {"AA#22#CC#DD",
				             "EE#55#BB#AA",
				             "AA#11#BB#AA",
				             "EE#33#BB#AA"};
		
		SortString sorter = new SortString();
		sorter.loadData(strList);
		
		System.out.println("(1-1)String arr Sort 오름차순==================");
		sorter.stringArrSort();
		sorter.printStringArr();
		System.out.println("(1-2)String arr Sort 내림차순==================");
		sorter.stringArrSortDescending();
		sorter.printStringArr();
		
		System.out.println("(2)String arr Two type Sort================");
		sorter.stringArrSortTwoType();
		sorter.printStringArr();
		
		System.out.println("(3-1)ArrayList Sort 오름차순=======================");
		sorter.listSort();
		sorter.printList();
		
		System.out.println("(3-2)ArrayList Sort 내림차순=======================");
		sorter.listSortDescending();
		sorter.printList();
		
		System.out.println("(4)ArrayList Two type Sort==========================");
		sorter.listSortTwotype();
		sorter.printList();
	}
	
	void loadData(String strArr[])
	{
		int i = 0; 
		for(String str: strArr)
		{
			arrList.add(str.split("#"));
			arr[i++]= str.split("#");
		}
			
	}
	
	// 오름차순
	void listSort()
	{
		Collections.sort(arrList, new Comparator<String[]>() {

			@Override
			public int compare(String[] arg0, String[] arg1) {
				// TODO Auto-generated method stub
				return arg0[0].compareTo(arg1[0]);
				//return 0;
			}
			
		});
	}
	
	// 내림차순
	void listSortDescending()
	{
		Collections.sort(arrList, new Comparator<String[]>() {

			@Override
			public int compare(String[] arg0, String[] arg1) {
				// TODO Auto-generated method stub
				return arg1[0].compareTo(arg0[0]);
				//return 0;
			}
			
		});
	}
	
	void listSortTwotype()
	{
		Collections.sort(arrList, new Comparator<String[]>() {

			@Override
			public int compare(String[] arg0, String[] arg1) {
				// TODO Auto-generated method stub
				if(arg0[0].compareTo(arg1[0]) ==0)
				{
					return arg0[1].compareTo(arg1[1]);
				}
				else
				{
					return arg0[0].compareTo(arg1[0]);
				}
				//return 0;
			}
			
		});
	}

	void stringArrSort()
	{
		Arrays.sort(arr, new Comparator<String[]>() {

			@Override
			public int compare(String[] o1, String[] o2) {
				// TODO Auto-generated method stub
				if(o1[0]==null || o2[0]==null)
					return 0;
				return o1[0].compareTo(o2[0]);
			}
			
		});
	}
	
	// 내림차순
	void stringArrSortDescending()
	{
		Arrays.sort(arr, new Comparator<String[]>() {

			@Override
			public int compare(String[] o1, String[] o2) {
				// TODO Auto-generated method stub
				if(o1[0]==null || o2[0]==null)
					return 0;
				return o2[0].compareTo(o1[0]);
			}
			
		});
	}
	
	void stringArrSortTwoType()
	{
		Arrays.sort(arr, new Comparator<String[]>() {

			@Override
			public int compare(String[] o1, String[] o2) {
				// TODO Auto-generated method stub
				if(o1[0]==null || o2[0]==null)
					return 0;
				
				if(o1[0].compareTo(o2[0]) == 0)
				{
					return o1[1].compareTo(o2[1]);
				}
				else
				{
					return o1[0].compareTo(o2[0]);
				}
			}
			
		});
	}
	
	void printList()
	{
		for(String [] str: arrList)
		{
			System.out.println(str[0]+" "+str[1]+" "+str[2]);
		}
	}
	
	void printStringArr()
	{
		for(String [] str: arr)
		{
			System.out.println(str[0]+" "+str[1]+" "+str[2]);
		}
	}

}
