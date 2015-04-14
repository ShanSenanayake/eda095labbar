package lab4;

import java.util.HashSet;

public class MultiThreadWebcrawler {
	private static final int NBR_THREADS = 10;

	public static void main(String[] args) {
		SpiderMonitor spider = new SpiderMonitor(args[0]);
		Thread[] threads = new Thread[NBR_THREADS];
		for (int i = 0; i < NBR_THREADS; i++) {
			threads[i] = new Thread(new Process(spider));
			threads[i].start();
		}
		try {
			HashSet<String> mailAddresses = spider.getMailAddresses();
			for (int i = 0; i < NBR_THREADS; i++) {
				threads[i].interrupt();
			}
			HashSet<String> httpAddresses = spider.getHTTPAdresses();
			System.out.println("Crawling finished, Mail addresses found: "
					+ mailAddresses.size() + " HTTP addresses visited "
					+ httpAddresses.size());
			for (String s : mailAddresses) {
				System.out.println(s);
			}
			for (String s : httpAddresses) {
				System.out.println(s);
			}
			System.out.println("Crawling finished, Mail addresses found: "
					+ mailAddresses.size() + " HTTP addresses visited "
					+ httpAddresses.size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
