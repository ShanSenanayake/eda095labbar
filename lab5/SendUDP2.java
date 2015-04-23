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

	public static void main(String[] args) {
		try {
			discoverServer();
			while (true) {
				int ch;
				String s = new String();
				s += "1 "; // This tells MCServerOffer that we want to send a
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
						reply.getAddress(), reply.getPort());
				ms.send(dp);
				DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
				ms.receive(packet);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
