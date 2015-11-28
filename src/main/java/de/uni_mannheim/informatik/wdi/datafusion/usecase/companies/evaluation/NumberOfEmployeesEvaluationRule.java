package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;

public class NumberOfEmployeesEvaluationRule extends EvaluationRule<FusableCompany> {
	
	@Override
	public boolean isEqual(FusableCompany record1, FusableCompany record2) {
		double pc = (double) Math.abs(record1.getNumberOfEmployees()-record2.getNumberOfEmployees())/
				(double) Math.max(record1.getNumberOfEmployees(), record2.getNumberOfEmployees());
		//System.out.println(record1.getName() + " " + record1.getNumberOfEmployees() + " " + record2.getName() + " "  + record2.getNumberOfEmployees() + " "+ pc);
		return pc < 0.11;
	}
	
}
