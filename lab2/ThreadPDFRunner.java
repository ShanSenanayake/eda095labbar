package lab2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ThreadPDFRunner extends Thread{
	private Downloader downloader;

	public ThreadPDFRunner(Downloader downloader) {
		this.downloader = downloader;
	}

	public void run() {
		while (!downloader.isEmpty() && !this.isInterrupted()) {
			byte[] buffer = new byte[1024];
			int offset = 0;
			String address = downloader.getAdress();
			URL url;
			try {
				url = new URL(address);
				String[] temp = address.split("/");
				OutputStream os = new FileOutputStream(new File(
						temp[temp.length - 1]));
				InputStream is = url.openStream();
				while ((offset = is.read(buffer)) != -1) {
					os.write(buffer, 0, offset);
				}
				os.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}
}
