package uniMannheim.webDataIntegration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import au.com.bytecode.opencsv.CSVWriter;
import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.GoldStandard;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;
import de.uni_mannheim.informatik.wdi.usecase.companies.CompanyFactory;
import de.uni_mannheim.informatik.wdi.usecase.companies.normalization.Normalization;

public class Goldstandard_Replacing {

	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		String path = "data/goldstandard/forbes_freebase_goldstandard_train.csv";
		String resultPath = "data/goldstandard/forbes_freebase_goldstandard_train_ID.csv";
		
		DataSet<Company> dsFreebase = new DataSet<>();
		DataSet<Company> dsForbes = new DataSet<>();
		dsForbes.loadFromXML(
				new File("data/mappingResults/IntegratedCompanyForbes.xml"),
				new CompanyFactory(null, null),   "/companies/company");
		dsFreebase.loadFromXML(
				new File("data/mappingResults/IntegratedCompanyFreebase.xml"),
				new CompanyFactory(null, null), "/companies/company"); //"Freebase_Company_237"
		
		
		GoldStandard gs = new GoldStandard();
		gs.loadFromCSVFile(new File(path));
		
		//Replace names by IDs
		CSVWriter writer = new CSVWriter(new FileWriter(resultPath));
		for (Pair<String,String> pair : gs.getNegativeExamples()) {
			//First one is forbes, 2nd is freebase
			String nameForbes = Normalization.normalizeCompanyName(pair.getFirst());
			String idForbes = getCompanyIDByName(dsForbes, nameForbes);
			
			String nameFreebase = Normalization.normalizeCompanyName(pair.getSecond());
			String idFreebase = getCompanyIDByName(dsFreebase, nameFreebase);

			String result[] = {idForbes, idFreebase, "FALSE"};
			writer.writeNext(result);			
		}
		for (Pair<String,String> pair : gs.getPositiveExamples()) {
			//First one is forbes, 2nd is freebase
			String nameForbes = Normalization.normalizeCompanyName(pair.getFirst());
			String idForbes = getCompanyIDByName(dsForbes, nameForbes);
			
			String nameFreebase = Normalization.normalizeCompanyName(pair.getSecond());
			String idFreebase = getCompanyIDByName(dsFreebase, nameFreebase);
			
			String result[] = {idForbes, idFreebase, "TRUE"};
			writer.writeNext(result);			
		}
		
		writer.close();
	}
	
	public static String getCompanyIDByName(DataSet<Company> ds, String name) {
		for (Company c : ds.getRecords()) {
			if (c.getName().equals(name))
				return c.getIdentifier();
		}
		return null;
	}
	
}
