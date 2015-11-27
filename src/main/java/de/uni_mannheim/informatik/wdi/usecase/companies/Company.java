package de.uni_mannheim.informatik.wdi.usecase.companies;

import java.util.ArrayList;
import java.util.List;

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
	private String originalName;
	private String countries;
	private String originalCountries;
	private String continent;
	private String industries;
	private long revenue = 0;
	private int numberOfEmployees = 0;
	private DateTime dateFounded;
	private long profit = 0;
	private long assets = 0;
	private long marketValue = 0;
	private String keyPeople;
	private List<Location> locations;
	
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

	public List<Location> getLocations() {
		if (locations == null) {
			return new ArrayList<Location>();
		}
		else {
			return locations;
		}
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	
	@Override
	public String toString() {
		return 	"======================" +
				"\nProvenance: " + getProvenance() +
				"\nID: " + getIdentifier() + 
				"\nName: " + getName() + 
				"\nOriginalName: " + getOriginalName() +
				"\ncountries: " + getCountries() + 
				"\nOriginalCountries: " + getOriginalCountries() +
				"\nIndustries: " + getIndustries() + 
				"\nRevenue: " + getRevenue() +
				"\nNumberOfEmployees: " + getNumberOfEmployees() + 
				"\nDateFounded: " + getDateFounded() + 
				"\nProfit: " + getProfit() +
				"\nAssets: " + getAssets() + 
				"\nMarketValue: " + getMarketValue() +
				"\nKeyPeople: " + getKeyPeople() + 
				"\nLocations: \n" + getLocationsString() +
				"======================";
	}
	private String getLocationsString() {
		String result = "";
		for (Location l : getLocations()) {
			result += l.toString() + "\n";
		}
		return result;
	}

	public long getAssets() {
		return assets;
	}

	public void setAssets(long assets) {
		this.assets = assets;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public long getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(long marketValue) {
		this.marketValue = marketValue;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getOriginalCountries() {
		return originalCountries;
	}

	public void setOriginalCountries(String originalCountries) {
		this.originalCountries = originalCountries;
	}
	
	public double getCompleteness() {
		int count = 0;
		if (getName()!=null && !getName().isEmpty()) count++;
		if (getIndustries()!=null && !getIndustries().isEmpty()) count++;
		if (getRevenue() > 0) count++;
		if (getNumberOfEmployees() > 0) count ++;
		if (getDateFounded() != null) count++;
		if (getAssets() > 0) count++;
		if (getMarketValue() > 0) count++;
		if (getProfit() > 0) count++;
		if (getContinent()!=null && !getContinent().isEmpty()) count++;
		if (getKeyPeople()!=null && !getKeyPeople().isEmpty()) count++;
		if (getCountries()!=null && !getCountries().isEmpty()) count++;
		if (getLocations().size() > 0) count++;
		
		return (double) count / (double) 12;
	}
}


















