package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RunManager {
	
	static String fileName ="";
	static String str = "";
	private static int port = 0;
	private static Map<String, String> map = new HashMap<String, String>();

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		RunManager rm = new RunManager();
		
		fileName = args[0];
		str = rm.readFile();
		JsonElement element = JsonParser.parseString(str);
		port =element.getAsJsonObject().get("port").getAsInt();
		JsonArray jsonArray = element.getAsJsonObject().getAsJsonArray("routes");
		
		for(int i=0; i<jsonArray.size(); i++) {
			JsonObject jo = jsonArray.get(i).getAsJsonObject();
			map.put(jo.get("pathPrefix").getAsString(), jo.get("url").getAsString());
		}
		
		rm.start();
	}
	
	public void start() throws Exception{
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(port);
		server.addConnector(http);
		
//		ServletHandler servletHander = new ServletHandler();
//		servletHander.addServletWithMapping(MyServlet.class, "/*");
		
		ServletContextHandler context = new ServletContextHandler();
		context.setAttribute("map", map);
		context.setContextPath("/");
		context.addServlet(MyServlet.class, "/*");
		server.setHandler(context);
		
		server.start();
		server.join();
		
	}
	
	public String readFile()
	{
		BufferedReader br = null;
		String str = "";
		StringBuilder buffer = new StringBuilder();
		
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        while(true) {
			String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (line==null) break;
        	buffer.append(line);
        	//System.out.println(line);
        }
        
        try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        str = buffer.toString();
        return str;

	}
	
	public String readServiceFile(String fileName)
	{
		BufferedReader br = null;
		String result = "";
		
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        while(true) {
			String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (line==null) break;
        	System.out.println(line);
        	result = line;
        }
        
        try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
	}


}
