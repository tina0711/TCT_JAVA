package test;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MyJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("name", "spiderman");
		jsonObject.addProperty("age", 45);
		jsonObject.addProperty("married", true);
		
		JsonArray jsonArray = new JsonArray();
		jsonArray.add("martial art");
		jsonArray.add("gun");
		jsonObject.add("speciality", jsonArray);
		
		JsonObject jsonObject1 = new JsonObject();
		jsonObject1.addProperty("1st", "done");
		jsonObject1.addProperty("2nd", "expected");
		jsonObject1.add("3rd", null);
		
		jsonObject.add("vaccine", jsonObject1);
		
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.addProperty("name", "spiderboy");
		jsonObject2.addProperty("age", 10);
		
		JsonObject jsonObject3 = new JsonObject();
		jsonObject3.addProperty("name", "spidergirl");
		jsonObject3.addProperty("age", 8);
		
		JsonArray jsonArray1 = new JsonArray();
		jsonArray1.add(jsonObject2);
		jsonArray1.add(jsonObject3);
		
		jsonObject.add("children", jsonArray1);
		
		jsonObject.add("address", null);
		
		String json = gson.toJson(jsonObject);
		System.out.println(json);
		
		FileWriter fw = null;
		String data = "";
		try {
			fw = new FileWriter("./JSON.TXT");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			fw.write(json);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}

//{
//	  "name": "spiderman",
//	  "age": 45,
//	  "married": true,
//	  "speciality": [
//	    "martial art",
//	    "gun"
//	  ],
//	  "vaccine": {
//	    "1st": "done",
//	    "2nd": "expected",
//	    "3rd": null
//	  },
//	  "children": [
//	    {
//	      "name": "spiderboy",
//	      "age": 10
//	    },
//	    {
//	      "name": "spidergirl",
//	      "age": 8
//	    }
//	  ],
//	  "address": null
//}

