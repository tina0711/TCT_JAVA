package com.git.test.timeutil;

public class TimeUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String strTime = secToHHMMSS(37500);
		System.out.println(strTime);
		
		int nSec = strTimeToSec(strTime);
		System.out.println(nSec);
		
	}
	public static String secToHHMMSS(int sec) {
		
		String strTime = "";
		
		strTime = String.format("%02d", (sec/3600)) + ":" + String.format("%02d", (sec%3600)/60) + ":" +  String.format("%02d", (sec%3600%60));

		
		return strTime;
		
	}
	
	public static int strTimeToSec(String time) {
		
		int sec = 0;
		
		String []tmp = time.split(":");
		
		sec = Integer.parseInt(tmp[0])*3600 + Integer.parseInt(tmp[1])*60 + Integer.parseInt(tmp[2]);
//		System.out.println(sec);
		
		return sec;
		
	}
}
