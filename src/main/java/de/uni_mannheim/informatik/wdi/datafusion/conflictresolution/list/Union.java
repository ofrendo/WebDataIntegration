package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.list;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;

/**
 * Union conflict resolution: Returns the union of all lists of values
 * @author Oliver
 *
 * @param <ValueType>
 * @param <RecordType>
 */
public class Union<ValueType, RecordType extends Matchable & Fusable> extends
		ConflictResolutionFunction<List<ValueType>, RecordType> {

	@Override
	public FusedValue<List<ValueType>, RecordType> resolveConflict(
			Collection<FusableValue<List<ValueType>, RecordType>> values) {

		HashSet<ValueType> union = new HashSet<>();

		for (FusableValue<List<ValueType>, RecordType> value : values) {
			union.addAll(value.getValue());
		}
		List<ValueType> list = new LinkedList<>(union);
		FusedValue<List<ValueType>, RecordType> fused = new FusedValue<>(list);

		for (FusableValue<List<ValueType>, RecordType> value : values) {
			fused.addOriginalRecord(value);
		}

		return fused;
	}

}
