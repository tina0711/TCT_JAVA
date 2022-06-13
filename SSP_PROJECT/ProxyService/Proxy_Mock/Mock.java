package test;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletHandler;

public class Mock {
	
	private static final String host = "127.0.0.1";
	private static int port = 8001;

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost(host);
		http.setPort(port);
		server.addConnector(http);
		
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(MyServlet.class, "/*");
		server.setHandler(servletHandler);
		
		server.start();
		
		Thread.sleep(1000);
		
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		ContentResponse contentRes = httpClient.newRequest("http://127.0.0.1:5001/auth").method(HttpMethod.GET).send();
		System.out.println(contentRes.getContentAsString());
		server.join();
	}
	
	
}
