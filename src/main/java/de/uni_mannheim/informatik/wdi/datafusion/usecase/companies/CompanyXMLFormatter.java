package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.wdi.datafusion.XMLFormatter;
import de.uni_mannheim.informatik.wdi.usecase.companies.Location;

public class CompanyXMLFormatter extends XMLFormatter<FusableCompany> {
	
	LocationXMLFormatter locationFormatter = new LocationXMLFormatter();
	
	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("companies");
	}

	@Override
	public Element createElementFromRecord(FusableCompany record, Document doc) {
		Element company = doc.createElement("company");

		company.appendChild(createTextElement("id", record.getIdentifier(), doc));
		
		company.appendChild(createTextElementWithProvenance("name", record.getName(), record.getMergedAttributeProvenance(FusableCompany.NAME),doc));
		
		company.appendChild(createLocationsElement(record, doc));
		
		return company;
	}

	protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}
	
	protected Element createLocationsElement(FusableCompany record, Document doc) {
		Element locationsRoot = locationFormatter.createRootElement(doc);
		locationsRoot.setAttribute("provenance", record.getMergedAttributeProvenance(FusableCompany.LOCATIONS));
		
		for(Location l : record.getLocations()) {
			locationsRoot.appendChild(locationFormatter.createElementFromRecord(l, doc));
		}
		
		return locationsRoot;
	}
}
