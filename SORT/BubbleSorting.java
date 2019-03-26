package com.git.test.bubblesorting;

import java.util.Arrays;

public class BubbleSorting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[][] result1 = sorting(getArray());
		for(int i =0; i<result1.length; i++)
		{
			System.out.println(Arrays.toString(result1[i]));
		}
		
		System.out.println("==========================");
		
		result1 = sortingTwoType(getArray());
		for(int i =0; i<result1.length; i++)
		{
			System.out.println(Arrays.toString(result1[i]));
		}
		
		System.out.println("==========================");
		String[][] result2 = DescendingSorting(getArray());
		for(int i =0; i<result2.length; i++)
		{
			System.out.println(Arrays.toString(result2[i]));
		}
	}

	// 오름차순
	public static String[][] sorting(String[][] arr)
	{
		String[] tmp = {"","",""};
		
		for(int i =0; i<arr.length; i++)
		{
			for(int j =0; j<arr.length-1; j++)
			{
				if(Integer.parseInt(arr[j][2]) > Integer.parseInt(arr[j+1][2]))
				{
					tmp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = tmp;		
				}
			}
		}
		
		return arr;
	}
	// 내림차순
	public static String[][] DescendingSorting(String[][] arr)
	{
		String[] tmp = {"","",""};
		
		for(int i =0; i<arr.length; i++)
		{
			for(int j =0; j<arr.length-1; j++)
			{
				if(Integer.parseInt(arr[j][2]) < Integer.parseInt(arr[j+1][2]))
				{
					tmp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = tmp;		
				}
			}
		}
		
		return arr;
	}
	
	// 오름차순 두개 조건
	public static String[][] sortingTwoType(String[][] arr)
	{
		String[] tmp = {"","",""};
		
		for(int i =0; i<arr.length; i++)
		{
			for(int j =0; j<arr.length-1; j++)
			{
				if(Integer.parseInt(arr[j][2]) > Integer.parseInt(arr[j+1][2]))
				{
					tmp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = tmp;		
				}
				else if(Integer.parseInt(arr[j][2]) == Integer.parseInt(arr[j+1][2]))
				{
					if(arr[j][1].compareTo(arr[j+1][1]) > 0 )
					{
						tmp = arr[j];
						arr[j] = arr[j+1];
						arr[j+1] = tmp;	
					}
				}
			}
		}
		
		return arr;
	}
	
	public static String[][] getArray(){
		String[][] array = {
				{"59545", "제이지", "10"},
				{"23561", "어피치", "20"},
				{"37123", "라이언", "10"},
				{"33777", "라라라", "30"}
		};
		
		return array;
	}
}
