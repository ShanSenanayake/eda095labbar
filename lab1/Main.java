

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		try {
			URL url = new URL(args[0]);
			Scanner siteScanner = new Scanner(url.openStream());
			Scanner addrScanner;
			Scanner pdfScanner;
			Pattern tagPattern = Pattern.compile("(<a)([^>]*)(>)");
			Pattern addrPattern = Pattern.compile("(href=\")[^\"]*");
			String tags = siteScanner.findWithinHorizon(tagPattern, 0);
			ArrayList<String> addresses = new ArrayList<String>();
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
			int buffersize = 1024;
			byte[] buffer = new byte[buffersize];
			for(String pdf: addresses){
				int offset = 0;
				url = new URL(pdf);
				String[] temp = pdf.split("/");
				OutputStream os = new FileOutputStream(new File(temp[temp.length-1]));
				InputStream is = url.openStream();
				while((offset = is.read(buffer))!=-1){
					os.write(buffer, 0, offset);
				}
				
			}
			siteScanner.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
