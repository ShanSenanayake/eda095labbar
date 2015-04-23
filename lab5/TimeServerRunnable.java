package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Date;

public class TimeServerRunnable implements Runnable {
	private int command;
	private InetAddress clientAddress;
	private int port;
	private MulticastSocket ms;
	
	public TimeServerRunnable(int command, InetAddress clientAddress, int port , MulticastSocket ms){
		this.command = command;
		this.clientAddress = clientAddress;
		this.port = port;
		this.ms = ms;
		
	}
	
	public void run(){
				byte[] sendData = new byte[1];
				Date date = new Date();
				DateFormat format;

				switch (command) {
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
						sendData.length, clientAddress, port);

				try {
					ms.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
	}

