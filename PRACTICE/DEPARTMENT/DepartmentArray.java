package com.git.test.departmentarray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DepartmentArray {
	public static List<String> departments;
	public static String deptStr;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[][] deptInfo = loadData();
		DepartmentArray department = new DepartmentArray();
		printInput(deptInfo);
		String topDepartment = department.getUpperDepartments(deptInfo, departments);
		System.out.println("상위부서  : " + topDepartment);
		int number = department.getlowerDepartmentsCount(deptInfo, deptStr);
		System.out.println("하위부서 개수 : " + number);
		
	}
	public String getUpperDepartments(String[][] inputData, List<String> departments)
	{
		String topDepartment = "";
		
		ArrayList<String> deptA = new ArrayList<String>();
		ArrayList<String> deptB = new ArrayList<String>();
		
		deptA.add(departments.get(0));
		deptB.add(departments.get(1));
		
		for(int m = 0; m<inputData.length; m++)
		{
			if(inputData[m][1].equals(deptA.get(deptA.size()-1)))
			{
				deptA.add(inputData[m][0]);
				m = -1;
			}
		}
		
		System.out.println(deptA.toString());
		for(int m = 0; m<inputData.length; m++)
		{
			if(inputData[m][1].equals(deptB.get(deptB.size()-1)))
			{
				deptB.add(inputData[m][0]);
				m = -1;
			}
		}
		System.out.println(deptB.toString());
		topDepartment = getSameDepartment(deptA, deptB);
		
		return topDepartment;
		
	}
	
	public String getSameDepartment(ArrayList<String> list1, ArrayList<String> list2)
	{
		for(String d1 : list1 )
		{
			for(String d2: list2)
			{
				if(d1.equals(d2))
					return d1;
			}
		}
		return null;
	}
	
	public int getlowerDepartmentsCount(String[][] inputData, String departmentStr)
	{
		int numOfLowerDept =0;
		int i, j =0;
		int idx = 0;
		ArrayList<String> catelist = new ArrayList<String>();
		
		for(i=0; i<inputData.length; i++)
		{
			if(departmentStr.equals(inputData[i][1]))
			{
				catelist.add(inputData[i][0]);
			}
		}
		
		while(idx < catelist.size())
		{
			for(i=0; i<inputData.length; i++)
			{
				if(catelist.get(idx).equals(inputData[i][0])) 
				{
					for(j=0; j<catelist.size(); j++)
					{
						if(catelist.get(j).equals(inputData[i][1]))
						{
							break;
						}
					}
					
					if(j==catelist.size())
					{
						catelist.add(inputData[i][1]);
					}
				}
			}
			idx++;
		}
		
		numOfLowerDept = catelist.size()-1;
		return numOfLowerDept;
	}
	
	private static String[][] loadData()
	{
		 String depInfo[][]= {
				{"M","B"},
				{"M","C"},
				{"M","K"},
				{"B","E"},
				{"C","F"},
				{"C","G"},
				{"C","H"},
				{"K","I"},
				{"K","J"},
				{"F","L"},
				{"F","A"},
				{"H","N"},
				{"H","O"},
				{"J","P"},
				{"J","Q"}
				};
		
		departments = Arrays.asList("F", "N");
	    deptStr = "J";
			
		return depInfo;
	}
	
	private static void printInput(String[][] inputData)
	{
		System.out.println("상위부서\t하위부서");
		for(String[] strArr : inputData)
		{
			for(String str : strArr)
			{
				System.out.print(str+"\t\t");
			}
			System.out.println();
		}
		
		printLine();
	}
	
	private static void printLine()
	{
		System.out.println("------------------------------------------------------------");
	}

}
