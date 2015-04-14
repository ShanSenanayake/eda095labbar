package lab4;

import java.util.HashSet;
import java.util.LinkedList;

public class SpiderMonitor {

	public static final int HTTP = 1;
	public static final int MAIL = 2;
	public static final int FRAME = 0;
	private static final int LIMIT = 100;
	
	private boolean timeToKill;
	private LinkedList<String> toBeVisited;
	private HashSet<String> mailAddresses;
	private HashSet<String> visited;
	private HashSet<String> currentlyVisiting;

	public SpiderMonitor(String start){
		timeToKill = false;
		toBeVisited = new LinkedList<String>();
		mailAddresses = new HashSet<String>();
		visited = new HashSet<String>();
		currentlyVisiting = new HashSet<String>();
		toBeVisited.add(start);
	}
	

	public synchronized String getAddress() throws InterruptedException {
		while(toBeVisited.isEmpty()){
			wait();
			if(visited.size()+currentlyVisiting.size() >= LIMIT){
				Thread.currentThread().interrupt();
			}
		}
		String temp = toBeVisited.pop();
		currentlyVisiting.add(temp);
		return temp;
	}

	public synchronized void addVisited(String currentURL) {
		currentlyVisiting.remove(currentURL);
		visited.add(currentURL);
		if(visited.size()>=LIMIT || (toBeVisited.isEmpty() && currentlyVisiting.isEmpty())){
			timeToKill = true;
			notifyAll();
			Thread.currentThread().interrupt();
		}
	}
	
	public synchronized  HashSet<String> getMailAddresses() throws InterruptedException{
		while(!timeToKill){
			wait();
		}
		return mailAddresses;
	}
	public synchronized  HashSet<String> getHTTPAdresses() throws InterruptedException{
		while(!timeToKill){
			wait();
		}
		return visited;
	}

	public synchronized boolean hasVisited(String found) {
		// TODO Auto-generated method stub
		return visited.contains(found);
	}

	public synchronized void addToVisit(String found) {

		toBeVisited.add(found);
		notifyAll();
	}

	public synchronized void addMailAddresses(String found) {

		mailAddresses.add(found);
		
	}


	public synchronized int[] getSizes() {
		// TODO Auto-generated method stub
		int[] temp = {mailAddresses.size(), visited.size(), toBeVisited.size() };
		return temp;
	}

}
