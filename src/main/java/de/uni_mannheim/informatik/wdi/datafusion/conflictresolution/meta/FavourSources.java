package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.meta;

import java.util.Collection;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;

/**
 * Favour sources conflict resolution: returns the value from the dataset with the highest score
 * @author Oliver
 *
 * @param <ValueType>
 * @param <RecordType>
 */
public class FavourSources<ValueType, RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<ValueType, RecordType> {

	@Override
	public FusedValue<ValueType, RecordType> resolveConflict(
			Collection<FusableValue<ValueType, RecordType>> values) {
		
		FusableValue<ValueType, RecordType> highestScore = null;
		
		for(FusableValue<ValueType, RecordType> value : values) {
			if(highestScore==null || value.getDataSourceScore()>highestScore.getDataSourceScore()) {
				highestScore = value;
			}
		}
		
		return new FusedValue<>(highestScore);
	}

}
