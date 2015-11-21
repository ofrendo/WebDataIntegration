package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies;

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
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovieFactory;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.MovieXMLFormatter;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.evaluation.ActorsEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.evaluation.DateEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.evaluation.DirectorEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.evaluation.TitleEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.fusers.ActorsFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.fusers.DateFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.fusers.DirectorFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.fusers.TitleFuser;

public class Companies_Main {

	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerException {
		String printCompanyID = null;
		
		// load the data sets
		FusableDataSet<FusableCompany> dsForbes = new FusableDataSet<>();
		FusableDataSet<FusableCompany> dsFreebase = new FusableDataSet<>();
		FusableDataSet<FusableCompany> dsDBpedia = new FusableDataSet<>();
		FusableDataSet<FusableCompany> dsLocation = new FusableDataSet<>();
		dsForbes.loadFromXML(
				new File("data/mappingResults/IntegratedCompanyForbes.xml"),
				new FusableCompanyFactory(printCompanyID), "/companies/company");
		dsFreebase.loadFromXML(
				new File("data/mappingResults/IntegratedCompanyFreebase.xml"),
				new FusableCompanyFactory(printCompanyID), "/companies/company");
		//dsDBpedia.loadFromXML(
		//		new File("data/mappingResults/IntegratedCompanyDBpedia.xml"), 
	//			new FusableCompanyFactory(printCompanyID), "/companies/company");
		dsLocation.loadFromXML(
				new File("data/mappingResults/IntegratedLocationDBpedia.xml"), 
				new FusableCompanyFactory(printCompanyID), "/companies/company");
		
		// set dataset metadata
		dsForbes.setScore(2.0);
		dsFreebase.setScore(1.0);
		dsDBpedia.setScore(1.0);
		dsLocation.setScore(4.0);
		dsForbes.setDate(DateTime.parse("2014-01-01"));
		dsFreebase.setDate(DateTime.parse("2015-11-21"));
		dsDBpedia.setDate(DateTime.parse("2015-11-21"));
		dsLocation.setDate(DateTime.parse("2015-11-21"));
		// print dataset density
		System.out.println("IntegratedCompanyForbes.xml");
		dsForbes.printDataSetDensityReport();
		System.out.println("IntegratedCompanyFreebase.xml");
		dsFreebase.printDataSetDensityReport();
		System.out.println("IntegratedCompanyDBpedia.xml");
		dsDBpedia.printDataSetDensityReport();
		
		// load the correspondences
		CorrespondenceSet<FusableCompany> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(new File("data/resolutionResults/companyForbes_2_companyFreebase_correspondences.csv"), dsForbes, dsFreebase);
		//correspondences.loadCorrespondences(new File("data/resolutionResults/companyFreebase_2_companyDBpedia_correspondences.csv"), dsFreebase, dsDBpedia);
		
		/*
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
		
		System.out.println(String.format("Accuracy: %.2f", accuracy));*/
		
	}
	
}
