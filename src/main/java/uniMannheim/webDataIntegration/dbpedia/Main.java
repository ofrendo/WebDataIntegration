package uniMannheim.webDataIntegration.dbpedia;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.api.client.http.GenericUrl;

import uniMannheim.webDataIntegration.utils.Utils;

public class Main {
	private static String pathQueryCompany = "queries/company_dbpedia/queryForDBcompany.sparql";
	private static String pathQueryLocation = "queries/location_dbpedia/queryForDBLocation.sparql";
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String queryCompany = Utils.readFile(pathQueryCompany);
		
		GenericUrl urlCompany = new GenericUrl("http://dbpedia.org/sparql");
		urlCompany.put("format", "XML");
		//urlCompany.put("default-graph-uri", URLEncoder.encode("http://dbpedia.org", "UTF-8"));
		urlCompany.put("query", queryCompany);
		
		String httpResult = Utils.doHTTPRequest(urlCompany);
		System.out.println("Result length: " + httpResult.length());
		Utils.writeFile("data/company_dbpedia.xml", httpResult);
		
	}
	
	
}
