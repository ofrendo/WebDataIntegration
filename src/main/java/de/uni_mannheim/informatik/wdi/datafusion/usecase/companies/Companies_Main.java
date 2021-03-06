package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.AssetsEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.ContinentEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.CountriesEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.DateFoundedEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.IndustriesEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.KeyPeopleEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.LocationsEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.MarketValueEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.NameEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.NumberOfEmployeesEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.ProfitEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation.RevenueEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.AssetsFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.ContinentFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.CountriesFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.DateFoundedFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.IndustriesFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.KeyPeopleFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.LocationsFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.MarketValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.NameFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.NumberOfEmployeesFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.ProfitFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers.RevenueFuser;

public class Companies_Main {

/* SAMPLE company
<?xml version="1.0" encoding="UTF-8"?>
<companies>
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
</companies>
 */
	
	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerException {
		//String printCompanyID = "Forbes_Company_35";  //null;
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
		dsDBpedia.loadFromXML(
				new File("data/mappingResults/IntegratedCompanyDBpedia.xml"), 
				new FusableCompanyFactory(printCompanyID), "/companies/company");
		dsLocation.loadFromXML(
				new File("data/mappingResults/IntegratedLocationDBpedia.xml"), 
				new FusableCompanyFactory(printCompanyID), "/companies/company");
		
		// set dataset metadata
		dsForbes.setScore(2.0);
		dsFreebase.setScore(1.0);
		dsDBpedia.setScore(1.0);
		dsLocation.setScore(0.5);
		dsForbes.setDate(DateTime.parse("2014-01-01"));
		//dsFreebase.setDate(DateTime.parse("2015-11-21"));
		//dsDBpedia.setDate(DateTime.parse("2015-11-21"));
		//dsLocation.setDate(DateTime.parse("2015-11-21"));
		// print dataset density
//		System.out.println("IntegratedCompanyForbes.xml");
//		dsForbes.printDataSetDensityReport();
//		System.out.println("IntegratedCompanyFreebase.xml");
//		dsFreebase.printDataSetDensityReport();
//		System.out.println("IntegratedCompanyDBpedia.xml");
//		dsDBpedia.printDataSetDensityReport();
//		System.out.println("IntegratedLocationDBpedia.xml");
//		dsLocation.printDataSetDensityReport();
		
		// load the correspondences
		CorrespondenceSet<FusableCompany> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(
				new File("data/resolutionResults/companyForbes_2_companyFreebase_correspondences.csv"),
				dsForbes, dsFreebase,
				true);
		correspondences.loadCorrespondences(
				new File("data/resolutionResults/companyFreebase_2_companyDBpedia_correspondences.csv"), 
				dsFreebase, dsDBpedia,
				true);
		correspondences.loadCorrespondences(
				new File("data/resolutionResults/company_2_location_correspondences.csv"),
				dsDBpedia, dsLocation,
				false);
		
		
		// write group size distribution
		correspondences.writeGroupSizeDistribution(new File("data/fusionResults/group_size_distribution.csv"));
		
		// define the fusion strategy
		DataFusionStrategy<FusableCompany> strategy = new DataFusionStrategy<>(new FusableCompanyFactory(printCompanyID));
		// add attribute fusers
		// Note: The attribute name is only used for printing the reports
		strategy.addAttributeFuser("name", new NameFuser(), new NameEvaluationRule());
		strategy.addAttributeFuser("countries", new CountriesFuser(), new CountriesEvaluationRule());
		strategy.addAttributeFuser("industries", new IndustriesFuser(), new IndustriesEvaluationRule());
		strategy.addAttributeFuser("revenue", new RevenueFuser(), new RevenueEvaluationRule());
		strategy.addAttributeFuser("numberOfEmployees", new NumberOfEmployeesFuser(), new NumberOfEmployeesEvaluationRule());
		strategy.addAttributeFuser("dateFounded", new DateFoundedFuser(), new DateFoundedEvaluationRule());
		strategy.addAttributeFuser("assets", new AssetsFuser(), new AssetsEvaluationRule());
		strategy.addAttributeFuser("marketValue", new MarketValueFuser(), new MarketValueEvaluationRule());
		strategy.addAttributeFuser("profit", new ProfitFuser(), new ProfitEvaluationRule());
		strategy.addAttributeFuser("continent", new ContinentFuser(), new ContinentEvaluationRule());
		strategy.addAttributeFuser("keyPeople", new KeyPeopleFuser(), new KeyPeopleEvaluationRule());
		strategy.addAttributeFuser("locations", new LocationsFuser(), new LocationsEvaluationRule());
		
		// create the fusion engine
		DataFusionEngine<FusableCompany> engine = new DataFusionEngine<>(strategy);
		
		// calculate cluster consistency
		//engine.printClusterConsistencyReport(correspondences);
		
		// run the fusion
		FusableDataSet<FusableCompany> fusedDataSet = engine.run(correspondences);
		
		System.out.println("FUSED RESULT");
		//fusedDataSet.printDataSetDensityReport();
		
		// write the result
		ArrayList<FusableDataSet<FusableCompany>> datasets = new ArrayList<>();
		datasets.add(dsForbes);
		datasets.add(dsFreebase);
		datasets.add(dsDBpedia);
		datasets.add(dsLocation);
		fusedDataSet.writeXML(new File("data/fusionResults/fused.xml"), new CompanyXMLFormatter(datasets));
		
		// load the gold standard
		DataSet<FusableCompany> gs = new FusableDataSet<>();
		gs.loadFromXML(
				new File("data/goldstandard/fused.xml"),
				new FusableCompanyFactory(printCompanyID), "/companies/company");
		
		// evaluate
		DataFusionEvaluator<FusableCompany> evaluator = new DataFusionEvaluator<>(strategy);
		evaluator.setVerbose(true);
		double accuracy = evaluator.evaluate(fusedDataSet, gs);

		System.out.println(String.format("Accuracy: %.2f", accuracy));
		
	}
	
}
