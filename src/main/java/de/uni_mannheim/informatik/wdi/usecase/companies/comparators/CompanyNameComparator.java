package de.uni_mannheim.informatik.wdi.usecase.companies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.LevenshteinSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;
import de.uni_mannheim.informatik.wdi.usecase.companies.similarity.FuzzyLevenshteinSimilarity;

/**
 * Use Levenshtein to compare names
 * @author D059373
 *
 */
public class CompanyNameComparator extends Comparator<Company> {

	private LevenshteinSimilarity simLevenshtein = new LevenshteinSimilarity();
	
	
	private FuzzyLevenshteinSimilarity simCombination = new FuzzyLevenshteinSimilarity();
	
	@Override
	public double compare(Company record1, Company record2) {
		double result = record1.getName().equals(record2.getName()) ? 1 : 0; //equals
		//double result = simLevenshtein.calculate(record1.getName(), record2.getName());
		//double result = simCombination.calculate(record1.getName(), record2.getName()); //use combination
		return result;
	}

}
