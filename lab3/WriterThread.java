package lab3;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class WriterThread extends Thread {
	private OutputStream output;
	private int id;
	private Mailbox mailbox;

	public WriterThread(Mailbox mailbox, OutputStream output, int id) {
		this.id = id;
		this.output = output;
		this.mailbox = mailbox;
	}

	public void run() {
		try {
			OutputStreamWriter ow = new OutputStreamWriter(output);
			while (!isInterrupted()) {
				String message = mailbox.read(id);
				if (message != null) {
					ow.write(message);
					ow.flush();
				} else {
					this.interrupt();
				}
			}
		} catch (InterruptedException e) {
			this.interrupt();
		} catch (IOException e) {
			this.interrupt();
			e.printStackTrace();
		}
	}
}
