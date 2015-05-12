package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MCServerOfferRunnable implements Runnable {

	private int port;

	public MCServerOfferRunnable(int port) {
		this.port = port;
	}

	public void run() {
		try {
			MulticastSocket ms = new MulticastSocket(8989);
			InetAddress ia;
			ia = InetAddress.getByName("experiment.mcast.net");
			ms.joinGroup(ia);
			while (true) {
				System.out.println("brodcast server...");
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				ms.receive(dp);
				String s = new String(dp.getData(), 0, dp.getLength());
				if (s.equals("0")) {

					byte[] data = toByteArray(port);
					DatagramPacket packet = new DatagramPacket(data,
							data.length, dp.getAddress(), dp.getPort());
					ms.send(packet);
				}
			}
		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}

	private byte[] toByteArray(int value) {
	    return new byte[] {
	            (byte) ((value >> 24) & 0xFF),
	            (byte) ((value >> 16) & 0xFF),   
	            (byte) ((value >> 8) & 0xFF),   
	            (byte) (value & 0xFF)
	        };
	}
}
