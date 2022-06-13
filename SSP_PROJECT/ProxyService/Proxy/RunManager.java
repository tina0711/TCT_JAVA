package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class RunManager {
	
	private static final String host = "127.0.0.1";
	private static int port = 0;
	static String configStr = "";
	static String fileName = "";
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		RunManager rm = new RunManager();
		
		fileName = "./" + args[0] + ".json";
		configStr = rm.readFile();
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(configStr);
		
		port = element.getAsJsonObject().get("port").getAsInt();
		
		new RunManager().start();
	}
	
	public void start() throws Exception {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost(host);
		http.setPort(port);
		server.addConnector(http);
		
//		ServletHandler servletHandler = new ServletHandler();
//		servletHandler.addServletWithMapping(MyServlet.class, "/*");
//		server.setHandler(servletHandler);
		
		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		context.setAttribute("config", configStr);
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
}
