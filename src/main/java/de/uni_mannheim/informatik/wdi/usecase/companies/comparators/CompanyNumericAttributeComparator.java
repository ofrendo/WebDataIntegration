package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.numeric.PercentageSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

/**
 * Compare two companies with each other, based on a given numeric attribute
 * @author Oliver
 *
 */
public class CompanyNumericAttributeComparator extends Comparator<Company> {

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
	
	private String comparisonAttribute;
	//private double max_percentage;
	private PercentageSimilarity sim;
	
	public CompanyNumericAttributeComparator(String comparisonAttribute, double max_percentage) {
		this.comparisonAttribute = comparisonAttribute;
		//this.max_percentage = max_percentage;
		this.sim = new PercentageSimilarity(max_percentage);
	}

	@Override
	public double compare(Company company1, Company company2) {
		long value1 = -1;
		long value2 = -1;
		
		switch (comparisonAttribute) {
		case "revenue":
			value1 = company1.getRevenue();
			value2 = company2.getRevenue();
			break;
		case "numberOfEmployees":
			value1 = company1.getRevenue();
			value2 = company2.getRevenue();
			break;
		case "profit":
			value1 = company1.getProfit();
			value2 = company2.getProfit();
			break;
		}
		if (value1 == -1 || value2 == -1) {
			return 0;
		}
		
		double similarity  = sim.calculate((double) value1, (double) value2);
		
		
		return similarity;
	}
	
	
	
}
