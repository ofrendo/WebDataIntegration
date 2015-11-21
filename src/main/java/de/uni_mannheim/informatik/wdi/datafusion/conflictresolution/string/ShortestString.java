package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.string;

import java.util.Collection;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;

/**
 * Shortest string conflict resolution: Returns the shortest string value
 * @author Oliver
 *
 * @param <RecordType>
 */
public class ShortestString<RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<String, RecordType> {

	@Override
	public FusedValue<String, RecordType> resolveConflict(Collection<FusableValue<String, RecordType>> values) {
		FusableValue<String, RecordType> shortest = null;
		
		for(FusableValue<String, RecordType> value : values) {
			if(shortest == null || value.getValue().length()<shortest.getValue().length()) {
				shortest = value;
			}
		}
		
		return new FusedValue<>(shortest);
	}

}
