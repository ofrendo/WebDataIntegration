package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.list;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;

/**
 * Intersection conflict resolution: returns the intersection of all lists of values
 * @author Oliver
 *
 * @param <ValueType>
 * @param <RecordType>
 */
public class Intersection<ValueType, RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<List<ValueType>, RecordType> {

	@Override
	public FusedValue<List<ValueType>, RecordType> resolveConflict(
			Collection<FusableValue<List<ValueType>, RecordType>> values) {
		// determine the intersection of values
		Set<ValueType> allValues = null;
		
		for(FusableValue<List<ValueType>, RecordType> value : values) {
			
			if(allValues==null) {
				allValues = new HashSet<>();
				allValues.addAll(value.getValue());
			} else {
				allValues.retainAll(value.getValue());
			}
			
		}
		List<ValueType> intersection = new LinkedList<>(allValues);
		FusedValue<List<ValueType>, RecordType> fused = new FusedValue<>(intersection);
		
		// list the original records that contributed to this intersection
		for(FusableValue<List<ValueType>, RecordType> value : values) {
			
			for(ValueType v : value.getValue()) {
				if(allValues.contains(v)) {
					fused.addOriginalRecord(value);
					break;
				}
			}
			
		}
		
		return fused;
	}


}
