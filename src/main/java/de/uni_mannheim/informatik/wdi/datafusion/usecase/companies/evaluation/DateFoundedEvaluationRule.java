package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;

public class DateFoundedEvaluationRule extends EvaluationRule<FusableCompany> {

	@Override
	public boolean isEqual(FusableCompany record1, FusableCompany record2) {
		//return Math.abs(record1.getDateFounded().getYear() - record2.getDateFounded().getYear()) <= 10;
		return Math.abs(record1.getDateFounded().getYear() - record2.getDateFounded().getYear()) <= 2;
	}

}
