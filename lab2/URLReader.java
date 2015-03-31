package lab2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class URLReader {
private static LinkedList<String> addresses;

	public static void main(String[] args) {
		URL url = null;
		try {
			url = new URL(args[0]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("illegal url");
			System.exit(1);
		}
		Scanner siteScanner = null;
		try {
			siteScanner = new Scanner(url.openStream());
		} catch (IOException e) {
			System.err.println("IO exception");
			System.exit(1);
			e.printStackTrace();
		}
		Scanner addrScanner;
		Scanner pdfScanner;
		Pattern tagPattern = Pattern.compile("(<a)([^>]*)(>)");
		Pattern addrPattern = Pattern.compile("(href=\")[^\"]*");
		String tags = siteScanner.findWithinHorizon(tagPattern, 0);
		addresses = new LinkedList<String>();
		while (tags != null) {
			addrScanner = new Scanner(tags);
			String address = addrScanner.findWithinHorizon(addrPattern, 0);
			if (address != null) {
				pdfScanner = new Scanner(address);
				address = pdfScanner.findWithinHorizon("(.*)(.pdf)$",0);
				
				if(address != null){
					addresses.add(address.replaceAll("href=\"", ""));	
					
				}
				pdfScanner.close();
			}
			tags = siteScanner.findWithinHorizon(tagPattern, 0);
			addrScanner.close();
		}
		Downloader downloader = new Downloader(addresses);
		//runnable pdf downloader
//		PDFRunner1(downloader);
		//thread pdf downloader
//		PDFRunner2(downloader);
		//executor pdf downloader
		PDFRunner3(addresses);
	}
	
	private static void PDFRunner1(Downloader downloader){
		Thread t1 = new Thread(new RunnablePDFRunner(downloader));
		Thread t2 = new Thread(new RunnablePDFRunner(downloader));
		Thread t3 = new Thread(new RunnablePDFRunner(downloader));
		long time = System.currentTimeMillis();
		t1.start();
		t2.start();
		t3.start();
	}
	
	private static void PDFRunner2(Downloader downloader){
		ThreadPDFRunner t1 = new ThreadPDFRunner(downloader);
		ThreadPDFRunner t2 = new ThreadPDFRunner(downloader);
		ThreadPDFRunner t3 = new ThreadPDFRunner(downloader);
		long time = System.currentTimeMillis();
		t1.start();
		t2.start();
		t3.start();
	}
	
	private static void PDFRunner3(LinkedList<String> addresses){
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for(String s: addresses){
			try {
				executor.submit(new ExcecutorPDFRunner(s));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long time = System.currentTimeMillis();
		executor.shutdown();
	}


}
