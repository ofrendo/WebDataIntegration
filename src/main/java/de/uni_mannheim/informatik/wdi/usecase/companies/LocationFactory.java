package de.uni_mannheim.informatik.wdi.usecase.companies;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.wdi.MatchableFactory;

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
		
		// fill the attributes
		location.setName(getValueFromChildElement(node, "name"));
		location.setPopulation(Integer.parseInt(getValueFromChildElement(node, "population")));
		location.setArea(Double.valueOf(getValueFromChildElement(node, "area")).longValue());
		location.setPostalCode(getValueFromChildElement(node, "postalCode"));
		location.setCountry(getValueFromChildElement(node, "country"));

		return location;
	}
}
