package lab5;

import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

public class TimeServer2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		/*
		 * Command for choosing date or time: 0: "D" for date 1: "T"
		 * for time
		 */
		while(true){
			System.out.println("Enter command, 0 for date, 1 for time:");
			int cmd = scan.nextInt();
			Date date = new Date();
			DateFormat format;
			switch (cmd) {
			case 0:
				format = DateFormat.getDateInstance();
				System.out.println(format.format(date));
				break;
			case 1:
				format = DateFormat.getTimeInstance();
				System.out.println(format.format(date));
				break;
			default:
				System.out.println("ERROR invalid command");
				break;
			}
		}
	}

}
