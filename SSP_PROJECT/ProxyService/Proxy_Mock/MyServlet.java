package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		System.out.println("doGet : " + req.getRequestURI().toString());
		
		String line = req.getRequestURI();
		
//		StringBuilder buffer = new StringBuilder();
//		BufferedReader reader = req.getReader();
//		String line;
//		while((line = reader.readLine()) != null) {
//			buffer.append(line);
//		//	buffer.append(System.lineSeparator());
//		}
		
		res.setStatus(200);
		res.getWriter().write("hello");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println("doPost : " + req.getRequestURI().toString());
		
		String line = req.getRequestURI();
		
		BufferedReader input = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String buffer;
		StringBuilder sb = new StringBuilder();
		while((buffer = input.readLine())!=null) {
			sb.append(buffer + "\n");
		}
		System.out.println(sb.toString());
		input.close();	
		res.setStatus(200);
		
	}
}
