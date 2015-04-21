package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Date;

public class TimeServerUDP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			DatagramSocket dserver = new DatagramSocket(30000);
			System.out.println("Server running...");
			while (true) {
				DatagramPacket packet = new DatagramPacket(new byte[1], 1);

				dserver.receive(packet);
				System.out.println("server got packet + " + packet);
				byte[] buffer = new byte[packet.getLength()];
				buffer = packet.getData();
				int clientPort = packet.getPort();
				InetAddress clientAddress = packet.getAddress();
				System.out.println("client address: " + clientAddress + " client port: " + clientPort);
				byte[] sendData = new byte[1];
				Date date = new Date();
				DateFormat format;

				switch (buffer[0]) {
				case 0:
					format = DateFormat.getDateInstance();
					sendData = format.format(date).getBytes();
					break;
				case 1:
					format = DateFormat.getTimeInstance();
					sendData = format.format(date).getBytes();
					break;
				default:
					sendData = "ERROR invalid command".getBytes();
					break;
				}
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, clientAddress, clientPort);

				dserver.send(sendPacket);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
