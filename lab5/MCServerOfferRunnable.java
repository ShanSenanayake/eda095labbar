package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MCServerOfferRunnable implements Runnable {
	private MulticastSocket ms;
	private DatagramPacket dp;
	
	public MCServerOfferRunnable(MulticastSocket ms, DatagramPacket dp){
		this.ms = ms;
		this.dp = dp;
	}

	public void run() {
		try {
				InetAddress localAddress = ms.getLocalAddress();
				byte[] data = localAddress.getHostAddress().getBytes();
				DatagramPacket packet = new DatagramPacket(data, data.length,
						dp.getAddress(), dp.getPort());
				ms.send(packet);
		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}
}
