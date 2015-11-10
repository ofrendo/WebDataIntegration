package de.uni_mannheim.informatik.wdi.usecase.companies;

import java.util.Arrays;

import org.joda.time.DateTime;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.wdi.MatchableFactory;

/**
 * Creation of companies
 * @author Oliver
 *
 */
public class CompanyFactory extends MatchableFactory<Company> {

/* example entry
	<company>
		<name>1-800-Flowers</name>
		<countries>United States of America</countries>
		<industries>Retail;;Information technology;;Gift, Novelty, and Souvenir Stores</industries>
		<revenue>912600000</revenue>
		<numberOfEmployees>3700</numberOfEmployees>
		<dateFounded>1976</dateFounded>
		<headquarters>Town of North Hempstead</headquarters>
		<profit>-98420000</profit>
		<keyPeople>James F. Mccann;;Christopher G. Mccann;;William E Shea;;Stephen J Bozzo;;David Taiclet;;Gerard M Gallagher</keyPeople>
	</company>
*/
	private int counter = 1;
	private String idPrefix;
	
	public CompanyFactory(String idPrefix) {
		this.idPrefix = idPrefix;
	}
	
	@Override
	public Company createModelFromElement(Node node, String provenanceInfo) {
		String id = idPrefix + "_" + counter;
		counter++;
		
		String name = getValueFromChildElement(node, "name");
		
		// create the object with id and provenance information
		Company company = new Company(name, provenanceInfo);
		//System.out.println("Creating company " + getValueFromChildElement(node, "name"));
		
		// fill the attributes
		//name = name.replaceAll("'", "");
		company.setName(name);
		
		//Normalize country attribute
		String countries = getValueFromChildElement(node, "countries");
		String resultCountries = normalizeCountries(countries);
		//if (name.equals("BP") || name.equals("BP")) System.out.println(countries + " normalized to " + resultCountries);
		company.setCountries(resultCountries);
		
		String industries = getValueFromChildElement(node, "industries");
		if (name.equals("Chevron") || name.equals("Chevron Corporation")) 
			System.out.println(name + ": " + industries);
		company.setIndustries(industries);

		String revenue = getValueFromChildElement(node, "revenue");
		company.setRevenue(getProfitOrRevenue(revenue));
		
		String numberOfEmployees = getValueFromChildElement(node, "numberOfEmployees");
		if (numberOfEmployees != null) 
			company.setNumberOfEmployees(Integer.parseInt(numberOfEmployees));

		try { // convert dateFounded string
			String date = getValueFromChildElement(node, "dateFounded");
			if(date != null) {
				DateTime dt = DateTime.parse(date);
				company.setDateFounded(dt);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		company.setHeadquarters(getValueFromChildElement(node, "headquarters"));
		String profit = getValueFromChildElement(node, "profit");
		company.setProfit(getProfitOrRevenue(profit));
		company.setKeyPeople(getValueFromChildElement(node, "keyPeople"));
		
		return company;
	}
	
	private long getProfitOrRevenue(String input) {
		if (input == null) 
			return -1;
		
		Double value = Double.valueOf(input);
		if (value < 100) {
			value *= 10^9;
		}
		
		return value.longValue();
	}
	
	/**
	 * @param String like England;;United Kingdom;;null
	 * @return Normalized version of String (in this case United Kingdom)
	 */
	private String normalizeCountries(String countries) {
		if (countries == null || countries.length() == 0) 
			return null;
		
		String[][] mapping = {
				{"United Kingdom", "UK", "England", "Britain"},
				{"United States of America", "US","USA","U.S","U.S.A","United States","America","Contiguous United States"}
		};
		
		//HashMap<String,String> mapping = new HashMap<>();
		String result = "";
		for (String c : countries.split(";;")) { //for each country part in England;;United Kingdom;;null
			String temp = c;
			for (String[] countryMapping : mapping) { //for each country defined in mapping
				if (Arrays.asList(countryMapping).contains(c)) {
					temp = countryMapping[0];
				}
			}
			if (result.indexOf(temp) == -1 && !temp.equals("null")) {
				result += temp + ";;";
			}
		}
		
		if (result.length() == 0) //for strings like null or null;;null
			return null;
		
		//System.out.println(result);
		result = result.substring(0, result.length()-2); //get rid of last two ;;
		return result;
	}

}
