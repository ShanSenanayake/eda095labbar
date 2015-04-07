package lab3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCP2 {

	public static void main(String args[]){
		try {
			ServerSocket server = new ServerSocket(30000);
			while(true){
				Socket client = server.accept();
				ServerThread thread = new ServerThread(client);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
