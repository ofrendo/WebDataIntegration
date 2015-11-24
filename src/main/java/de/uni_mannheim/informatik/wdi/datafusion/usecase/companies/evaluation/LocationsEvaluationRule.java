package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.usecase.companies.Location;

public class LocationsEvaluationRule extends EvaluationRule<FusableCompany> {
	
	//Return true if at least one of them is equal
	@Override
	public boolean isEqual(FusableCompany record1, FusableCompany record2) {
		for (Location l1 : record1.getLocations()) {
			for (Location l2 : record2.getLocations()) {
				if (l1.getName().equals(l2.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
