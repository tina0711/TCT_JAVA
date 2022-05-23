package test;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.*;


public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		res.setStatus(200);
		res.getWriter().write("Hello!");

		//1. Header 모두 출력
		Enumeration<String> headerNames = req.getHeaderNames();
	
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = req.getHeader(headerName);
			System.out.println(headerName + " : " +   headerValue);
			
			if(headerName.toLowerCase().equals("x-api-key")) {
				String key = req.getHeader(headerName);
			}
		}
		
		// request.getHeader("referer"); // 접속 경로
		// request.getHeader("user-agent"); // 유저의 시스템 정보
		// request.getHeader("host"); // 접속 ip
		// request.getHeader("User-Agent"); // 브라우져 정보 가져오기
		// request.getHeader("WL-Proxy-Client-IP")
		// request.getHeader("Proxy-Client-IP")
		// request.getHeader("X-Forwarded-For") // 클라이언트 ip 주소 가져오기
		// request.getRequestURI();   //프로젝트경로부터 파일까지의 경로값을 얻어옴
		// request.getContextPath();  //프로젝트의 경로값만 가져옴
		// request.getRequestURL();   //전체 경로를 가져옴
		// request.getServletPath();   //파일명

		// String request.getParameter(name)  //페이지에 전달된 파라미터를 name 기준으로 가져옴
		// String[] request.getParameterValues(name)  // 페이지에 전달된 파라미터들을 모두 가져와 배열형태로 반환함
		// String request.getProtocol() 웹 서버로 요청 시, 사용 중인 프로토콜을 리턴한다.
		// String request.getServerName() 웹 서버로 요청 시, 서버의 도메인 이름을 리턴한다. 
		// String request.getMethod() 웹 서버로 요청 시, 요청에 사용된 요청 방식(GET, POST, PUT 등)을 리턴한다. 
		// String request.getQueryString() 웹 서버로 요청 시, 요청에 사용된 QueryString을 리턴한다. 
		// String request.getRequestURI()  웹 서버로 요청 시, 요청에 사용된 URL 로부터 URI 값을 리턴한다. 
		// String request.getRemoteAddr()  웹 서버로 정보를 요청한 웹 브라우저의 IP주소를 리턴한다. 
		// int request.getServerPort() 웹 서버로 요청 시, 서버의 Port번호를 리턴한다. 
		// String request.getContextPath() 해당 JSP 페이지가 속한 웹 어플리케이션의 콘텍스트 경로를 리턴한다. 
		// String request.getHeader(name) 웹 서버로 요청 시, HTTP 요청 헤더(header)의 헤더 이름인 name에 해당하는 속성값을 리턴한다. 
//		출처: https://programdev.tistory.com/3 [개발자생각]
		
	}
}
