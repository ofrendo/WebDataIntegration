package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution;

import java.util.Collection;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;

/**
 * The abstract super class for all conflict resolution functions
 * @author Oliver
 *
 * @param <ValueType>
 * @param <RecordType>
 */
public abstract class ConflictResolutionFunction<ValueType, RecordType extends Matchable & Fusable> {

	/**
	 * Fuses a collection of fusable values by applying the conflict resolution
	 * @param values
	 * @return
	 */
	public abstract FusedValue<ValueType, RecordType> resolveConflict(Collection<FusableValue<ValueType, RecordType>> values);
	
}
