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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		Document doc =Jsoup.connect(currentURL).get();
		Elements links = doc.select("a[href]");
		Elements relative = doc.select("frame[src]");
		for(Element l: links){
			System.out.println(l.absUrl("href"));
			System.out.println(l.absUrl("mailto"));
		}
	}
}
