package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.meta;

import java.util.Collection;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;

/**
 * Most recent conflict resolution: Returns the most recent value according to the dataset metadata
 * @author Oliver
 *
 * @param <ValueType>
 * @param <RecordType>
 */
public class MostRecent<ValueType, RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<ValueType, RecordType> {

	@Override
	public FusedValue<ValueType, RecordType> resolveConflict(
			Collection<FusableValue<ValueType, RecordType>> values) {
		
		FusableValue<ValueType, RecordType> mostRecent = null;
		
		for(FusableValue<ValueType, RecordType> value : values) {
			if(mostRecent==null || value.getDateSourceDate().isAfter(mostRecent.getDateSourceDate())) {
				mostRecent = value;
			}
		}
		
		return new FusedValue<>(mostRecent);
	}

}
