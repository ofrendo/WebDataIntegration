package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;
import de.uni_mannheim.informatik.wdi.usecase.companies.similarity.FuzzyJaccardSimilarity;

public class CompanyKeyPeopleComparator extends Comparator<Company> {
	
	private FuzzyJaccardSimilarity sim = new FuzzyJaccardSimilarity();
	private TokenizingJaccardSimilarity simJaccard = new TokenizingJaccardSimilarity();
	
	@Override
	public double compare(Company c1, Company c2) {
		if (c1.getKeyPeople() == null || c2.getKeyPeople() == null)
			return 0;
		
		// Jaccard
		//String k1s = c1.getKeyPeople().replaceAll(";;", " ");
		//String k2s = c2.getKeyPeople().replaceAll(";;", " ");
		//return simJaccard.calculate(k1s, k2s); 
		
		// Own mix of jaccard and levenshtein
		double similarity = sim.calculate(c1.getKeyPeople(), c2.getKeyPeople());
		return similarity;
	}
	
}
