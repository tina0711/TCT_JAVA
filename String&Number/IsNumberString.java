package com.lgcns.tct.isnumber;

public class IsNumberString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String inputData = "371B4A4";
		
		String newStr = getNewStr(inputData);
		System.out.println("[string -> number]");
		System.out.println(newStr);
		
		System.out.println("[product no]");
		String productNum = getProductNum(newStr);
		System.out.println(productNum);
	}
	
	public static String getNewStr(String inputData)
	{
		String newStr = "";
		String tmpStr = "";
		
		char[] numChar = inputData.toCharArray();
		for(char c : numChar)
		{
			String cToStr = String.valueOf(c);
			if(isStringNum(cToStr)) {
				tmpStr += cToStr;
			}
			else
			{
				newStr += cToStr;
			}
		}
		newStr += tmpStr;
		return newStr;
	}
	
	public static String getProductNum(String newStr)
	{
		String productNum = "";
		char[] numChar = newStr.toCharArray();
		int num = 9;
		int cal = 0;
		
		for(char c : numChar)
		{
			String cToStr = String.valueOf(c);
			if(isStringNum(cToStr))
			{
				cal += Integer.parseInt(cToStr)*num;
				num--;
			}
		}
		productNum = newStr + (cal%5);
		
		return productNum;
	}
	
	public static boolean isStringNum(String num){
		
		try {
			Double.parseDouble(num);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
		
	}

}
