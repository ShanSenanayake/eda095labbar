package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SendUDP {

	public static void main(String[] args) {
		/*
		 * args[0]: address args[1]: port args[2]: command 0 if date, 1 if time.
		 */
		try {
			DatagramSocket socket = new DatagramSocket();
			byte[] buffer = new byte[1];
			buffer[0] = Byte.parseByte(args[2]);
			DatagramPacket packet = new DatagramPacket(buffer, 1,
					InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
			
			socket.send(packet);
			
			
			DatagramPacket received = new DatagramPacket(new byte[1024],1024);
			socket.receive(received);
			byte[] data = received.getData();
			System.out.println(new String(data));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
