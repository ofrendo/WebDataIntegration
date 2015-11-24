package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution;

import java.util.Collection;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;

public class SingleSourceResolution<ValueType, RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<ValueType, RecordType> {

	@Override
	public FusedValue<ValueType, RecordType> resolveConflict(Collection<FusableValue<ValueType, RecordType>> values) {
		if(values.size()==0) {
			return new FusedValue<>(null);
		} else {
		
			FusableValue<ValueType, RecordType> fusedResult = null;
			for(FusableValue<ValueType, RecordType> value : values) {
				if (value != null)
					fusedResult = value;
			}
			
			FusedValue<ValueType, RecordType> result = new FusedValue<>(fusedResult);
			result.addOriginalRecord(fusedResult);
			
			return result;
		}
	}

}
