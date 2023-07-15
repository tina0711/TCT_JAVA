package test;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletHandler;

public class MyServer {

	private static Map<String, String> map = new HashMap<String, String>();
	private static int port = 8080;
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		map.put("pathPrefix", "/front");
		
		new MyServer().start();
	}
	
	public void start() throws Exception {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(port);
		server.addConnector(http);
		
		//ServletHandler servletHandler = new ServletHandler();
		//servletHandler.addServletWithMapping(MyServlet.class, "/*");

		ServletContextHandler context = new ServletContextHandler();
		context.setAttribute("map", map);
		context.setContextPath("/");
		context.addServlet(MyServlet.class, "/*");
		server.setHandler(context);
		
		server.start();
		server.join();
	}
}
