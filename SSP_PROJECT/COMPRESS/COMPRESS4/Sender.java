package test.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Sender {

	int port;
	ServerSocket serverSock;
	Socket clientSock;
	OutputStream out;
	InputStream in;
	
	Sender(int port)
	{
		this.port = port;
	}
	
	void initSender()
	{
		try {
			serverSock = new ServerSocket(port);
			clientSock = serverSock.accept();
			
			System.out.println("==>클라이언트 연결 승인!");
			
			out = clientSock.getOutputStream();
			in = clientSock.getInputStream();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void destroySender()
	{
		try {
			serverSock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void sendMsg(String msg)
	{
		byte [] data = msg.getBytes();
		try {
			out.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	String recvMsg()
	{
		byte data[]= new byte[1024];
		int readByte = 0;
		try {
			readByte = in.read(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(data,0,readByte);
	}
}