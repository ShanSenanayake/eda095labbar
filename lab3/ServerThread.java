package lab3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
private Socket client;
	
	public ServerThread(Socket client){
		this.client = client;
	}
	public void run(){
		byte[] buffer = new byte[1024];
		int offset = 0;
		try {
			InputStream input = client.getInputStream();
			OutputStream output = client.getOutputStream();
			System.out.println("Connectionen established with: " + client.getInetAddress());
			while((offset = input.read(buffer))!=-1){
				System.out.write(buffer, 0, offset);
				output.write(buffer, 0, offset);
			}
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connection closed with: " + client.getInetAddress());
	}
}
