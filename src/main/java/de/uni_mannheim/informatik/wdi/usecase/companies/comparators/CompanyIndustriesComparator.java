package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

/**
 * Choose the highest similarity between two arrays of industries
 * @author Oliver
 *
 */
public class CompanyIndustriesComparator extends Comparator<Company> {
	
	private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
	
	@Override
	public double compare(Company c1, Company c2) {
		if (c1.getIndustries() == null || c2.getIndustries() == null)
			return 0;
		
		String[] i1s = c1.getIndustries().split(";;");
		String[] i2s = c2.getIndustries().split(";;");
		
		double result = -1;
		for (String i1 : i1s) {
			for (String i2 : i2s) {
				double calc = sim.calculate(i1, i2);
				if (result <= calc) result = calc;
			}
		}
		//this would result in similarity being pretty low 0.5 with real values
		
		
		return result;
	}

}
