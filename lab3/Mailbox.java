package lab3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class Mailbox {
	private HashMap<Integer, LinkedList<String>> mailbox;

	public Mailbox() {
		mailbox = new HashMap<Integer, LinkedList<String>>();
	}

	public synchronized void register(int id) {
		mailbox.put(id, new LinkedList<String>());
	}

	public synchronized void unregister(int id) {
		mailbox.remove(id);
	}

	public synchronized void writeEcho(String message, int id) {
		LinkedList<String> queue = mailbox.get(id);
		queue.addLast(message);
		notifyAll();
	}

	public synchronized void writeAll(String message) {
		Set<Integer> keyset = mailbox.keySet();
		for (Integer key : keyset) {
			LinkedList<String> queue = mailbox.get(key);
			queue.add(message);
		}
		notifyAll();
	}

	public synchronized String read(int id) throws InterruptedException {
		if (mailbox.containsKey(id)) {
			LinkedList<String> queue = mailbox.get(id);
			while (queue.isEmpty()) {
				wait();
			}
			return queue.pollFirst();
		} else {
			return null;
		}
	}
}
