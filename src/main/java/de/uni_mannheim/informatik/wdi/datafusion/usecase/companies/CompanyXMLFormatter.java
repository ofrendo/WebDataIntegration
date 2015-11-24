package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.wdi.datafusion.FusableDataSet;
import de.uni_mannheim.informatik.wdi.datafusion.XMLFormatter;
import de.uni_mannheim.informatik.wdi.usecase.companies.Location;

public class CompanyXMLFormatter extends XMLFormatter<FusableCompany> {
	
	/*
<company>
	<Company_ID>Forbes_Company_15</Company_ID>	
	<name>Apple</name>
	<industries>Computer hardware;;Software;;Consumer electronics;;Technology;;Electronic Computer Manufacturing;;Digital distribution</industries>
	<revenue>234000000000</revenue> <!-- http://www.apple.com/pr/library/2015/10/27Apple-Reports-Record-Fourth-Quarter-Results.html -->
	<numberOfEmployees>92600</numberOfEmployees> <!-- http://www.statista.com/statistics/273439/number-of-employees-of-apple-since-2005/ -->
	<dateFounded>1976</dateFounded> <!--http://www.forbes.com/companies/apple/-->
	<assets>261890000000</assets> <!--http://www.forbes.com/companies/apple/-->
	<marketValue>741800000000</marketValue> <!--http://www.forbes.com/companies/apple/-->
	<profit>53400000000</profit> <!-- http://www.telegraph.co.uk/technology/apple/11959016/Apple-reports-biggest-annual-profit-in-history.html -->
	<continent>North America</continent>
	<keyPeople>Timothy Cook</keyPeople> <!--http://www.forbes.com/companies/apple/-->
	<countries>United States of America</countries>
	<locations>
		<location>
			<name>Cupertino</name> 
			<population>58302</population> <!-- https://suburbanstats.org/population/california/how-many-people-live-in-cupertino -->
			<area>29.16</area> <!-- https://en.wikipedia.org/wiki/Cupertino,_California -->
			<elevation>72</elevation> <!-- https://en.wikipedia.org/wiki/Cupertino,_California -->
			<country>http://dbpedia.org/resource/United_States</country>
		</location>
	</locations>
</company>
	 */
	
	private static int counter = 1;
	private LocationXMLFormatter locationFormatter = new LocationXMLFormatter();
	private ArrayList<FusableDataSet<FusableCompany>> datasets;
	
	public CompanyXMLFormatter(ArrayList<FusableDataSet<FusableCompany>> datasets) {
		this.datasets = datasets;
	}
	
	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("companies");
	}

	@Override
	public Element createElementFromRecord(FusableCompany record, Document doc) {
		Element company = doc.createElement("company");

		company.appendChild(createTextElement("fusion_counter", "" + counter++, doc));
		company.appendChild(createTextElement("id", record.getIdentifier(), doc));
		
		String nameProvenanceID = record.getMergedAttributeProvenance(FusableCompany.NAME);
		String originalName = null;
		for (FusableDataSet<FusableCompany> dataset : datasets) {
			for (FusableCompany fusableCompanyName : dataset.getRecords()) {
				if (fusableCompanyName.getIdentifier().equals(nameProvenanceID)) {
					originalName = fusableCompanyName.getOriginalName();
				}
			}
		}
		company.appendChild(createTextElementWithProvenance("name", originalName, nameProvenanceID, doc));
		
		/*String countriesProvenanceID = record.getMergedAttributeProvenance(FusableCompany.COUNTRIES);
		String originalCountries = null;
		for (FusableDataSet<FusableCompany> dataset : datasets) {
			for (FusableCompany fusableCompanyCountries : dataset.getRecords()) {
				if (fusableCompanyCountries.getIdentifier().equals(countriesProvenanceID)) {
					originalCountries = fusableCompanyCountries.getOriginalCountries();
				}
			}
		}*/
		//System.out.println(countriesProvenanceID);
		company.appendChild(createTextElementWithProvenance(
				"countries", record.getCountries(),
				record.getMergedAttributeProvenance(FusableCompany.COUNTRIES), doc));
		company.appendChild(createTextElementWithProvenance(
				"industries", record.getIndustries(),
				record.getMergedAttributeProvenance(FusableCompany.INDUSTRIES), doc));
		
		long revenue = record.getRevenue();
		company.appendChild(createTextElementWithProvenance(
				"revenue", revenue > 0 ?
						   "" + revenue :
						   "",
				record.getMergedAttributeProvenance(FusableCompany.REVENUE), doc));
		
		int numberOfEmployees = record.getNumberOfEmployees();
		company.appendChild(createTextElementWithProvenance(
				"numberOfEmployees", numberOfEmployees > 0 ?
						   "" + numberOfEmployees :
						   "",
				record.getMergedAttributeProvenance(FusableCompany.NUMBER_OF_EMPLOYEES), doc));
		
		company.appendChild(createTextElementWithProvenance(
				"dateFounded", 
				record.getDateFounded() != null ?
						record.getDateFounded().toString("dd-MM-yyyy") :
						"",
				record.getMergedAttributeProvenance(FusableCompany.DATE_FOUNDED), doc));
		
		company.appendChild(createTextElementWithProvenance(
				"assets", record.getAssets() > 0 ?
						   "" + record.getAssets() :
						   "",
				record.getMergedAttributeProvenance(FusableCompany.ASSETS), doc));
		
		company.appendChild(createTextElementWithProvenance(
				"marketValue", record.getMarketValue() > 0 ?
						   "" + record.getMarketValue() :
						   "",
				record.getMergedAttributeProvenance(FusableCompany.MARKET_VALUE), doc));
		
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
