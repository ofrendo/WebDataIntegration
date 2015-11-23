package de.uni_mannheim.informatik.wdi.identityresolution.similarity.companyString;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import de.uni_mannheim.informatik.wdi.identityresolution.similarity.numeric.PercentageSimilarity;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.LevenshteinSimilarity;
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
		String kp1 = "Durinda Biesman, Senior Vice President, Global Service Delivery;;Gregory Ayers, Chief Financial Officer;;Mariann McDonagh, Chief Marketing Officer;;Paul Jarman, Chief Executive Officer;;Scott Welch, Chief Operating Officer;;Sunny Gosain, Executive Vice President &amp; Chief Product Officer;;Theodore Stern, Executive Chairman";
		Company c1 = new Company(n1, null); //Forbes
		c1.setName(n1);
		c1.setIndustries(i1);
		c1.setKeyPeople(kp1);
		
		//String n2 = "Apple Inc.";
		String n2 = "The Coca-Cola Company";
//		String n2 = "Chevron Corporation";		
		//String i2 = "Major Banks";
		String i2 = "Beverages";
		String kp2 = "Mark Miller;;Tim Jenkins;;Mari Tangredi;;Brian Slitt;;Arash Saffarnia;;Kirsten McMullen;;Ed Koenig;;Michelle Estabrook;;Chuck Moxley;;Arthur Coleman;;Julie Shumaker;;Prakash Chandra;;Yonggang Xu;;Prashant Chaudhary;;Richard Qiu;;Zaw Thet;;Ray Colwell;;Ted C. Burns;;Patricia Clark;;Rajiv Parikh;;Markus A. Nordvik;;Dennis Yang;;Markus A. Nordvik;;Rajiv Parikh;;Chris Record;;Dave Matthews;;JB Sugar";
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
		
		String s1 = "Oil and Gas Field Services";
		String s2 = "Oil and Gas Field Services, NEC";
		LevenshteinSimilarity l = new LevenshteinSimilarity();
		System.out.println("Levenshtein sim " + s1 + " AND " + s2 + "=" + l.calculate(s1, s2));
		
		Long l1 = new Long("173800000000");
		Long l2 = new Long("234000000000");
		PercentageSimilarity sim = new PercentageSimilarity(1);
		System.out.println("Percentage sim: " + sim.calculate((double) l1, (double) l2));
		
		//Date tests
		DateTime d1 = DateTime.parse("1999-01-01");
		DateTime d2 = DateTime.parse("2015");
		System.out.println(d1);
		System.out.println(d2);
		System.out.println(d1.toString("dd-MM-yyyy"));
		
	}

}

