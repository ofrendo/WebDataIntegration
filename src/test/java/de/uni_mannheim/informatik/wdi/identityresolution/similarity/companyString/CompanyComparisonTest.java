package de.uni_mannheim.informatik.wdi.identityresolution.similarity.companyString;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.wdi.usecase.companies.Company;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyIndustriesComparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.comparators.CompanyStringAttributeComparatorJaccard;
import junit.framework.TestCase;

public class CompanyComparisonTest extends TestCase {

	public void testCalculateCompanySimilarity() {
//		String n1 = "Chevron";
		//String n1 = "Apple  Inc.";
		String n1 = "http://dbpedia.org/resource/The_Coca-Cola_Company";
		//String i1 = "National Commercial Banks;;Investment banking;;Financial Services;;Offices of Bank Holding Companies";
		String i1 = "http://dbpedia.org/resource/Drink";
		String kp1 = "Ahmet Bozer;;http://dbpedia.org/resource/Muhtar_Kent";
		Company c1 = new Company(n1, null); //Forbes
		c1.setName(n1);
		c1.setIndustries(i1);
		c1.setKeyPeople(kp1);
		
		//String n2 = "Apple Inc.";
		String n2 = "The Coca-Cola Company";
//		String n2 = "Chevron Corporation";		
		//String i2 = "Major Banks";
		String i2 = "Beverages";
		String kp2 = "Gary Fayard;;Cynthia Mccague;;Joe Tripodi;;Alexander B Cummings Jr;;E. Neville Isdell;;Muhtar Kent";
		Company c2 = new Company(n2, null); //Freebase
		c2.setName(n2);
		c2.setIndustries(i2);
		c2.setKeyPeople(kp2);
		
		CompanyStringAttributeComparatorJaccard nameCompare = new CompanyStringAttributeComparatorJaccard("name");
		CompanyStringAttributeComparatorJaccard peopleCompare = new CompanyStringAttributeComparatorJaccard("keyPeople");
		System.out.println("Name comparison: " + nameCompare.compare(c1, c2));
		System.out.println("Industries comparison: " +  
							new CompanyIndustriesComparator().compare(c1,  c2));
		System.out.println("KeyPeople comparison: " + peopleCompare.compare(c1, c2));
	}

}

