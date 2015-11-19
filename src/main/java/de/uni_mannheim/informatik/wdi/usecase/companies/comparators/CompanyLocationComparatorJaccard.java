package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;


import java.util.List;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

public class CompanyLocationComparatorJaccard extends Comparator<Company> {

	@Override
	public double compare(Company c1, Company c2) {
		List l1 = c1.getLocations();
		List l2 = c2.getLocations();
		
		return 0;
	}

}
