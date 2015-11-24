package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.wdi.datafusion.XMLFormatter;
import de.uni_mannheim.informatik.wdi.usecase.companies.Location;

public class LocationXMLFormatter extends XMLFormatter<Location> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("locations");
	}

	@Override
	public Element createElementFromRecord(Location record, Document doc) {
		Element location = doc.createElement("location");
		
		location.appendChild(createTextElement("name", record.getOriginalName(), doc));
		location.appendChild(createTextElement("population", 
				record.getPopulation() > 0 ?
				record.getPopulation() + "":
				"", doc));
		location.appendChild(createTextElement("area", 
				record.getArea() > 0 ?
				record.getArea() + "" :
				"", doc));
		location.appendChild(createTextElement("elevation", 
				record.getElevation() > 0 ?
				record.getElevation() + "" :
				"", doc));
		location.appendChild(createTextElement("country", record.getCountry(), doc));
		
		return location;
	}
	
}
