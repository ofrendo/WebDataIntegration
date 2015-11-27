package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

/**
 * Choose the highest similarity between two arrays of industries
 * @author Oliver
 *
 */
public class CompanyCountriesComparator extends Comparator<Company> {
	
	private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
	
	@Override
	public double compare(Company c1, Company c2) {
		if (c1.getCountries() == null || c2.getCountries() == null) 
			return 0;
		
		// Equals comparator
		//return c1.getCountries().equals(c2.getCountries()) ? 1 : 0;
		
		// Jaccard comparator
		/*String c1s = c1.getCountries().replaceAll(";;", " ");
		String c2s = c2.getCountries().replaceAll(";;", " ");
		
		return sim.calculate(c1s, c2s);*/
		
		// Highest matching comparator
		String[] c1s = c1.getCountries().split(";;");
		String[] c2s = c2.getCountries().split(";;");
		
		double result = 0;
		for (String country1 : c1s) {
			for (String country2 : c2s) {
				double calc = sim.calculate(country1, country2);
				if (result <= calc) result = calc;
			}
		}
		
		return result;
	}

}
