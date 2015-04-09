package lab3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Chat {

	public static void main(String[] args){
		Mailbox mailbox = new Mailbox();
		try {
			ServerSocket server = new ServerSocket(30000);
			int id = 0;
			while(true){
				Socket client = server.accept();
				id++;
				ClientWriterThread wt = new ClientWriterThread(mailbox,client.getOutputStream(),id);
				ClientReaderThread rt = new ClientReaderThread(mailbox,client,id);
				mailbox.register(id);
				wt.start();
				rt.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
