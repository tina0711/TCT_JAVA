package com.git.test.contains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Contains {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> inputData = Arrays.asList("tbc.exe", "tbce", "tbcd.exe", "bxyz.exe", "bcde", "txbz.exe", "tbcxd.exe", "tcxy.exe", "txzy.exe");
		List<String> outputData = new ArrayList<String>();
		List<String> first = new ArrayList<String>();
		
		for(String str : inputData)
		{
			if(str.contains("."))
			{
				first.add(str);
			}
		}
		
		for(String str : first)
		{
			String tmpStr = str.substring(0, str.indexOf("."));
			
			if(tmpStr.indexOf("t") > -1 || tmpStr.indexOf("x") > -1)
			{
				if(tmpStr.indexOf("tb") > -1 || tmpStr.indexOf("tx") > -1 || tmpStr.indexOf("xy")> -1)
				{
					outputData.add(str);
				}
			}
			else
			{
				outputData.add(str);
			}
		}
		
		
		for(String str : outputData)
		{
			System.out.println(str);
		}
	}

}
