package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.LevenshteinSimilarity;

public class KeyPeopleEvaluationRule extends EvaluationRule<FusableCompany> {
	
private LevenshteinSimilarity sim = new LevenshteinSimilarity();
	
	/**
	 * Is equal if at least one key person is the same
	 */
	@Override
	public boolean isEqual(FusableCompany record1, FusableCompany record2) {
		//if (record1.getKeyPeople() == null || record2.getKeyPeople() == null) 
		//	return false;
		
		String[] k1s = record1.getKeyPeople().split(";;");
		String[] k2s = record2.getKeyPeople().split(";;");
		for (String k1 : k1s) {
			for (String k2 : k2s) {
				if (sim.calculate(k1, k2) > 0.8) {
					return true;
				}
			}
		}
		return false;
	}
	
}
