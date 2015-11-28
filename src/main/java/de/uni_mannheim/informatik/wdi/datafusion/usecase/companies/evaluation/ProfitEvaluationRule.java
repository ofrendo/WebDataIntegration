package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;

public class ProfitEvaluationRule extends EvaluationRule<FusableCompany> {

	@Override
	public boolean isEqual(FusableCompany record1, FusableCompany record2) {
		double pc = (double) Math.abs(record1.getProfit()-record2.getProfit())/
				(double) Math.max(record1.getProfit(), record2.getProfit());
		return pc < 0.9;
	}
	
}
