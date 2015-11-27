package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;

import com.wcohen.ss.Jaccard;
import com.wcohen.ss.api.Token;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;
import de.uni_mannheim.informatik.wdi.usecase.companies.similarity.FuzzyJaccardSimilarity;

/**
 * Choose the highest similarity between two arrays of industries
 * @author Oliver
 *
 */
public class CompanyIndustriesComparator extends Comparator<Company> {
	
	private FuzzyJaccardSimilarity sim = new FuzzyJaccardSimilarity();
	private TokenizingJaccardSimilarity simJaccard = new TokenizingJaccardSimilarity();
	
	@Override
	public double compare(Company c1, Company c2) {
		if (c1.getIndustries() == null || c2.getIndustries() == null)
			return 0;
		
		// Jaccard
		String i1s = c1.getIndustries().replaceAll(";;", " ");
		String i2s = c2.getIndustries().replaceAll(";;", " ");
		return simJaccard.calculate(i1s, i2s); 
		
		// Own mix of jaccard and levenshtein
		//double similarity = sim.calculate(c1.getIndustries(), c2.getIndustries());
		//return similarity;
	}
	
}
