package de.uni_mannheim.informatik.wdi.datafusion.usecase.movies;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.xml.sax.SAXException;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.wdi.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.wdi.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.wdi.datafusion.FusableDataSet;
import de.uni_mannheim.informatik.wdi.datafusion.evaluation.DataFusionEvaluator;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.evaluation.ActorsEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.evaluation.DateEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.evaluation.DirectorEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.evaluation.TitleEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.fusers.ActorsFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.fusers.DateFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.fusers.DirectorFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.fusers.TitleFuser;

public class Movies_Main {

	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerException {
		// load the data sets
		FusableDataSet<FusableMovie> ds1 = new FusableDataSet<>();
		FusableDataSet<FusableMovie> ds2 = new FusableDataSet<>();
		FusableDataSet<FusableMovie> ds3 = new FusableDataSet<>();
		ds1.loadFromXML(
				new File("usecase/movie/input/academy_awards.xml"),
				new FusableMovieFactory(), "/movies/movie");
		ds2.loadFromXML(
				new File("usecase/movie/input/actors.xml"),
				new FusableMovieFactory(), "/movies/movie");
		ds3.loadFromXML(
				new File("usecase/movie/input/golden_globes.xml"), 
				new FusableMovieFactory(), 
				"/movies/movie");
		
		// set dataset metadata
		ds1.setScore(1.0);
		ds2.setScore(2.0);
		ds3.setScore(3.0);
		ds1.setDate(DateTime.parse("2012-01-01"));
		ds2.setDate(DateTime.parse("2010-01-01"));
		ds3.setDate(DateTime.parse("2008-01-01"));
		
		// print dataset density
		System.out.println("academy_awards.xml");
		ds1.printDataSetDensityReport();
		System.out.println("actors.xml");
		ds2.printDataSetDensityReport();
		System.out.println("golden_globes.xml");
		ds3.printDataSetDensityReport();
		
		// load the correspondences
		CorrespondenceSet<FusableMovie> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(new File("usecase/movie/correspondences/academy_awards_2_actors_correspondences.csv"), ds1, ds2);
		correspondences.loadCorrespondences(new File("usecase/movie/correspondences/actors_2_golden_globes_correspondences.csv"), ds2, ds3);
		
		// write group size distribution
		correspondences.writeGroupSizeDistribution(new File("usecase/movie/output/group_size_distribution.csv"));
		
		// define the fusion strategy
		DataFusionStrategy<FusableMovie> strategy = new DataFusionStrategy<>(new FusableMovieFactory());
		// add attribute fusers
		// Note: The attribute name is only used for printing the reports
		strategy.addAttributeFuser("Title", new TitleFuser(), new TitleEvaluationRule());
		strategy.addAttributeFuser("Director", new DirectorFuser(), new DirectorEvaluationRule());
		strategy.addAttributeFuser("Date", new DateFuser(), new DateEvaluationRule());
		strategy.addAttributeFuser("Actors", new ActorsFuser(), new ActorsEvaluationRule());
		
		// create the fusion engine
		DataFusionEngine<FusableMovie> engine = new DataFusionEngine<>(strategy);
		
		// calculate cluster consistency
		engine.printClusterConsistencyReport(correspondences);
		
		// run the fusion
		FusableDataSet<FusableMovie> fusedDataSet = engine.run(correspondences);
		
		// write the result
		fusedDataSet.writeXML(new File("usecase/movie/output/fused.xml"), new MovieXMLFormatter());
		
		// load the gold standard
		DataSet<FusableMovie> gs = new FusableDataSet<>();
		gs.loadFromXML(
				new File("usecase/movie/goldstandard/fused.xml"),
				new FusableMovieFactory(), "/movies/movie");
		
		// evaluate
		DataFusionEvaluator<FusableMovie> evaluator = new DataFusionEvaluator<>(strategy);
		evaluator.setVerbose(true);
		double accuracy = evaluator.evaluate(fusedDataSet, gs);
		
		System.out.println(String.format("Accuracy: %.2f", accuracy));
		
	}
	
}
