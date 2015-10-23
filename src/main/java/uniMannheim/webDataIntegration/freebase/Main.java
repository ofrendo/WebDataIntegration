package uniMannheim.webDataIntegration.freebase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.api.client.http.GenericUrl;

import uniMannheim.webDataIntegration.utils.Utils;

public class Main {

	  public static void main(String[] args) {
	    try {
	    	String apiKey = "AIzaSyCP5sTogZthPmdkOiGUVnzD_2zPy_HMzFU";
	    	
	      
	      JSONParser parser = new JSONParser();
	      
	      //String query = "[{"id":null,\"name\":null,\"type\":\"/astronomy/planet\"}]";
	      String path = "queries/company_freebase/query.json";
	      String query = Utils.readFile(path);
	      
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
	    	  String httpResult = Utils.doHTTPRequest(url);
		      
		      JSONObject response = (JSONObject)parser.parse(httpResult);
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
	      
	      resultString = allResults.toJSONString();
	      //resultString = resultString.replace("\\", "");
	      
	      Utils.writeFile("data/company_freebase.json", resultString);
	      
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

}
