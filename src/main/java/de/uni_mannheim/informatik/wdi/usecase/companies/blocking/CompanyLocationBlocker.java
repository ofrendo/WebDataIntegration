package de.uni_mannheim.informatik.wdi.usecase.companies.blocking;

import java.util.LinkedList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.identityresolution.blocking.Blocker;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

public class CompanyLocationBlocker extends Blocker<Company> {
	
	/**
	 * Partioning based on country of full company and country of location
	 */
	@Override
	public List<Pair<Company,Company>> generatePairs(DataSet<Company> ds1, DataSet<Company> ds2) {
		List<Pair<Company,Company>> result = new LinkedList<>();
		
		System.out.println("============= CompanyLocationBlocker ============");
		System.out.println("ds1.length=" + ds1.getSize());
		System.out.println("ds2.length=" + ds2.getSize());
		
		for (Company c1: ds1.getRecords()) {
			
			String key1;
			if (c1.getName() != null) {
				key1 = (c1.getCountries() != null) ? c1.getCountries() : "";
			}
			else {
				key1 = c1.getLocations().get(0).getCountry();
			}
			
			
			for (Company c2: ds2.getRecords()) {
				String key2;
				if (c2.getName() != null) {
					key2 = (c2.getCountries() != null) ? c2.getCountries() : "";
				}
				else {
					key2 = c2.getLocations().get(0).getCountry();
				}
				//System.out.println(key2);
				
				if(c1!=c2 && key1.equals(key2)) { 
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