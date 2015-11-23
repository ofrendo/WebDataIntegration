package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;

public class NumberOfEmployeesEvaluationRule extends EvaluationRule<FusableCompany> {
	
	@Override
	public boolean isEqual(FusableCompany record1, FusableCompany record2) {
		double pc = Math.abs(record1.getNumberOfEmployees()-record2.getNumberOfEmployees())/
				Math.max(record1.getNumberOfEmployees(), record2.getNumberOfEmployees());
		return pc < 0.5;
	}
	
}
