package lab2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ExcecutorPDFRunner implements Runnable {
	private URL url;
	private String address;

	public ExcecutorPDFRunner(String address) throws MalformedURLException {
		url = new URL(address);
		this.address = address;
	}

	public void run() {
		byte[] buffer = new byte[1024];
		int offset = 0;
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
