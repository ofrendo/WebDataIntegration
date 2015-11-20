package de.uni_mannheim.informatik.wdi.usecase.companies;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.wdi.MatchableFactory;
import de.uni_mannheim.informatik.wdi.usecase.companies.normalization.Normalization;

/**
 * Creation of location classes
 * @author Oliver
 *
 */
public class LocationFactory extends MatchableFactory<Location> {

/* example entry
	<location>
		<name>Gomastapur Upazila</name>
		<population>321972</population>
		<area>3.1813e+08</area>
		<postalCode>6321</postalCode>
		<country>http://dbpedia.org/resource/Bangladesh</country>
	</location>
*/
	private int counter = 1;
	
	@Override
	public Location createModelFromElement(Node node, String provenanceInfo) {
		String id = "location_" + counter;
		counter++;
		
		// create the object with id and provenance information
		Location location = new Location(id, provenanceInfo);
		
		String name = getValueFromChildElement(node, "name");
		String populationString = getValueFromChildElement(node, "population");
		int population = populationString != null ? Integer.parseInt(populationString) : 0;
		String areaString = getValueFromChildElement(node, "area");
		long area = areaString != null ?  Double.valueOf(areaString).longValue() : 0;
		String elevationString = getValueFromChildElement(node, "elevation");
		int elevation = elevationString != null ? Double.valueOf(elevationString).intValue() : 0;
		String country = getValueFromChildElement(node, "country");
		
		name = Normalization.normalizeLocationName(name);
		country = Normalization.normalizeValueInDBpedia(country);
		country = Normalization.normalizeCountries(country);
		// fill the attributes
		location.setName(name);
		location.setPopulation(population);
		location.setArea(area);
		location.setElevation(elevation);
		location.setCountry(country);

		return location;
	}
}
