package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;


import java.util.List;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;
import de.uni_mannheim.informatik.wdi.usecase.companies.Location;
import de.uni_mannheim.informatik.wdi.usecase.companies.similarity.FuzzyLevenshteinSimilarity;

public class CompanyLocationComparatorJaccard extends Comparator<Company> {
	
	private FuzzyLevenshteinSimilarity sim = new FuzzyLevenshteinSimilarity();
	
	@Override
	public double compare(Company c1, Company c2) {
		List<Location> l1 = c1.getLocations();
		List<Location> l2 = c2.getLocations();

		//Only compare name of location (and country?)
		if (l1 == null || l2 == null) {
			return 0;
		}
		
		String h1 = getConcatenatedLocationNames(l1);
		String h2 = getConcatenatedLocationNames(l2);
		
		double similarity = sim.calculate(h1, h2);
		return similarity;
	}
	
	private String getConcatenatedLocationNames(List<Location> list) {
		String result = "";
		for (int i=0; i < list.size(); i++) {
			result += list.get(i).getName();
			if (i != list.size()-1) {
				result += ";;";
			}
		}
		return result;
	}

}
