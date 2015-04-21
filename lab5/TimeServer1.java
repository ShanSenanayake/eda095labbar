package lab5;

import java.text.DateFormat;
import java.util.Date;

public class TimeServer1 {

	public static void main(String[] args) {
		/*
		 * Command for choosing date or time: args[0]: "D" for date args[0]: "T"
		 * for time
		 */
		Date date = new Date();
		DateFormat format;
		switch (args[0]) {
		case "D":
			format = DateFormat.getDateInstance();
			System.out.println(format.format(date));
			break;
		case "T":
			format = DateFormat.getTimeInstance();
			System.out.println(format.format(date));
			break;
		default:
			System.out.println("hallå john");
			break;
		}

	}

}
