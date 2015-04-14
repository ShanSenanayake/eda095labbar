package lab3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost",30000);
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			Scanner scan = new Scanner(System.in);
			ReadFromServerThread rfst = new ReadFromServerThread(is);
			Thread readthread = new Thread(rfst);
			readthread.start();
			while(true){
				String command = scan.nextLine();
				if(!readthread.isAlive()){
					throw new IOException();
				}
				os.write(command);
				os.flush();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
