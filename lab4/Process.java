package lab4;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Process implements Runnable {

	private SpiderMonitor monitor;
	private int id;

	public Process(SpiderMonitor monitor, int id) {
		this.monitor = monitor;
		this.id = id;
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				String currentURL;

				currentURL = monitor.getAddress();
				int[] sizes = monitor.getSizes();
				System.out.println("Thread " + id + "\tProccessing: " + currentURL + " Processed " + sizes[0] + " mails, " + sizes[1] + " URLS. Queue: " + sizes[2] );
				try {
					Document doc = Jsoup.connect(currentURL).get();
					Elements links = doc.select("a[href]");
					Elements frameLinks = doc.select("frame[src]");
					for (Element l : links) {
						String found = l.absUrl("href");
						if (!monitor.hasVisited(found) && found.startsWith("http")) {
							monitor.addToVisit(found);
						}else if(found.startsWith("mailto")){
							
							monitor.addMailAddresses(found);
						}
					}
					for (Element f : frameLinks) {
						String found = f.absUrl("href");
						if (!monitor.hasVisited(found)) {
							monitor.addToVisit(found);
						}
					}
					monitor.addVisited(currentURL);
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}

			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}

}
