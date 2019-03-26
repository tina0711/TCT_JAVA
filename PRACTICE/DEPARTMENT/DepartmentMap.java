package com.git.test.departmentMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DepartmentMap {
	public static List<String> departments;
	public static String deptStr;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[][] deptInfo = loadData();
		DepartmentMap department = new DepartmentMap();
		
		printInput(deptInfo);
		String topDepartment = department.getUpperDepartments(deptInfo, departments);
		System.out.println("상위부서  : " + topDepartment);
		int number = department.getlowerDepartmentsCount(deptInfo, deptStr);
		System.out.println("하위부서 개수 : " + number);
		
	}
	
	public String getUpperDepartments(String[][] inputData, List<String> departments)
	{
		String topDepartment = "";
		Map<String, List<String>> tMap = new LinkedHashMap<String, List<String>>();
		
		for(int i=0; i<inputData.length; i++)
		{
			String parent = inputData[i][0];
			String child = inputData[i][1];
			
			if(tMap.containsKey(parent))
			{
				List<String> childs = tMap.get(parent);
				childs.add(child);
				
				tMap.put(parent, childs);
			}
			else
			{
				List<String> childs = new ArrayList<String>();
				childs.add(child);
				
				tMap.put(parent, childs);
			}
		}
//		printMap(tMap);
		
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();

		boolean flag = false;

		for(String dept : departments)
		{
			
			if(flag== false)
			{
				for(String key:tMap.keySet())
				{
					List<String> values = tMap.get(key);
					if(values.contains(dept))
					{
						list1.add(key);
						
						String parent = getParent(tMap, key);
						while(!parent.isEmpty())
						{
							list1.add(parent);
							parent = getParent(tMap, parent);
						}
						
						break;
					}
				}
				
				flag = true;
			}
			else
			{
				for(String key:tMap.keySet())
				{
					List<String> values = tMap.get(key);
					if(values.contains(dept))
					{
						list2.add(key);
						
						String parent = getParent(tMap, key);
						while(!parent.isEmpty())
						{
							list2.add(parent);
							parent = getParent(tMap, parent);
						}
						
						break;
					}
				}
			}
		}
		
//		System.out.println(list1.toString());
//		System.out.println(list2.toString());
		
		topDepartment = getSameDepartment(list1, list2);
		return topDepartment;
		
	}
	
	public String getSameDepartment(List<String> list1, List<String> list2)
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
	
	public String getParent(Map<String, List<String>> tMap, String child)
	{
		String parent = "";
		
		for(String key:tMap.keySet())
		{
			List<String> values = tMap.get(key);
			if(values.contains(child))
			{
				parent = key;
				break;
			}
		}
		
		return parent;
	}
	
	public List<String> getChilds(Map<String, List<String>> tMap, String parent)
	{
		List<String> childs = new ArrayList<String>();
		
		if(tMap.containsKey(parent))
		{
			childs = tMap.get(parent);
		}
		
		return childs;
	}
	
	private void getChildren(Map <String, List<String>> tMap, String parent, List<String> children)
	{
		List<String> tChildren = getChilds(tMap, parent);
		if(tChildren.size() > 0)
		{
			children.addAll(tChildren);
			
			for(String tChildParents : tChildren)
			{
				getChildren(tMap, tChildParents, children);
			}
		}
	}
	
	public int getlowerDepartmentsCount(String[][] inputData, String departmentStr)
	{
		int numOfLowerDept =0;
		Map<String, List<String>> tMap = new LinkedHashMap<String, List<String>>();

		
		for(int i=0; i<inputData.length; i++)
		{
			String parent = inputData[i][0];
			String child = inputData[i][1];
			
			if(tMap.containsKey(parent))
			{
				List<String> childs = tMap.get(parent);
				childs.add(child);
				
				tMap.put(parent, childs);
			}
			else
			{
				List<String> childs = new ArrayList<String>();
				childs.add(child);
				
				tMap.put(parent, childs);
			}
		}
		
//		printMap(tMap);
		

		for(String key : tMap.keySet())
		{
			List<String> values = tMap.get(key);
			if(values.contains(departmentStr))
			{
				String parent = getParent(tMap, departmentStr);
				
				List<String> children = new ArrayList<String>();
				getChildren(tMap, parent, children);
				
				numOfLowerDept = children.size();
			}
			
		}
		
		
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
	
	private static void printMap(Map<String, List<String>> tMap)
	{
		for(String key : tMap.keySet())
		{
			System.out.println("key : " + key + " value : " + tMap.get(key));
		}
	}
	private static void printLine()
	{
		System.out.println("------------------------------------------------------------");
	}

}
