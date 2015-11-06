package uniMannheim.webDataIntegration.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class Utils {
	
	public static void main(String args[]) {
		System.out.println("Hello world!");
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
		    FileWriter fw = new FileWriter(path);
		    fw.write(content);
		    fw.flush();
		    fw.close();
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
