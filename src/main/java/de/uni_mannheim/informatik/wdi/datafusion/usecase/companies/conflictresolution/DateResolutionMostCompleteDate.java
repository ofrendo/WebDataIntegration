package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution;

import java.util.Collection;

import org.joda.time.DateTime;

import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;

public class DateResolutionMostCompleteDate extends ConflictResolutionFunction<DateTime, FusableCompany> {
	
	@Override
	public FusedValue<DateTime, FusableCompany> resolveConflict(Collection<FusableValue<DateTime, FusableCompany>> values) {
		if(values.size()==0) {
			return new FusedValue<>(null);
		} else {
			// The conflict resolution strategy for dates is
			// MostComplete Date (dd-mm-yyyy), not just yyyy
			// If both incomplete: MostComplete record
			FusableValue<DateTime, FusableCompany> result = null;
			
			for (FusableValue<DateTime, FusableCompany> value : values) {
				if (result == null) {
					result = value;
				}

				
				if (value.getValue().getDayOfMonth() != 1 || value.getValue().getMonthOfYear() != 1) {
					// This is probably a more complete Date
					result = value;
				}
				
			}
			
			
			return new FusedValue<>(result);
		}
	}
}
