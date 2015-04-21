package lab5;

import java.net.*;
import java.io.*;



public class MCServerOffer {

    public static void main(String args[]) {
	try {
	    MulticastSocket ms = new MulticastSocket(4099);
	    InetAddress ia = InetAddress.getByName("experiment.mcast.net");
	    ms.joinGroup(ia);
	    while(true) {
		byte[] buf = new byte[65536];
		DatagramPacket dp = new DatagramPacket(buf,buf.length);
		ms.receive(dp);
		String s = new String(dp.getData(),0,dp.getLength());
		InetAddress localAddress = ms.getLocalAddress();
		System.out.println(localAddress.getHostAddress());
		System.out.println("Received: "+s);
		byte[] data = localAddress.getHostAddress().getBytes();
		DatagramPacket packet = new DatagramPacket(data,data.length,dp.getAddress(),dp.getPort());
		ms.send(packet);
	    }
	} catch(IOException e) {
	    System.out.println("Exception:"+e);
	}
    }

}
