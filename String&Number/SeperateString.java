package com.git.test.seperate;

import java.util.ArrayList;
import java.util.List;

public class SeperateString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<String> strList = new ArrayList<String>();
		String inputData = "A358EKBJD43";
		String str ="";
		String num = "";
		
		for(int i =0; i<inputData.length(); i++)
		{
			if(isStringDouble(inputData.substring(i,  i+1)))
			{
				num += inputData.substring(i, i+1);
			}
			else
			{
				str += inputData.substring(i, i+1);
			}
		}
		
		strList.add(str);
		strList.add(num);
		
		for(String str1 : strList)
		{
			System.out.println(str1);
		}
		

		
		String newStr = getNewStr(strList);
	}
	
	public static String getNewStr(List<String> strList)
	{
		String newStr  = "";
		StringBuilder tmp = new StringBuilder();
		String letter = strList.get(0);
		String num = strList.get(1);
		int i =0;
		int m =0;
		int n =0;
		
		while(true)
		{
			if(i==letter.length() +num.length())
			{
				break;
			}
			
			if(i%2 ==0)
			{
				tmp.append(letter.substring(m, m+1));
				m++;
			}
			else
			{
				tmp.append(num.substring(n, n+1));
				n++;
			}
			i++;
		}
		
		newStr = tmp.toString();
		
		return newStr;
	}
	
	public static boolean isStringDouble(String s)
	{
		try {
			Double.parseDouble(s);
			return true;
		}catch(NumberFormatException e)
		{
			return false;
		}
	}

}
