package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SendUDP2 {
	private static String REQUEST_COMMAND = "0";
	private static DatagramPacket reply;
	private static MulticastSocket ms;
	private static int port;

	public static void main(String[] args) {
		try {
			discoverServer();
			DatagramSocket ds = new DatagramSocket();
			while (true) {
				int ch;
				String s = new String();
				 // This tells MCServerOffer that we want to send a
							// date or time request. Which one is inputted using
							// system.in
				do {
					ch = System.in.read();
					if (ch != '\n') {
						s = s + (char) ch;
					}
				} while (ch != '\n');
				System.out.println("Sending message: " + s);
				byte[] buf = s.getBytes();
				DatagramPacket dp = new DatagramPacket(buf, buf.length,
						reply.getAddress(),port);
				ds.send(dp);
				DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
				ds.receive(packet);
				System.out.println(new String(packet.getData()));
			}
		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}

	}

	public static void discoverServer() {
		try {
			ms = new MulticastSocket();
			ms.setTimeToLive(1);
			InetAddress ia = InetAddress.getByName("experiment.mcast.net");
			DatagramPacket requestServer = new DatagramPacket(
					REQUEST_COMMAND.getBytes(),
					REQUEST_COMMAND.getBytes().length, ia, 8989);
			ms.send(requestServer);
			reply = new DatagramPacket(new byte[1024], 1024);
			ms.receive(reply);
			port = byteArrayToInt(reply.getData());
			System.out.println("found server " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int byteArrayToInt(byte[] b) 
	{
	    return   b[3] & 0xFF |
	            (b[2] & 0xFF) << 8 |
	            (b[1] & 0xFF) << 16 |
	            (b[0] & 0xFF) << 24;
	}


}
