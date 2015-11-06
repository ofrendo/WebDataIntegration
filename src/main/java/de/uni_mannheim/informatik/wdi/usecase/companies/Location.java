package de.uni_mannheim.informatik.wdi.usecase.companies;

import de.uni_mannheim.informatik.wdi.Record;

/**
 * Main location class
 * @author Oliver
 *
 */
public class Location extends Record {
	
/* example entry
	<location>
		<name>Gomastapur Upazila</name>
		<population>321972</population>
		<area>3.1813e+08</area>
		<postalCode>6321</postalCode>
		<country>http://dbpedia.org/resource/Bangladesh</country>
	</location>
*/
	
	private String name;
	private int population;
	private long area;
	private String postalCode;
	private String country;
	
	public Location(String identifier, String provenance) {
		super(identifier, provenance);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public long getArea() {
		return area;
	}

	public void setArea(long area) {
		this.area = area;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
