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
	private String originalName;
	private int population;
	private long area;
	private int elevation;
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


	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getElevation() {
		return elevation;
	}

	public void setElevation(int elevation) {
		this.elevation = elevation;
	}
	
	@Override
	public String toString() {
		if (country == null) {
			return "\t name=" + getName();
		}
		else {
			return "\t name=" + getName() +
				   "\n\t originalName=" + getOriginalName() +
				   "\n\t population=" + getPopulation() + 
				   "\n\t area=" + getArea() +
				   "\n\t elevation=" + getElevation() + 
				   "\n\t country=" + getCountry();
		}
	}
	
	public double getCompleteness() {
		int count = 0;
		if (getName()!=null && !getName().isEmpty()) count++;
		if (getPopulation() > 0) count++;
		if (getArea() > 0) count++;
		if (getElevation() > 0) count++;
		if (getCountry()!=null && !getCountry().isEmpty()) count++;
		
		return (double) count / (double) 5;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	

}
