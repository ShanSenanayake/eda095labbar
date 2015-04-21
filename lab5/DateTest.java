package lab5;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTest {

	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(date);
		DateFormat dateFormat = DateFormat.getDateInstance();
		System.out.println(dateFormat.format(date));
		Locale locale = Locale.SIMPLIFIED_CHINESE;
		dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
		System.out.println(locale);
		System.out.println(dateFormat.format(date));

	}

}
