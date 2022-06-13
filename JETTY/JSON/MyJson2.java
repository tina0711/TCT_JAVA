package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MyJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//1. JSON 만들기
		Gson gson = new Gson();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("name", "anna");
		jsonObject.addProperty("id", 1);
		
		//JsonObject를 Json 문자열로 변환
		String jsonStr = gson.toJson(jsonObject);
		System.out.println("1. JSON 만들기");
		System.out.println(jsonStr);
		
		//----------------------------------------------------------//
		
		//2-1. JSON 파싱하기
		String jsonStr1 = "{\"id\":1,\"name\":\"anna\"}";
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonStr1) ;
		
		System.out.println("2. JSON 파싱하기");
		int age = element.getAsJsonObject().get("id").getAsInt();
		System.out.println("id = " + age);
		
		String name = element.getAsJsonObject().get("name").getAsString();
		System.out.println("name = " + name);
		
		//2-2. JSON  Array파싱하기
		String json =
	            "{" +
	                    "    strKey : strValue, " +
	                    "    numKey: 235.3, " +
	                    "    arrKey: [arrV1, arrV2, arrV3]," +
	                    "    objKey: {subKey: subValue}," +
	                    "    numArrKey: [100, 200, 300]," +
	                    "    nullKey: null" +
	                    "}";
		
		JsonParser parser1 = new JsonParser();
		JsonElement element1 = parser1.parse(json) ;
		
		JsonArray jsonArray = element1.getAsJsonObject().get("arrKey").getAsJsonArray();
		
		for(int i=0; i<jsonArray.size(); i++) {
			String strArr = jsonArray.get(i).getAsString(); // 인덱스 번호로 접근해서 가져온다. 
			System.out.println(strArr);
		}
		
		//----------------------------------------------------------//
		System.out.println("3. 클래스를 Json 문자열로 변환");
		//3. 클래스를 Json 문자열로 변환
		Student student = new Student(1, "Anna");
		
		// Student 객체 --> Json 문자열
		String studentJson = gson.toJson(student);
		System.out.println(studentJson);
		
		System.out.println("4. Json 문자열을 Student 클래스로 변환");
		//----------------------------------------------------------//
		//4. Json 문자열을 Student 클래스로 변환
		Student student1 = gson.fromJson(studentJson, Student.class);
		System.out.println(student1);
		
		//5. Map -> Json문자열 변환하기
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 1);
		map.put("name", "Anna");
		
		String jsonStr2 = gson.toJson(map);
		
		System.out.println(jsonStr2);
		
		//. Json -> Map문자열 변환하기
		String jsonStr3 = "{\"id\":\"1\",\"name\":\"anna\"}";
		Map<String, Object> map2 = gson.fromJson(jsonStr3, Map.class);
		for(Map.Entry<String, Object> entry : map2.entrySet()) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
				
	}	
}
class Student{
	int id;
	String name;
	
	public Student(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Student [id = " + id + ", name = " + name + "]";
	}
}
