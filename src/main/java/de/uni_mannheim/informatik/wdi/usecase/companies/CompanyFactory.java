package de.uni_mannheim.informatik.wdi.usecase.companies;

import java.util.List;

import org.joda.time.DateTime;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.wdi.MatchableFactory;
import de.uni_mannheim.informatik.wdi.usecase.companies.normalization.Normalization;

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
		String countries = getValueFromChildElement(node, "countries");
		String industries = getValueFromChildElement(node, "industries");
		String revenue = getValueFromChildElement(node, "revenue");
		String numberOfEmployees = getValueFromChildElement(node, "numberOfEmployees");
		String date = getValueFromChildElement(node, "dateFounded");
		String profit = getValueFromChildElement(node, "profit");
		String keyPeople = getValueFromChildElement(node, "keyPeople");

		// create the object with id and provenance information
		Company company = new Company(name, provenanceInfo);
		
		//Retrieve format from DBpedia
		if(provenanceInfo.contains("dbpedia")){
			name = Normalization.normalizeValueInDBpedia(name);
			countries = Normalization.normalizeValueInDBpedia(countries);
			industries = Normalization.normalizeValueInDBpedia(industries);
			//headquarters = Normalization.normalizeValueInDBpedia(headquarters);
			keyPeople = Normalization.normalizeValueInDBpedia(keyPeople);
		}

		//set value
		company.setName(Normalization.normalizeCompanyName(name));
		company.setCountries(Normalization.normalizeCountries(countries));
		company.setIndustries(industries);
		company.setRevenue(Normalization.normalizeProfitOrRevenue(revenue));
		company.setNumberOfEmployees( numberOfEmployees != null ? Integer.parseInt(numberOfEmployees) : 0);
		company.setProfit(Normalization.normalizeProfitOrRevenue(profit));
		company.setKeyPeople(keyPeople);

		try { // convert dateFounded string
			if(date != null && !date.equals("")) {
				String[] dateArr = date.split(";;");
				String finalDate = date;
				if(dateArr.length > 1){
					for(int i = 0;i < dateArr.length-1;i++){
						finalDate = Integer.parseInt(dateArr[i]) < Integer.parseInt(dateArr[i+1])? dateArr[i+1] : dateArr[i];
					}
				}
				DateTime dt = DateTime.parse(finalDate);
				company.setDateFounded(dt);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		List<Location> locations = getObjectListFromChildElement(node, "locations", "location", 
				new LocationFactory(), provenanceInfo);
		
		if (name.contains("Eleven") || name.contains("Kyushu")) {
			System.out.println(name);
			System.out.println(locations);
		}
		
		company.setLocations(locations);
		
		
//		System.out.println("get countries: "+company.getCountries());
		return company;
	}
	
	
	
	

	
	
	
	
}
