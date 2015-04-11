package lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Webcrawler {
	private static final int HTTP = 1;
	private static final int MAIL = 2;
	private static final int FRAME = 3;
	private static final int LIMIT = 1000;

	private static LinkedList<String> toBeVisited;
	private static HashSet<String> mailAddresses;
	private static HashSet<String> visited;

	public static void main(String[] args) {
		visited = new HashSet<String>();
		mailAddresses = new HashSet<String>();
		toBeVisited = new LinkedList<String>();
		toBeVisited.add(args[0]);
		URL url;
		while (!toBeVisited.isEmpty() && visited.size() < LIMIT) {
			try {
				crawl();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void crawl() throws MalformedURLException, IOException {
		URL url;
		String currentURL = toBeVisited.pop();
		url = new URL(currentURL);
		System.out.println(url.toString());
		BufferedReader br = new BufferedReader(new InputStreamReader(
				url.openStream()));
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
		match(httpPattern, sb.toString(), HTTP, null);
		match(mailPattern, sb.toString(), MAIL, null);
		match(framePattern, sb.toString(), FRAME, currentURL);
		visited.add(currentURL);
	}

	// finds and matcher depending of the pattern
	public static void match(Pattern p, String text, int pattern,
			String absolute) {
		Matcher matcher = p.matcher(text);
		while (matcher.find()) {
			String found = matcher.group(1);
			switch (pattern) {
			case FRAME:
				found = absolute + found;
				/* FALLTHROUGH */
			case HTTP:
				if (!visited.contains(found)) {
					toBeVisited.addLast(found);
				}
				break;
			case MAIL:
				mailAddresses.add(found);
				break;
			default:
				System.out.println(found);
			}

		}

	}
}
