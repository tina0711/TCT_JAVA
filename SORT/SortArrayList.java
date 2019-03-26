package com.lgcns.tct.sortArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

class Employee
{
	private String name;
	private String department;
	private int age;
	private int height;
	
	public Employee(String name, String department, int age, int height)
	{
		this.name = name;
		this.department = department;
		this.age = age;
		this.height = height;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
public class SortArrayList {

	ArrayList<Employee> arrList = new ArrayList<Employee>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SortArrayList sorter = new SortArrayList();
		
		sorter.loadData();
		System.out.println("원본 데이터=======================");
		sorter.printList();
		sorter.listSort();
		System.out.println("ArrayList Sort 오름차순=======================");
		sorter.printList();
	}
	
	void loadData()
	{
		Employee emp1 = new Employee("KIM", "FI", 23, 165);
		Employee emp2 = new Employee("LEE", "HR", 23, 160);
		Employee emp3 = new Employee("CHOI", "IT", 30, 180);
		Employee emp4 = new Employee("SSO", "IT", 30, 165);
		Employee emp5 = new Employee("LIM", "FI", 35, 185);
		
		arrList.add(emp1);
		arrList.add(emp2);
		arrList.add(emp3);
		arrList.add(emp4);
		arrList.add(emp5);
	}
	
	// 오름차순
	void listSort()
	{
		Collections.sort(arrList, new Comparator<Employee>() {

			@Override
			public int compare(Employee p1, Employee p2) {
				// TODO Auto-generated method stub
				if(p1.getAge() > p2.getAge())
				{
					return 1;
				}
				else if(p1.getAge() > p2.getAge())
				{
					return -1;
				}
				else
				{
					if(p1.getHeight() > p2.getHeight())
					{
						return 1;
					}
					else if(p1.getHeight() < p2.getHeight())
					{
						return -1;
					}
					else 
					{
						return 0;
					}
				}
			}
			
		});
	}
	
	void printList()
	{
		for(int i =0; i<arrList.size(); i++)
		{
			System.out.println(arrList.get(i).getName()+"\t"+ arrList.get(i).getDepartment()+"\t"+ arrList.get(i).getAge()+"\t"+arrList.get(i).getHeight() );
		}
	}

}
