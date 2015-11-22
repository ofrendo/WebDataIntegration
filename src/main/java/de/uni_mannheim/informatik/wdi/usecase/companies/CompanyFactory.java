package de.uni_mannheim.informatik.wdi.usecase.companies;

import java.util.ArrayList;
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
	private String printCompanyID = null;
	private String attributeToCount = null;
	public int attributeCounter = 0;
	
	public CompanyFactory(String attributeToCount, String printCompanyID) {
		this.attributeToCount = attributeToCount;
		this.printCompanyID = printCompanyID;
	}
	
	@Override
	public Company createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "Company_ID");
		// create the object with id and provenance information
		Company company = new Company(id, provenanceInfo);
		company = this.createCompany(company, node, provenanceInfo);
		return company;
	}
	
	public Company createCompany(Company company, Node node, String provenanceInfo) {
		String name = getValueFromChildElement(node, "name");
		String countries = getValueFromChildElement(node, "countries");
		String continent = getValueFromChildElement(node, "continent");
		String industries = getValueFromChildElement(node, "industries");
		String revenue = getValueFromChildElement(node, "revenue");
		String numberOfEmployees = getValueFromChildElement(node, "numberOfEmployees");
		String date = getValueFromChildElement(node, "dateFounded");
		String profit = getValueFromChildElement(node, "profit");
		String assets = getValueFromChildElement(node, "assets");
		String marketValue = getValueFromChildElement(node, "marketValue");
		String keyPeople = getValueFromChildElement(node, "keyPeople");
		
		String attribute = getValueFromChildElement(node, attributeToCount);
		if (attribute != null && !attribute.equals("")) {
			attributeCounter++;
		}
		
		
		
		//Retrieve format from DBpedia
		if(provenanceInfo.contains("DBpedia")){
			name = Normalization.normalizeValueInDBpedia(name);
			countries = Normalization.normalizeValueInDBpedia(countries);
			industries = Normalization.normalizeValueInDBpedia(industries);
			//headquarters = Normalization.normalizeValueInDBpedia(headquarters);
			keyPeople = Normalization.normalizeValueInDBpedia(keyPeople);
		}

		//set value
		company.setName(Normalization.normalizeCompanyName(name));
		company.setOriginalName(name);
		company.setCountries(Normalization.normalizeCountries(countries));
		company.setOriginalCountries(countries);
		company.setContinent(continent);
		company.setIndustries(industries);
		company.setRevenue(Normalization.normalizeProfitOrRevenue(revenue));
		company.setNumberOfEmployees( numberOfEmployees != null ? Integer.parseInt(numberOfEmployees) : 0);
		company.setProfit(Normalization.normalizeProfitOrRevenue(profit));
		company.setAssets(Normalization.normalizeProfitOrRevenue(assets));
		company.setMarketValue(Normalization.normalizeProfitOrRevenue(marketValue));
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
		
		if (locations == null) {
			locations = new ArrayList<Location>();
		}
			
		
		if (company.getIdentifier().contains("Eleven") || company.getIdentifier().contains("Kyushu")) {
			//System.out.println(name);
			//System.out.println(locations);
		}
		
		company.setLocations(locations);
		if (company.getLocations() == null) {
			System.out.println("locations still null");
		}
		
		if (company.getIdentifier().equals(this.printCompanyID)) {
			System.out.println(company);
		}
//		System.out.println("get countries: "+company.getCountries());
		return company;
	}
	
	
	
}
