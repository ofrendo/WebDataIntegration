package de.uni_mannheim.informatik.wdi.identityresolution.similarity.companyString;

import de.uni_mannheim.informatik.wdi.usecase.companies.Company;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyIndustriesComparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyStringAttributeComparatorJaccard;
import junit.framework.TestCase;

public class CompanyComparisonTest extends TestCase {

	public void testCalculateCompanySimilarity() {
		//String n1 = "Chevron";
		String n1 = "Kellogg";
		//String i1 = "National Commercial Banks;;Investment banking;;Financial Services;;Offices of Bank Holding Companies";
		String i1 = "Petroleum Refineries;;Mining;;Crude Petroleum and Natural Gas Extraction;;Petroleum industry";
		Company c1 = new Company(n1, null); //Forbes
		c1.setName(n1);
		c1.setIndustries(i1);
		
		String n2 = "Kelloggs";
		//String n2 = "Chevron Corporation";		
		//String i2 = "Major Banks";
		String i2 = "Oil & Gas Operations";
		Company c2 = new Company(n2, null); //Freebase
		c2.setName(n2);
		c2.setIndustries(i2);
		
		CompanyStringAttributeComparatorJaccard nameCompare = new CompanyStringAttributeComparatorJaccard("name");
		System.out.println("Name comparison: " + nameCompare.compare(c1, c2));
		System.out.println("Industries comparison: " +  
							new CompanyIndustriesComparator().compare(c1,  c2));
	}

}

