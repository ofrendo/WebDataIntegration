package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.string;

import java.util.Collection;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;

/**
 * Longest string conflict resolution: Returns the longest string value
 * @author Oliver
 *
 * @param <RecordType>
 */
public class LongestString<RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<String, RecordType> {

	@Override
	public FusedValue<String, RecordType> resolveConflict(Collection<FusableValue<String, RecordType>> values) {
		FusableValue<String, RecordType> longest = null;
		
		for(FusableValue<String, RecordType> value : values) {
			if(longest == null || value.getValue().length()>longest.getValue().length()) {
				longest = value;
			}
		}
		
		return new FusedValue<>(longest);
	}

}
