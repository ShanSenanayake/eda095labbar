package lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Process implements Runnable {

	private SpiderMonitor monitor;

	public Process(SpiderMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				String currentURL;

				currentURL = monitor.getAddress();
				URL url = null;
				try {
					url = new URL(currentURL);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(url.openStream()));
					Pattern httpPattern = Pattern
							.compile("<a[^>]*?href=\"(http.*?)\"[^>]*>");
					Pattern mailPattern = Pattern
							.compile("<a[^>]*?href=\"mailto:(.*?)\"[^>]*>");
					Pattern framePattern = Pattern
							.compile("<frame[^>]*?src=\"(.*?)\"[^>]*?>");
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();
					while (line != null) {
						sb.append(line);
						line = br.readLine();
					}
					match(httpPattern, sb.toString(), SpiderMonitor.HTTP, null);
					match(mailPattern, sb.toString(), SpiderMonitor.MAIL, null);
					match(framePattern, sb.toString(), SpiderMonitor.FRAME,
							currentURL);
					monitor.addVisited(currentURL);
				} catch (MalformedURLException e) {
				} catch (IOException e) {
				} catch (IllegalArgumentException e){
					System.out.println("url: " + url.toString());
				}
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}

	private void match(Pattern p, String text, int pattern, String absolute) {
		Matcher matcher = p.matcher(text);
		while (matcher.find()) {
			String found = matcher.group(1);
			switch (pattern) {
			case SpiderMonitor.FRAME:
				found = absolute + found;
				/* FALLTHROUGH */
			case SpiderMonitor.HTTP:
				if (!monitor.hasVisited(found)) {
					monitor.addToVisit(found);
				}
				break;
			case SpiderMonitor.MAIL:
				monitor.addMailAddresses(found);
				break;
			default:
				System.out.println(found);
			}

		}

	}

}
