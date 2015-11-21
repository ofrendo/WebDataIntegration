package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

public class FusableCompany extends Company implements Fusable {

	private Map<String, Collection<String>> provenance = new HashMap<>();
	
	public void setRecordProvenance(Collection<String> provenance) {
		this.provenance.put("RECORD", provenance);
	}
	public Collection<String> getRecordProvenance() {
		return provenance.get("RECORD");
	}
	
	public void setAttributeProvenance(String attribute, Collection<String> provenance) {
		this.provenance.put(attribute, provenance);
	}
	public Collection<String> getAttributeProvenance(String attribute) {
		return provenance.get(attribute);
	}
	public String getMergedAttributeProvenance(String attribute) {
		Collection<String> prov = provenance.get(attribute);
		
		if(prov!=null) {
			return StringUtils.join(prov, "+");
		} else {
			return "";
		}
	}
	
	public FusableCompany(String identifier, String provenance) {
		super(identifier, provenance);
	}
	
	public static final String NAME = "name";
	public static final String INDUSTRIES = "industries";
	public static final String REVENUE = "revenue";
	public static final String NUMBER_OF_EMPLOYEES = "numberOfEmployees";
	public static final String DATE_FOUNDED = "dateFounded";
	public static final String ASSETS = "assets";
	public static final String MARKET_VALUE = "marketValue";
	public static final String PROFIT = "profit";
	public static final String CONTINENT = "contintent";
	public static final String KEY_PEOPLE = "keyPeople";
	public static final String COUNTRIES = "countries";
	public static final String LOCATIONS = "locations";
	
	@Override
	public Collection<String> getAttributeNames() {
		return Arrays.asList(new String[] { NAME, INDUSTRIES, REVENUE, NUMBER_OF_EMPLOYEES, 
				DATE_FOUNDED, ASSETS, MARKET_VALUE, PROFIT, CONTINENT,
				KEY_PEOPLE, COUNTRIES, LOCATIONS});
	}

	@Override
	public boolean hasValue(String attributeName) {
		switch (attributeName) {
		case NAME:
			return getName()!=null && !getName().isEmpty();
		case INDUSTRIES:
			return getIndustries()!=null && !getIndustries().isEmpty();
		case REVENUE:
			return getRevenue() > 0;
		case NUMBER_OF_EMPLOYEES:
			return getNumberOfEmployees() > 0;
		case DATE_FOUNDED:
			return getDateFounded()!=null;
		case ASSETS:
			return getAssets() > 0;
		case MARKET_VALUE:
			return getMarketValue() > 0;
		case PROFIT:
			return getProfit() > 0;
		case CONTINENT:
			return getContinent()!=null && !getContinent().isEmpty();
		case KEY_PEOPLE:
			return getKeyPeople()!=null && !getKeyPeople().isEmpty();
		case COUNTRIES:
			return getCountries()!=null && !getCountries().isEmpty();
		case LOCATIONS:
			return getLocations()!=null && getLocations().size() > 0;
		default:
			return false;
		}
	}

}
