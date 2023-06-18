package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class MyHttpServer{
	
	public void start() throws Exception{
	
		List<Server> serverList = new ArrayList<Server>();
		
		File[] files = new File("./").listFiles();
		
		for(File f : files) {
			if(f.getName().startsWith("Proxy-")&& f.getName().endsWith(".json")) {
				
				try {
					JsonElement element = JsonParser.parseReader(new FileReader(f.getPath()));
					Server server = new Server();
					ServerConnector http = new ServerConnector(server);
					http.setHost("127.0.0.1");
					http.setPort(element.getAsJsonObject().get("port").getAsInt());
					server.addConnector(http);
					
					ServletContextHandler context = new ServletContextHandler();
					context.setContextPath("/");
					
					context.setAttribute("proxy.routes", element.getAsJsonObject().get("routes"));
					context.setAttribute("proxy.name", f.getName().split("[.]")[0]);
					context.addServlet(MyServlet.class, "/*");
					server.setHandler(context);
					
					server.start();
					serverList.add(server);
				}catch (JsonIOException e) {
					e.printStackTrace();
				}catch(JsonSyntaxException e) {
					e.printStackTrace();
				}catch(FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		
		for(Server server : serverList) {
			server.join();
		}
	
	}

}
