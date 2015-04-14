package lab3;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFromServerThread implements Runnable{
	private BufferedReader is;
	
	public ReadFromServerThread(BufferedReader is){
		this.is = is;
	}
	
	public void run(){
		try {
			String test = is.readLine();
		while(test!=null){
				System.out.println(test);
				test = is.readLine();
		}
		} catch (IOException e) {
			
			e.printStackTrace();
		}			
		
	}
}
