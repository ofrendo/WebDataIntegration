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
		String n1 = "NIKE";
		//String i1 = "National Commercial Banks;;Investment banking;;Financial Services;;Offices of Bank Holding Companies";
		String i1 = "Petroleum Refineries;;Mining;;Crude Petroleum and Natural Gas Extraction;;Petroleum industry";
		String kp1 = "Robert C. Weber;;Jon C. Iwata;;Ginni Rometty;;Mark Loughridge;;Linda S. Sanford;;Timothy S Shaughnessy;;Michael E. Daniels;;J. Randall MacDonald;;James J Kavanaugh;;Samuel J. Palmisano;;Ginni Rometty;;Thomas J. Watson;;Thomas Watson Jr.;;T. Vincent Learson;;Frank T. Cary;;John R. Opel;;John F. Akers;;Lou Gerstner";
		Company c1 = new Company(n1, null); //Forbes
		c1.setName(n1);
		c1.setIndustries(i1);
		c1.setKeyPeople(kp1);
		
		//String n2 = "Apple Inc.";
		String n2 = "Nike";
//		String n2 = "Chevron Corporation";		
		//String i2 = "Major Banks";
		String i2 = "Oil & Gas Operations";
		String kp2 = "Ginni Rometty";
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

