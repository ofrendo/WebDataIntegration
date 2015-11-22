package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;

public class CountriesEvaluationRule extends EvaluationRule<FusableCompany> {

	@Override
	public boolean isEqual(FusableCompany record1, FusableCompany record2) {
		return record1.getCountries().equals(record2.getCountries());
	}

}
