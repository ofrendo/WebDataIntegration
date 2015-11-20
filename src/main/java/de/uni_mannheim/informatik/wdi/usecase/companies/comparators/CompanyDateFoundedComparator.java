package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.date.YearSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

public class CompanyDateFoundedComparator extends Comparator<Company> {
	
	YearSimilarity sim = new YearSimilarity(20);
	
	@Override
	public double compare(Company c1, Company c2) {
		if (c1.getDateFounded() == null || c2.getDateFounded() == null)
			return 0;
		
		return sim.calculate(c1.getDateFounded(), c2.getDateFounded());
	}
}
