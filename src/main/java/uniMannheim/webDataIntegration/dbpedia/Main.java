package uniMannheim.webDataIntegration.dbpedia;

import java.io.UnsupportedEncodingException;

import com.google.api.client.http.GenericUrl;

import uniMannheim.webDataIntegration.utils.Utils;

public class Main {
	private static String pathQueryCompany = "queries/company_dbpedia/queryForDBcompany.sparql";
	private static String pathQueryLocation = "queries/location_dbpedia/queryForDBLocation.sparql";
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String format = "csv"; //could be "json" or "xml"

		String queryCompany = Utils.readFile(pathQueryCompany);
		if (true) {
			GenericUrl urlCompany = new GenericUrl("http://dbpedia.org/sparql");
			urlCompany.put("format", format);
			//urlCompany.put("default-graph-uri", URLEncoder.encode("http://dbpedia.org", "UTF-8"));
			urlCompany.put("query", queryCompany);
			String httpResult = Utils.doHTTPRequest(urlCompany);
			System.out.println("Result length: " + httpResult.length());
			Utils.writeFile("data/company_dbpedia_temp." + format, httpResult);
		}
		
		
		format = "csv";
		
		if (false) {
			String queryLocation = Utils.readFile(pathQueryLocation);
			GenericUrl urlLocation = new GenericUrl("http://dbpedia.org/sparql");
			urlLocation.put("format", format);
			urlLocation.put("query", queryLocation);
			String httpResult = Utils.doHTTPRequest(urlLocation);
			System.out.println("Result length: " + httpResult.length());
			Utils.writeFile("data/location_dbpedia." + format, httpResult);
		}
		
	}
	
	
}
