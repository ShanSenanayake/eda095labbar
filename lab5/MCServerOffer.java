package lab5;

import java.net.*;
import java.text.DateFormat;
import java.util.Date;
import java.io.*;

public class MCServerOffer {
	public final static String TimeServer_request = "0";
	public final static String TimeAndDate_request = "1";

	public static void main(String args[]) {
		try {
			DatagramSocket ms = new DatagramSocket(8990);
			Thread serverOffer = new Thread(new MCServerOfferRunnable(
					ms.getLocalPort()));
			serverOffer.start();
			while (true) {
				System.out.println("Timeserver Running...");
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				ms.receive(dp);
				String s = new String(dp.getData(), 0, dp.getLength());
				byte[] sendData = new byte[1];
				Date date = new Date();
				DateFormat format;

				switch (s) {
				case "0":
					format = DateFormat.getDateInstance();
					sendData = format.format(date).getBytes();
					break;
				case "1":
					format = DateFormat.getTimeInstance();
					sendData = format.format(date).getBytes();
					break;
				default:
					sendData = "ERROR invalid command".getBytes();
					break;
				}
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, dp.getAddress(), dp.getPort());

				ms.send(sendPacket);

			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
