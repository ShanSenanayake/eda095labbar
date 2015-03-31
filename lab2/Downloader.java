package lab2;

import java.util.LinkedList;

public class Downloader {
	private LinkedList<String> addresses;

	public Downloader(LinkedList<String> addresses) {
		this.addresses = addresses;
	}

	public synchronized String getAdress() {
		return addresses.pop();

	}

	public synchronized boolean isEmpty() {
		return addresses.isEmpty();
	}

}
