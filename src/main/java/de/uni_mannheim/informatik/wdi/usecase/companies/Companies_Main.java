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
import de.uni_mannheim.informatik.wdi.identityresolution.blocking.Blocker;
import de.uni_mannheim.informatik.wdi.identityresolution.blocking.CrossProductBlocker;
import de.uni_mannheim.informatik.wdi.identityresolution.blocking.PartitioningBlocker;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.GoldStandard;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.MatchingEvaluator;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.Performance;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.Correspondence;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.MatchingEngine;
import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecord;
import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecordCSVFormatter;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyNumericAttributeComparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyStringAttributeComparatorJaccard;
import de.uni_mannheim.informatik.wdi.usecase.movies.Movie;
import de.uni_mannheim.informatik.wdi.usecase.movies.MovieBlockingFunction;

/**
 * 
 * @author Oliver
 *
 */
public class Companies_Main {

/* example entry
	<company>
		<name>1-800-Flowers</name>
		<countries>United States of America</countries>
		<industries>Retail;;Information technology;;Gift, Novelty, and Souvenir Stores</industries>
		<revenue>912600000</revenue>
		<numberOfEmployees>3700</numberOfEmployees>
		<dateFounded>1976</dateFounded>
		<headquarters>Town of North Hempstead</headquarters>
		<profit>-98420000</profit>
		<keyPeople>James F. Mccann;;Christopher G. Mccann;;William E Shea;;Stephen J Bozzo;;David Taiclet;;Gerard M Gallagher</keyPeople>
	</company>
*/
	
	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {

		DataSet<Company> dsFreebase = new DataSet<>();
		DataSet<Company> dsForbes = new DataSet<>();
		dsFreebase.loadFromXML(
				new File("data/mappingResults/IntegratedCompanyFreebase.xml"),
				new CompanyFactory("freebase"), "/companies/company");
		dsForbes.loadFromXML(
				new File("data/mappingResults/IntegratedCompanyForbes.xml"),
				new CompanyFactory("forbes"),   "/companies/company");
		
		LinearCombinationMatchingRule<Company> rule = new LinearCombinationMatchingRule<>(
				0, 1.5);
		rule.addComparator(new CompanyStringAttributeComparatorJaccard("name"), 1);
		rule.addComparator(new CompanyStringAttributeComparatorJaccard("countries"), 1);
		rule.addComparator(new CompanyStringAttributeComparatorJaccard("industries"), 1);
		//rule.addComparator(new CompanyStringAttributeComparatorJaccard("headquarters"), 1); // not in forbes
		//rule.addComparator(new CompanyStringAttributeComparatorJaccard("keyPeople"), 1); //not in forbes
		
		//Comparison of numeric values relies on max percentage difference. i.e. revenue of 100 and 120 leads to
		// 1 - ((120-100)/120)/0.5 = 0.66667
		rule.addComparator(new CompanyNumericAttributeComparator("revenue", 0.5), 0.5);
		//rule.addComparator(new CompanyNumericAttributeComparator("numberOfEmployees", max_percentage), 1); //not in forbes
		rule.addComparator(new CompanyNumericAttributeComparator("profit", 0.5), 0.5);
		
		// create the matching engine
		Blocker<Company> blocker = new PartitioningBlocker<>(new CompanyBlockingFunction());;
		MatchingEngine<Company> engine = new MatchingEngine<>(rule, blocker);
		
		// run the matching
		List<Correspondence<Company>> correspondences = engine.runMatching(dsForbes, dsFreebase);
		
		// write the correspondences to the output file
		engine.writeCorrespondences(correspondences, new File("data/resolutionResults/companyForbes_2_companyFreebase_correspondences.csv"));
		
		//printCorrespondences(correspondences);
		
		// load the gold standard (training set)
		GoldStandard gsTraining = new GoldStandard();
		gsTraining.loadFromCSVFile(new File("data/goldstandard/forbes_freebase_goldstandard_train.csv"));

		// create the data set for learning a matching rule (use this file in RapidMiner)
		DataSet<DefaultRecord> features = engine
				.generateTrainingDataForLearning(dsForbes, dsFreebase, gsTraining);
		features.writeCSV(
				new File("resolutionResults/optimisation/companyForbes_2_companyFreebase_correspondences_features.csv"),
				new DefaultRecordCSVFormatter());
		
		// load the gold standard (test set)
		GoldStandard gsTest = new GoldStandard();
		gsTest.loadFromCSVFile(new File("data/goldstandard/forbes_freebase_goldstandard_test.csv"));

		// evaluate the result
		MatchingEvaluator<Company> evaluator = new MatchingEvaluator<>(true);
		Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);
		
		
		// print the evaluation result
		System.out.println(String.format(
				"Precision: %.4f\nRecall: %.4f\nF1: %.4f", perfTest.getPrecision(),
				perfTest.getRecall(), perfTest.getF1()));
	}
	
	private static void printCorrespondences(List<Correspondence<Company>> correspondences) {
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
