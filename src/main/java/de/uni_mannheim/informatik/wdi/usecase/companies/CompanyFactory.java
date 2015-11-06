package de.uni_mannheim.informatik.wdi.usecase.companies;

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
		
		// create the object with id and provenance information
		Company company = new Company(id, provenanceInfo);
		//System.out.println("Creating company " + getValueFromChildElement(node, "name"));
		
		// fill the attributes
		company.setName(getValueFromChildElement(node, "name"));
		company.setCountries(getValueFromChildElement(node, "countries"));
		company.setIndustries(getValueFromChildElement(node, "industries"));

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

}
