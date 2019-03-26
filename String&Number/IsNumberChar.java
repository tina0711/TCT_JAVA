package com.lgcns.tct.isnumber;

public class IsNumberChar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputData = "371B4A4";
		
		String newStr = getNewStr(inputData);
		System.out.println("[STRING FIRST - NUM SECOND]");
		System.out.println(newStr);
		
		System.out.println("[PRODUCT NO]");
		String productNum = getProductNum(newStr);
		System.out.println(productNum);
	}
	
	public static String getNewStr(String inputData)
	{
		String newStr = "";
		String tmpStr = "";
		
		for(int i=0; i<inputData.length(); i++)
		{
			char ch =inputData.charAt(i);
			if(Character.isLetter(ch))
			{
				newStr += String.valueOf(ch);
			}
			else if(Character.isDigit(ch))
			{
				tmpStr += String.valueOf(ch);
			}
		}
		
		newStr += tmpStr;
		return newStr;
	}

	
	public static String getProductNum(String newStr)
	{
		String productNum ="";
		String tmpNum = "";
		
		int sum = 0;
		int j = 9;
		int resultNum = 0;
		
		for(int i=0; i<newStr.length(); i++)
		{
			char ch = newStr.charAt(i);
			
			if(Character.isDigit(ch))
			{
				int num = ch - 0x30;
				sum += num * j;
				j--;
			}
		}
		resultNum = sum%5;
		
		newStr += String.valueOf(resultNum);
		productNum = newStr;
		
		return productNum;
		
	}
}
