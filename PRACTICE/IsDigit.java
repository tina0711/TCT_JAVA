package com.git.test.isdigit;

public class IsDigit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String inputData = "we1re3hewo34ddre67com21rue";
		String backup = "1eare4hewor5dd8eamcome3rue";
		String []netWork = new String[] {"wear", "ethe", "worl", "ddre", "amco", "metr", "ue"};
		
		IsDigit digit = new IsDigit();
		String firstRecovery = digit.getFirstRecovery(inputData, backup);
		System.out.println(firstRecovery);
		
		String SecondRecovery = digit.getSecondRecovery(firstRecovery, netWork);
		System.out.println(SecondRecovery);
		
	}
	
	public String getFirstRecovery(String inputData, String backup)
	{
		String firstRecovery = "";
		
		for(int i =0; i<inputData.length(); i++)
		{
			char currentCh = inputData.charAt(i);
			char backupCh = backup.charAt(i);
			
			if(Character.isDigit(currentCh))
			{
				if(Character.isDigit(backupCh))
				{
					firstRecovery += currentCh;
				}
				else
				{
					firstRecovery += backupCh;
				}
			}
			else
			{
				firstRecovery += currentCh;
			}
		}
			
		
		return firstRecovery;
	}

	public String getSecondRecovery(String firstRecovery, String[] netWork)
	{
		String SecondRecovery = "";
		String netStr = "";
		
		for(int i=0; i<netWork.length; i++)
		{
			netStr += netWork[i].trim();
		}
		
		SecondRecovery = getFirstRecovery(firstRecovery, netStr);
		
		return SecondRecovery;
	}
}
