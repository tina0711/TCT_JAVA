package test.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Receiver {
	int port;
	Socket clientSocket;
	OutputStream out;
	InputStream in;
	Receiver(int port)
	{
		this.port = port;
	}
	void initReceiver()
	{
		
		try {
			clientSocket = new Socket("127.0.0.1",port);
			out = clientSocket.getOutputStream();
			in = clientSocket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void destroyReceiver()
	{
		try {
			clientSocket.close();
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
