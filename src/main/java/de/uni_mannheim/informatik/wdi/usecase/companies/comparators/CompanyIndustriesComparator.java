package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;
import de.uni_mannheim.informatik.wdi.usecase.companies.similarity.FuzzyLevenshteinSimilarity;

/**
 * Choose the highest similarity between two arrays of industries
 * @author Oliver
 *
 */
public class CompanyIndustriesComparator extends Comparator<Company> {
	
	private FuzzyLevenshteinSimilarity sim = new FuzzyLevenshteinSimilarity();
	
	@Override
	public double compare(Company c1, Company c2) {
		if (c1.getIndustries() == null || c2.getIndustries() == null)
			return 0;
		
		double similarity = sim.calculate(c1.getIndustries(), c2.getIndustries());
		return similarity;
	}
	
}
