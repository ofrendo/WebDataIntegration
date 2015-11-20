package de.uni_mannheim.informatik.wdi.usecase.companies.locations.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

public class CompanyLocationCountryComparator extends Comparator<Company> {

	private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
	
	
	@Override
	public double compare(Company c1P, Company c2P) {

		// First find full company from freebase/dbpedia
		Company c1 = c1P.getName() != null ? c1P : c2P; //c1 is full company
		Company c2 = c1P.getName() == null ? c1P : c2P; //c2 is only location data
		
		String[] c1s = c1.getCountries().split(";;");
		String country2 = c2.getLocations().get(0).getCountry();
		
		double result = 0;
		for (String country1 : c1s) {
			double calc = sim.calculate(country1, country2);
			if (result <= calc) result = calc;
		}
		
		return result;
	}

}
