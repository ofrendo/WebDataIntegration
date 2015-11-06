package de.uni_mannheim.informatik.wdi.usecase.companies;

import org.joda.time.DateTime;

import de.uni_mannheim.informatik.wdi.Record;

/**
 * Main company class
 * @author Oliver
 *
 */
public class Company extends Record {

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
	
	private String name;
	private String countries;
	private String industries;
	private long revenue = -1;
	private int numberOfEmployees = -1;
	private DateTime dateFounded;
	private String headquarters;
	private long profit = -1;
	private String keyPeople;
	
	public Company(String identifier, String provenance) {
		super(identifier, provenance);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountries() {
		return countries;
	}

	public void setCountries(String countries) {
		this.countries = countries;
	}

	public long getRevenue() {
		return revenue;
	}

	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}

	public int getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(int numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public DateTime getDateFounded() {
		return dateFounded;
	}

	public void setDateFounded(DateTime dateFounded) {
		this.dateFounded = dateFounded;
	}

	public String getHeadquarters() {
		return headquarters;
	}

	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}

	public long getProfit() {
		return profit;
	}

	public void setProfit(long profit) {
		this.profit = profit;
	}

	public String getKeyPeople() {
		return keyPeople;
	}

	public void setKeyPeople(String keyPeople) {
		this.keyPeople = keyPeople;
	}

	public String getIndustries() {
		return industries;
	}

	public void setIndustries(String industries) {
		this.industries = industries;
	}

}
