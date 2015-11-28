package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.LevenshteinSimilarity;

public class IndustriesEvaluationRule extends EvaluationRule<FusableCompany> {

	private LevenshteinSimilarity sim = new LevenshteinSimilarity();
	
	/**
	 * Is equal if at least one industry has levenshtein of over 0.8
	 */
	@Override
	public boolean isEqual(FusableCompany record1, FusableCompany record2) {
		String[] i1s = record1.getIndustries().split(";;");
		String[] i2s = record2.getIndustries().split(";;");
		for (String i1 : i1s) {
			for (String i2 : i2s) {
				if (sim.calculate(i1, i2) > 0.8) {
					return true;
				}
			}
		}
		return false;
	}
	
}
