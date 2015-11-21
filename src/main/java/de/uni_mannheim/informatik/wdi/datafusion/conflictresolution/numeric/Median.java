package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.numeric;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;

/**
 * Median conflict resolution: Returns the median of all values
 * @author Oliver
 *
 * @param <RecordType>
 */
public class Median<RecordType extends Matchable & Fusable> extends
		ConflictResolutionFunction<Double, RecordType> {

	@Override
	public FusedValue<Double, RecordType> resolveConflict(
			Collection<FusableValue<Double, RecordType>> values) {

		List<Double> list = new LinkedList<>();

		for (FusableValue<Double, RecordType> value : values) {
			list.add(value.getValue());
		}

		Collections.sort(list);

		boolean isEven = list.size() % 2 == 0;
		if (list.size() == 0) {
			return new FusedValue<>((Double) null);
		} else if (isEven) {
			double middle = ((double) list.size() + 1.0) / 2.0;
			double median1 = list.get((int) Math.floor(middle) - 1);
			double median2 = list.get((int) Math.ceil(middle) - 1);

			return new FusedValue<>((median1 + median2) / 2.0);
		} else {
			int middle = list.size() / 2;

			return new FusedValue<>(list.get(middle - 1));
		}
	}
}
