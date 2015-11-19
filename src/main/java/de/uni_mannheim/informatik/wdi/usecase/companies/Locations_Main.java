package de.uni_mannheim.informatik.wdi.usecase.companies;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.LinearCombinationMatchingRule;

public class Locations_Main {

	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {

		String dbpediaXML = "data/mappingResults/Integratedcompany_dbpedia.xml";
		String freebaseXML = "data/mappingResults/IntegratedCompanyFreebase.xml";
		
		DataSet<Company> dsCompanies = new DataSet<>();
		DataSet<Location> dsLocations = new DataSet<>();
		dsCompanies.loadFromXML(
				new File(dbpediaXML),
				new CompanyFactory("freebase"), "/companies/company");
		dsLocations.loadFromXML(
				new File("data/mappingResults/IntegratedLocation_DBpedia.xml"),
				new LocationFactory(),   "/locations/location");
		
		
		double threshold = 0.5; //should be 0.5 always
		double nameWeight = 0.899;
		double countryWeight = 0.195;
		double intercept = -1.117;
		
		LinearCombinationMatchingRule<Company> rule = new LinearCombinationMatchingRule<>(
				intercept, threshold);
		
		rule.addComparator(new CompanyLocationNameComparator(), nameWeight);
		rule.addComparator(new CompanyLocationCountryComparator(), countryWeight);
		
		
	}

}
