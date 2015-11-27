package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;


import java.util.List;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;
import de.uni_mannheim.informatik.wdi.usecase.companies.Location;
import de.uni_mannheim.informatik.wdi.usecase.companies.similarity.FuzzyLevenshteinSimilarity;

public class CompanyLocationComparatorJaccard extends Comparator<Company> {
	
	private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
	
	@Override
	public double compare(Company c1, Company c2) {
		List<Location> l1 = c1.getLocations();
		List<Location> l2 = c2.getLocations();

		//Only compare name of location (and country?)
		if (l1 == null || l2 == null) {
			return 0;
		}
		
		// Normal Jaccard
		String l1s = getConcatenatedLocationNames(l1);
		String l2s = getConcatenatedLocationNames(l2);
		l1s = l1s.replaceAll(";;", " ");
		l2s = l2s.replaceAll(";;", " ");
		return sim.calculate(l1s, l2s);
		
		// Highest Jaccard
		/*String l1s[] = getConcatenatedLocationNames(l1).split(";;");
		String l2s[] = getConcatenatedLocationNames(l2).split(";;");
		
		double result = 0;
		for (String name1 : l1s) {
			for (String name2 : l2s) {
				double calc = sim.calculate(name1, name2);
				if (result <= calc) result = calc;
			}
		}
		
		return result;*/
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
