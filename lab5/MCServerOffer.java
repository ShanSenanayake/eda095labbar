package lab5;

import java.net.*;
import java.io.*;

public class MCServerOffer {
	public final static String TimeServer_request = "0";
	public final static String TimeAndDate_request = "1";
	
	public static void main(String args[]) {
		try {
			MulticastSocket ms = new MulticastSocket(8989);
			InetAddress ia;
			ia = InetAddress.getByName("experiment.mcast.net");
			ms.joinGroup(ia);
			while (true) {
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				ms.receive(dp);
				String s = new String(dp.getData(), 0, dp.getLength());
				String[] command = s.split(" ");
				/*  
				 * Command: "0" for TimeServer_request with no more arguments
				 * 			"1" for TimeAndDate_request plus space followed by an additional "0" or "1" for date and time respectively.
				 */
				switch(command[0]){
				case TimeServer_request:
					System.out.println("Time request received, sending response back.");
					Thread serverOffer = new Thread(new MCServerOfferRunnable(ms,
							dp));
					serverOffer.start();
					break;
				case TimeAndDate_request:
					Thread timeServerThread = new Thread(new TimeServerRunnable(Integer.parseInt(command[1]),dp.getAddress(),dp.getPort(),ms));
					timeServerThread.start();
					break;
				}
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
