package de.uni_mannheim.informatik.wdi.usecase.companies;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.GoldStandard;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.MatchingEvaluator;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.Performance;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.Correspondence;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.MatchingEngine;
import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecord;
import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecordCSVFormatter;
import de.uni_mannheim.informatik.wdi.usecase.companies.blocking.CompanyLocationBlocker;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyLocationComparatorJaccard;
import de.uni_mannheim.informatik.wdi.usecase.companies.locations.comparators.CompanyLocationCountryComparator;

public class Locations_Main {

	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {

		String dbpediaXML = "data/mappingResults/IntegratedCompanyDBpedia.xml";
		String freebaseXML = "data/mappingResults/IntegratedCompanyFreebase.xml";
		
		DataSet<Company> dsCompanies = new DataSet<>();
		DataSet<Company> dsLocations = new DataSet<>();
		dsCompanies.loadFromXML(
				new File(dbpediaXML),
				new CompanyFactory(null, null), 
				"/companies/company");
		dsLocations.loadFromXML(
				new File("data/mappingResults/IntegratedLocationDBpedia.xml"),
				new CompanyFactory(null, null),   
				"/companies/company");
		
		CompanyLocationBlocker blocker = new CompanyLocationBlocker();
		
		double threshold = 1; //should be 0.5 always if using rapidminer
		double nameWeight = 1.0;
		double countryWeight = 0.195;
		double intercept = 0;
		
		LinearCombinationMatchingRule<Company> rule = new LinearCombinationMatchingRule<>(
				intercept, threshold);
		
		rule.addComparator(new CompanyLocationComparatorJaccard(), nameWeight);
		rule.addComparator(new CompanyLocationCountryComparator(), countryWeight);
		
		// create the matching engine
		MatchingEngine<Company> engine = new MatchingEngine<>(rule, blocker);
		
		// run the matching
		List<Correspondence<Company>> correspondences = engine.runMatching(dsCompanies, dsLocations);
		
		// write the correspondences to the output file
		engine.writeCorrespondences(correspondences, new File("data/resolutionResults/company_2_location_correspondences.csv"));
		
		//printCorrespondences(correspondences);
		
		// load the gold standard (training set)
		GoldStandard gsTraining = new GoldStandard();
		gsTraining.loadFromCSVFile(new File("data/goldstandard/location_goldstandard_train.csv"));

		// create the data set for learning a matching rule (use this file in RapidMiner)
		DataSet<DefaultRecord> features = engine
				.generateTrainingDataForLearning(dsCompanies, dsLocations, gsTraining);
		features.writeCSV(
				new File("data/resolutionResults/company_2_location_correspondences_features.csv"),
				new DefaultRecordCSVFormatter());
		
		// load the gold standard (test set)
		GoldStandard gsTest = new GoldStandard();
		gsTest.loadFromCSVFile(new File("data/goldstandard/location_goldstandard_test.csv"));

		// evaluate the result
		MatchingEvaluator<Company> evaluator = new MatchingEvaluator<>(true);
		Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);
		
		
		// print the evaluation result
		System.out.println(String.format(
				"Precision: %.4f\nRecall: %.4f\nF1: %.4f", perfTest.getPrecision(),
				perfTest.getRecall(), perfTest.getF1()));	
		
	}

}
