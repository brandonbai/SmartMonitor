package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
	public static void main(String[] args) {
		ServerSocket server = null;
		Socket socket = null;
		byte[] data = {14, 15, 2, 1, 23, 25, 7, 9, 11, 13, 15, 65, 19, 81};
		try {
				server = new ServerSocket(20000);
				System.out.println("server start");
				socket = server.accept();
				System.out.println("client connect");
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				while(true) {
					byte[] data1 = new byte[20];
					in.read(data1);
					System.out.println("accept:\t"+Arrays.toString(data1));
					System.out.println("服务器要发送数据了");
					out.write(data);
					System.out.println("服务器发送了数据");
					out.flush();
				}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch(Exception ee) {
				ee.printStackTrace();
			}
		}
	}
}
