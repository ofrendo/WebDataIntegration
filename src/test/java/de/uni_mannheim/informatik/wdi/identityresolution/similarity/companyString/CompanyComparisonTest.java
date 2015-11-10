package de.uni_mannheim.informatik.wdi.identityresolution.similarity.companyString;

import de.uni_mannheim.informatik.wdi.usecase.companies.Company;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyStringAttributeComparatorJaccard;
import junit.framework.TestCase;

public class CompanyComparisonTest extends TestCase {

	public void testCalculateCompanySimilarity() {
		String n1 = "group name";
		Company c1 = new Company(n1, null); //Forbes
		c1.setName(n1);
		
		String n2 = "name group";		
		Company c2 = new Company(n2, null); //Freebase
		c2.setName(n2);
		
		CompanyStringAttributeComparatorJaccard nameCompare = new CompanyStringAttributeComparatorJaccard("name");
		System.out.println("Name comparison: " + nameCompare.compare(c1, c2));
	}

}

