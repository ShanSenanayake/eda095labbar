package lab3;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientReaderThread extends Thread {

	private Mailbox mailbox;
	private Socket client;
	private int id;

	public ClientReaderThread(Mailbox mailbox, Socket client, int id) {
		this.mailbox = mailbox;
		this.client = client;
		this.id = id;
	}

	public void run() {
		byte[] buffer = new byte[1024];
		int offset = 0;
		try {
			InputStream input = (client.getInputStream());
			System.out.println("Connectionen established with: "
					+ client.getInetAddress() + " id " + id);
			while (!isInterrupted()) {
				char cmd = (char)input.read();
				offset = input.read(buffer);
				String message = new String(buffer,0,offset);
				switch (cmd) {
				case 'Q':
					mailbox.unregister(id);
					client.close();
					this.interrupt();
					break;
				case 'M':
					mailbox.writeAll(message);
					break;
				case 'E':
					mailbox.writeEcho(message, id);
					break;
				default:
					System.out.println("invalid commad: " + cmd);
				}
				System.out.println("Server, client " + id + ": " + message);
			}


		} catch (IOException e) {
			this.interrupt();
			e.printStackTrace();
		}
	}
}
