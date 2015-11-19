package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;
import de.uni_mannheim.informatik.wdi.usecase.companies.similarity.FuzzyLevenshteinSimilarity;

/**
 * Compare two companies with each other, based on a given String attribute
 * @author Oliver
 *
 */
public class CompanyStringAttributeComparatorJaccard extends Comparator<Company> {

/* example entry
	<company>
		<name>1-800-Flowers</name>
		<countries>United States of America</countries>
		<industries>Retail;;Information technology;;Gift, Novelty, and Souvenir Stores</industries>
		<revenue>912600000</revenue>
		<numberOfEmployees>3700</numberOfEmployees>
		<dateFounded>1976</dateFounded>
		<headquarters>Town of North Hempstead</headquarters>
		<profit>-98420000</profit>
		<keyPeople>James F. Mccann;;Christopher G. Mccann;;William E Shea;;Stephen J Bozzo;;David Taiclet;;Gerard M Gallagher</keyPeople>
	</company>
*/
	
	private FuzzyLevenshteinSimilarity sim = new FuzzyLevenshteinSimilarity();
	private String comparisonAttribute;

	public CompanyStringAttributeComparatorJaccard(String comparisonAttribute) {
		this.comparisonAttribute = comparisonAttribute;
	}
	
	@Override
	public double compare(Company company1, Company company2) {
		String value1 = null;
		String value2 = null;
		switch (comparisonAttribute) {
		case "name":
			value1 = company1.getName();
			value2 = company2.getName();
			break;
		case "keyPeople":
			value1 = company1.getKeyPeople();
			value2 = company2.getKeyPeople();
			break;
		default: 
			throw new RuntimeException("Used Comparator for wrong attributes");
		}
		if (value1 == null || value2 == null) {
			return 0;
		}
		
		// preprocessing
		

		// calculate similarity
//		double similarity = sim.calculate(value1, value2);
//		System.out.println("arr1.length: "+ arr1.length);
//		System.out.println("arr2.length: "+ arr2.length);
//		System.out.println("Intersection: "+ intersectionNum);
		
		// postprocessing
		/*if(similarity<=0.3) {
			similarity = 0;
		}*/
		//similarity *= similarity;
		double similarity = sim.calculate(value1, value2);
		return similarity;
	}

	
}
