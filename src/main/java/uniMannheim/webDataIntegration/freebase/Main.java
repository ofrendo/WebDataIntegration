package uniMannheim.webDataIntegration.freebase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class Main {

	  public static void main(String[] args) {
	    try {
	    	String apiKey = "AIzaSyCP5sTogZthPmdkOiGUVnzD_2zPy_HMzFU";
	    	
	      HttpTransport httpTransport = new NetHttpTransport();
	      HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
	      JSONParser parser = new JSONParser();
	      //String query = "[{"id":null,\"name\":null,\"type\":\"/astronomy/planet\"}]";
	      String query = "";
	      String path = "queries/company_freebase/query.json";
	      FileReader fr = new FileReader(path);
	      BufferedReader br = new BufferedReader(fr);
	      while (br.ready()) {
	    	  query += br.readLine();
	      }
	      fr.close();
	      
	      GenericUrl url = new GenericUrl("https://www.googleapis.com/freebase/v1/mqlread");
	      url.put("query", query);
	      url.put("key", apiKey);
	      
	      String resultString = "";
	      String cursor = "";
	      boolean isDone = false;
	      int max = 2;
	      int i = 0;
	      while (!isDone) {
	    	  i++;
	    	  
	    	  url.put("cursor", cursor);
	    	  
	    	  HttpRequest request = requestFactory.buildGetRequest(url);
		      HttpResponse httpResponse = request.execute();
		      
		      JSONObject response = (JSONObject)parser.parse(httpResponse.parseAsString());
		      JSONArray results = (JSONArray)response.get("result");
		      
		      try {
		    	  cursor = (String) response.get("cursor");
		      }
		      catch (Exception e) {
		    	  isDone = true;
		      }
		      
		      System.out.println("Request " + i + "; cursor=" + cursor);
		      for (Object result : results) {
		    	  resultString += ((JSONObject) result).toString() + "\n";
		    	  //System.out.println(result);
		      }
	      }
	      
	      resultString = resultString.replace("\\", "");

	      System.out.println("Writing to fie...");
	      FileWriter fw = new FileWriter("queries/company_freebase/result.json");
	      fw.write(resultString);
	      fw.flush();
	      fw.close();
	      System.out.println("Done.");
	      
	      
	      
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

}
