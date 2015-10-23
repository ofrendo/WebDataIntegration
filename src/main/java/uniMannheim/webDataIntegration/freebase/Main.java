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
	static String separater = ";;";
	static JSONParser parser = new JSONParser();

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
	      JSONArray allResults = new JSONArray();
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
		    	  //resultString += ((JSONObject) result).toString() + "\n";
		    	  allResults.add( (JSONObject) result );
		    	  //System.out.println(result);
		      }
	      }
	      
	      //convert format of leadership,citytown and industry
	      allResults = convertArray("/business/business_operation/industry",
	    		  allResults);
	      allResults = convertJsonObj("/organization/organization/leadership",
					"/organization/leadership/person", allResults);
	      allResults = convertJsonObj(
					"/organization/organization/headquarters",
					"/location/mailing_address/citytown", allResults);
	      
	      resultString = allResults.toJSONString();
	      //resultString = resultString.replace("\\", "");
	      
	      System.out.println("Writing to file...");
	      FileWriter fw = new FileWriter("data/company_freebase.json");
	      fw.write(resultString);
	      fw.flush();
	      fw.close();
	      System.out.println("Done.");
	      
	      
	      
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
	  
	  public static JSONArray convertArray(String outerKey, JSONArray allData) {
			for (int i = 0; i < allData.size(); i++) {
				String newData = "";
				JSONObject data = (JSONObject) allData.get(i);
				JSONArray arrayInData = (JSONArray) data.get(outerKey);
				for (int j = 0; j < arrayInData.size(); j++) {
					newData += arrayInData.get(j);
					if (j != arrayInData.size() - 1)
						newData += separater;
				}
				data.remove(outerKey);
				data.put(outerKey, newData);
			}
			return allData;
		}

		public static JSONArray convertJsonObj(String outerKey, String innerKey,
				JSONArray allData) {
			for (int i = 0; i < allData.size(); i++) {
				String newData = "";
				JSONObject data = (JSONObject) allData.get(i);
				JSONArray arrayInData = (JSONArray) data.get(outerKey);
				for (int j = 0; j < arrayInData.size(); j++) {
					JSONObject temp = (JSONObject) arrayInData.get(j);
					newData += temp.get(innerKey);
					if (j != arrayInData.size() - 1)
						newData += separater;
				}
				data.remove(outerKey);
				data.put(outerKey, newData);
			}
			return allData;
		}
}
