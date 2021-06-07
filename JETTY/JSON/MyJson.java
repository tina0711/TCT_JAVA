
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class MyJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonElement jsonElement = JsonParser.parseString("{ \"key\":\"value\"}");
		System.out.println(jsonElement.toString());
	}

}
