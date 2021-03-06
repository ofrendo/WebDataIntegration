package uniMannheim.webDataIntegration.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.joda.time.DateTime;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class Utils {
	
	public static void main(String args[]) {
		System.out.println("Hello world!");
		
		String date = "1927";
		DateTime dt = DateTime.parse(date);
		System.out.println(dt);
		
		String test = "1.10631e+09";
		System.out.println(Double.valueOf(test).longValue());
		
		test = "hello;;world";
		System.out.println(test.replaceAll(";;", " "));
	}
	
	public static String readFile(String path) {
		String result = "";
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
				while (br.ready()) {
					result += br.readLine() + "\n";
				}
		    fr.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void writeFile(String path, String content) {
		try {
			System.out.println("Writing to file " + path + "...");
			Writer out = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream(path), "UTF-8"));
		    //fw.write(content);
		    //fw.flush();
		    //fw.close();
			out.write(content);
			out.flush();
			out.close();
		    System.out.println("Done.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static HttpTransport httpTransport = new NetHttpTransport();
    private static HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
	
	public static String doHTTPRequest(GenericUrl url) {
		String result = "";
		try {
			HttpRequest request = requestFactory.buildGetRequest(url);
		    HttpResponse httpResponse = request.execute();
		    
		    result = httpResponse.parseAsString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
