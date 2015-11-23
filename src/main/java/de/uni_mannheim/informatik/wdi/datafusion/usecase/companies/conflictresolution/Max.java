package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution;

import java.util.Collection;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;

public class Max<RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<Double, RecordType>{

	@Override
	public FusedValue<Double, RecordType> resolveConflict(Collection<FusableValue<Double, RecordType>> values) {
		if(values.size()==0) {
			return new FusedValue<>((Double)null);
		} else {
		
			double max = 0.0;
			
			FusableValue<Double,RecordType> maxValue = null;
			for(FusableValue<Double, RecordType> value : values) {
				if (max < value.getValue())  {
					max = value.getValue();
					maxValue = value;
				}
					
				
			}
			
			FusedValue<Double, RecordType> result = new FusedValue<>(max);
			result.addOriginalRecord(maxValue);
			
			return result;
		}
	}

}
