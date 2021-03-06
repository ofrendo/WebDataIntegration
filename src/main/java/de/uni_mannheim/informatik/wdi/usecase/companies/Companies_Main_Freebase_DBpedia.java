package de.uni_mannheim.informatik.wdi.usecase.companies;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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
import de.uni_mannheim.informatik.wdi.usecase.companies.blocking.CompanyBlocker;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyCountriesComparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyDateFoundedComparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyIndustriesComparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyKeyPeopleComparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyLocationComparatorJaccard;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyNameComparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyNumericAttributeComparator;

public class Companies_Main_Freebase_DBpedia {

	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		String attributeToCount = "countries";
		DataSet<Company> dsFreebase = new DataSet<>();
		CompanyFactory freebaseFactory = new CompanyFactory(attributeToCount, 
				//null);
				"Freebase_Company_943"); //rosneft
		CompanyFactory dbpediaFactory = new CompanyFactory(attributeToCount, 
				//null);
				"DBPedia_Company_15465");
		DataSet<Company> dsDBpedia = new DataSet<>();
		dsFreebase.loadFromXML(
				new File("data/mappingResults/IntegratedCompanyFreebase.xml"), freebaseFactory, "/companies/company");
		dsDBpedia.loadFromXML(
				new File("data/mappingResults/IntegratedCompanyDBpedia.xml"), dbpediaFactory, "/companies/company");
		
		System.out.println("Attribute count for " + attributeToCount);
		System.out.println("freebaseFactory attributeCount=" + freebaseFactory.attributeCounter);
		System.out.println("dbpediaFactory attributeCount=" + dbpediaFactory.attributeCounter);
		
		//Blocker<Company> blocker = new CrossProductBlocker<>();
		//Blocker<Company> blocker = new PartitioningBlocker<>(new CompanyCountryBlockingFunction());
		//Blocker<Company> blocker = new PartitioningBlocker<>(new CompanyDateFoundedBlockingFunction());
		CompanyBlocker blocker = new CompanyBlocker();
		
		//Results from rapidminer
		double threshold = 0.67; //was 0.67, good value ~667 correspondences
		double nameWeight = 0.689;
		double countriesWeight = 0.088;
		double industriesWeight = 0.025;
		
		double revenueWeight = 0.0;
		double numberOfEmployeesWeight = 0.0;
		double dateFoundedWeight = 0.170;
		
		double keyPeopleWeight = 0.377;
		double locationsWeight = 0.218; //only comparing names of headquarters at this point
		
		double intercept = -0.135;
		
		LinearCombinationMatchingRule<Company> rule = new LinearCombinationMatchingRule<>(
				intercept, threshold
				//);
				, "Freebase_Company_943", "DBPedia_Company_15465");
				//, "4INFO", "http://dbpedia.org/resource/Jive_Software");
				//, "http://dbpedia.org/resource/The_Coca-Cola_Company", "The Coca-Cola Company");
				
		//Need to be careful: 
		// SAP and SAP SE
		// China Citic Bank, China Merchants Bank
		rule.addComparator(new CompanyNameComparator(), nameWeight);
		rule.addComparator(new CompanyCountriesComparator(), countriesWeight);
		rule.addComparator(new CompanyIndustriesComparator(), industriesWeight);
		
		//Comparison of numeric values relies on max percentage difference. i.e. revenue of 100 and 120 leads to
				// 1 - ((120-100)/120)/0.5 = 0.66667
		rule.addComparator(new CompanyNumericAttributeComparator("revenue", 0.5), revenueWeight); //seems this is not usable to compare!
		rule.addComparator(new CompanyNumericAttributeComparator("numberOfEmployees", 0.5), numberOfEmployeesWeight);
		rule.addComparator(new CompanyDateFoundedComparator(), dateFoundedWeight);
		
		rule.addComparator(new CompanyKeyPeopleComparator(), keyPeopleWeight);
		rule.addComparator(new CompanyLocationComparatorJaccard(), locationsWeight);
		
		
		
		// create the matching engine
		MatchingEngine<Company> engine = new MatchingEngine<>(rule, blocker);
		
		// run the matching
		List<Correspondence<Company>> correspondences = engine.runMatching(dsFreebase, dsDBpedia);
		
		// write the correspondences to the output file
		engine.writeCorrespondences(correspondences, new File("data/resolutionResults/companyFreebase_2_companyDBpedia_correspondences.csv"));
		
		//printCorrespondences(correspondences);
		
		// load the gold standard (training set)
		GoldStandard gsTraining = new GoldStandard();
		gsTraining.loadFromCSVFile(new File("data/goldstandard/dbpedia_freebase_goldstandard_train_ID.csv"));

		// create the data set for learning a matching rule (use this file in RapidMiner)
		DataSet<DefaultRecord> features = engine
				.generateTrainingDataForLearning(dsDBpedia, dsFreebase, gsTraining);
		features.writeCSV(
				new File("data/resolutionResults/companyFreebase_2_companyDBpedia_correspondences_features.csv"),
				new DefaultRecordCSVFormatter());
		
		// load the gold standard (test set)
		GoldStandard gsTest = new GoldStandard();
		gsTest.loadFromCSVFile(new File("data/goldstandard/dbpedia_freebase_goldstandard_test_ID.csv"));

		// evaluate the result
		MatchingEvaluator<Company> evaluator = new MatchingEvaluator<>(true);
		Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);
		
		
		// print the evaluation result
		System.out.println(String.format(
				"Precision: %.4f\nRecall: %.4f\nF1: %.4f", perfTest.getPrecision(),
				perfTest.getRecall(), perfTest.getF1()));
	}
	
	public static void printCorrespondences(List<Correspondence<Company>> correspondences) {
		// sort the correspondences
		Collections.sort(correspondences, new Comparator<Correspondence<Company>>() {

			@Override
			public int compare(Correspondence<Company> o1, Correspondence<Company> o2) {
				int score = Double.compare(o1.getSimilarityScore(), o2.getSimilarityScore());
				int title = o1.getFirstRecord().getName().compareTo(o2.getFirstRecord().getName());
				
				if(score!=0) {
					return -score;
				} else {
					return title;
				}
			}

		});
		
		// print the correspondences
		for (Correspondence<Company> correspondence : correspondences) {
			if(correspondence.getSimilarityScore()<1.0) {
				System.out.println(String.format("%s,%s,|\t\t%.2f\t[%s] %s (%s) <--> [%s] %s (%s)",
						correspondence.getFirstRecord().getIdentifier(),
						correspondence.getSecondRecord().getIdentifier(),
						correspondence.getSimilarityScore(), correspondence
								.getFirstRecord().getIdentifier(), correspondence
								.getFirstRecord().getName(), correspondence.getFirstRecord()
								.getDateFounded().toString("YYYY-MM-DD"), correspondence
								.getSecondRecord().getIdentifier(), correspondence
								.getSecondRecord().getName(), correspondence
								.getSecondRecord().getDateFounded().toString("YYYY-MM-DD")));
			}
		}
	}

}
