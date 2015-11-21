package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;

/**
 * Vote conflict resolution: returns the most frequent value
 * @author Oliver
 *
 * @param <ValueType>
 * @param <RecordType>
 */
public class Voting<ValueType, RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<ValueType, RecordType> {

	@Override
	public FusedValue<ValueType, RecordType> resolveConflict(
			Collection<FusableValue<ValueType, RecordType>> values) {

		// determine the frequencies of all values
		Map<ValueType, Integer> frequencies = new HashMap<>();
		
		for(FusableValue<ValueType, RecordType> value : values) {
			Integer freq = frequencies.get(value.getValue());
			if(freq==null) {
				freq = 0;
			}
			frequencies.put(value.getValue(), freq+1);
		}
		
		// find the most frequent value
		ValueType mostFrequent = null;
		
		for(ValueType value : frequencies.keySet()) {
			if(mostFrequent==null || frequencies.get(value)>frequencies.get(mostFrequent)) {
				mostFrequent = value;
			}
		}
		
		FusedValue<ValueType, RecordType> result = new FusedValue<>(mostFrequent);
		
		// collect all original records with the most frequent value
		for(FusableValue<ValueType, RecordType> value : values) {
			if(value.getValue().equals(mostFrequent)) {
				result.addOriginalRecord(value);
			}
		}
		
		return result;
	}

}
