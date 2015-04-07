package lab3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCP1 {

	public static void main(String args[]){
		try {
			ServerSocket server = new ServerSocket(30000);
			while(true){
				System.out.println("Waiting for client to connect..");
				Socket client = server.accept();
				System.out.println("Client connected  with ip: " + client.getInetAddress());
				InputStream input = client.getInputStream();
				OutputStream output = client.getOutputStream();
				byte[] buffer = new byte[1024];
				int offset = 0;
				while((offset = input.read(buffer))!=-1){
					System.out.write(buffer, 0, offset);
					output.write(buffer, 0, offset);
				}
				client.close();
				System.out.println("Close connection");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
