package de.uni_mannheim.informatik.wdi.usecase.companies.blocking;

import java.util.LinkedList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.identityresolution.blocking.Blocker;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

public class CompanyBlocker extends Blocker<Company> {
	
	/**
	 * Partioning strategy based on Country first, then dateFounded as alternative
	 */
	@Override
	public List<Pair<Company,Company>> generatePairs(DataSet<Company> ds1, DataSet<Company> ds2) {
		List<Pair<Company,Company>> result = new LinkedList<>();
		
		CompanyCountryBlockingFunction f1 = new CompanyCountryBlockingFunction();
		CompanyDateFoundedBlockingFunction f2  = new CompanyDateFoundedBlockingFunction();
		
		for (Company c1: ds1.getRecords()) {
			String keyc1f1 = f1.getBlockingKey(c1);
			String keyc1f2 = f2.getBlockingKey(c1);
			
			for (Company c2: ds2.getRecords()) {
				String keyc2f1 = f1.getBlockingKey(c2);
				String keyc2f2 = f2.getBlockingKey(c2);
				if(c1!=c2 && keyc1f1!=null && keyc1f2!=null && 
				  (keyc1f1.equals(keyc2f1) || keyc1f2.equals(keyc2f2))) { //Generate a pair if one of the blocking functions match
					result.add(new Pair<Company,Company>(c1,c2));
				}
				/*if (c2.getIdentifier().equals("http://dbpedia.org/resource/Genzyme") && c1.getIdentifier().equals("Genzyme")) {
					System.out.println(c1);
					System.out.println(c2);
				}*/
			}
			
		}
		
		calculatePerformance(ds1, ds2, result);
		return result;
	}
	
}
