package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

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
	
	private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
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
		case "countries":
			value1 = company1.getCountries();
			value2 = company2.getCountries();
			//TODO: Transform US, USA, U.S., United States, etc
			String[] shouldReplaced = {"US","USA","U.S","U.S.A","United States","America","Contiguous United States"};
			String[] arr1 = value1.split(";;");
			String[] arr2 = value2.split(";;");
			String result1 = "";
			String result2 = "";
			//value1
			for(int i = 0;i < arr1.length;i++){
				for(String s : shouldReplaced){
					if(arr1[i].equals(s)){
						arr1[i] = "United States of America";
					}
				}
				result1 += arr1[i];
				if(i != arr1.length-1)
					result1 += ";;";
			}
			//value2
			for(int i = 0;i < arr2.length;i++){
				for(String s : shouldReplaced){
					if(arr2[i].equals(s)){
						arr2[i] = "United States of America";
					}
				}
				result2 += arr2[i];
				if(i != arr2.length-1)
					result2 += ";;";
			}
			value1 = result1;
			value2 = result2;
			break;
		case "industries":
			value1 = company1.getIndustries();
			value2 = company2.getIndustries();
			break;
		case "headquarters":
			value1 = company1.getHeadquarters();
			value2 = company2.getHeadquarters();
			break;
		case "keyPeople":
			value1 = company1.getKeyPeople();
			value2 = company2.getKeyPeople();
			break;
		}
		if (value1 == null || value2 == null) {
			return 0;
		}
		
		// preprocessing
		value1 = value1.toLowerCase().replaceAll(";;", " ");
		value2 = value2.toLowerCase().replaceAll(";;", " ");
		
		// calculate similarity
		double similarity = sim.calculate(value1, value2);
		
		// postprocessing
		/*if(similarity<=0.3) {
			similarity = 0;
		}*/
		//similarity *= similarity;
		
		return similarity;
	}

}
