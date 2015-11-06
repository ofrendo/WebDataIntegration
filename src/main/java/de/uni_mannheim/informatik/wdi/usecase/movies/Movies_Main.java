package de.uni_mannheim.informatik.wdi.usecase.movies;

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
import de.uni_mannheim.informatik.wdi.identityresolution.blocking.PartitioningBlocker;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.GoldStandard;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.MatchingEvaluator;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.Performance;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.Correspondence;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.MatchingEngine;
import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecord;
import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecordCSVFormatter;
import de.uni_mannheim.informatik.wdi.usecase.movies.comparators.MovieDateComparator;
import de.uni_mannheim.informatik.wdi.usecase.movies.comparators.MovieTitleComparator;

public class Movies_Main {

	public static void main(String[] args) throws XPathExpressionException,
			ParserConfigurationException, SAXException, IOException {

		// define the matching rule
		LinearCombinationMatchingRule<Movie> rule = new LinearCombinationMatchingRule<>(
				-1.497, 0.5);
		rule.addComparator(new MovieTitleComparator(), 1.849);
		rule.addComparator(new MovieDateComparator(), 0.822);
		
		// create the matching engine
		Blocker<Movie> blocker = new PartitioningBlocker<>(new MovieBlockingFunction());
		MatchingEngine<Movie> engine = new MatchingEngine<>(rule, blocker);

		// load the data sets
		DataSet<Movie> ds1 = new DataSet<>();
		DataSet<Movie> ds2 = new DataSet<>();
		ds1.loadFromXML(
				new File("usecase/movie/input/academy_awards.xml"),
				new MovieFactory(), "/movies/movie");
		ds2.loadFromXML(
				new File("usecase/movie/input/actors.xml"),
				new MovieFactory(), "/movies/movie");
		
		// run the matching
		List<Correspondence<Movie>> correspondences = engine
				.runMatching(ds1, ds2);

		// write the correspondences to the output file
		engine.writeCorrespondences(correspondences, new File("usecase/movie/output/academy_awards_2_actors_correspondences.csv"));
		
		printCorrespondences(correspondences);
		
		// load the gold standard (training set)
		GoldStandard gsTraining = new GoldStandard();
		gsTraining.loadFromCSVFile(new File(
				"usecase/movie/goldstandard/gs_academy_awards_2_actors.csv"));

		// create the data set for learning a matching rule (use this file in RapidMiner)
		DataSet<DefaultRecord> features = engine
				.generateTrainingDataForLearning(ds1, ds2, gsTraining);
		features.writeCSV(
				new File(
						"usecase/movie/output/optimisation/academy_awards_2_actors_features.csv"),
				new DefaultRecordCSVFormatter());
		
		// load the gold standard (test set)
		GoldStandard gsTest = new GoldStandard();
		gsTest.loadFromCSVFile(new File(
				"usecase/movie/goldstandard/gs_academy_awards_2_actors_test.csv"));

		// evaluate the result
		MatchingEvaluator<Movie> evaluator = new MatchingEvaluator<>(true);
		Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);
		
		// print the evaluation result
		System.out.println(String.format(
				"Precision: %.4f\nRecall: %.4f\nF1: %.4f", perfTest.getPrecision(),
				perfTest.getRecall(), perfTest.getF1()));
	}
	
	private static void printCorrespondences(List<Correspondence<Movie>> correspondences) {
		// sort the correspondences
		Collections.sort(correspondences, new Comparator<Correspondence<Movie>>() {

			@Override
			public int compare(Correspondence<Movie> o1, Correspondence<Movie> o2) {
				int score = Double.compare(o1.getSimilarityScore(), o2.getSimilarityScore());
				int title = o1.getFirstRecord().getTitle().compareTo(o2.getFirstRecord().getTitle());
				
				if(score!=0) {
					return -score;
				} else {
					return title;
				}
			}

		});
		
		// print the correspondences
		for (Correspondence<Movie> correspondence : correspondences) {
			if(correspondence.getSimilarityScore()<1.0) {
				System.out.println(String.format("%s,%s,|\t\t%.2f\t[%s] %s (%s) <--> [%s] %s (%s)",
						correspondence.getFirstRecord().getIdentifier(),
						correspondence.getSecondRecord().getIdentifier(),
						correspondence.getSimilarityScore(),
						correspondence.getFirstRecord().getIdentifier(), correspondence
								.getFirstRecord().getTitle(), correspondence.getFirstRecord()
								.getDate().toString("YYYY-MM-DD"), correspondence
								.getSecondRecord().getIdentifier(), correspondence
								.getSecondRecord().getTitle(), correspondence.getSecondRecord()
								.getDate().toString("YYYY-MM-DD")));
			}
		}
	}

}
