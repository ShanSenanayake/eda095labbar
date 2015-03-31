package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class RunnablePDFRunner implements Runnable {
	private Downloader downloader;

	public RunnablePDFRunner(Downloader downloader) {
		this.downloader = downloader;
	}

	public void run() {
		while (!downloader.isEmpty()) {
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
