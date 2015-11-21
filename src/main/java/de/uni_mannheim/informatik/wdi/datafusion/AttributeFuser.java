package de.uni_mannheim.informatik.wdi.datafusion;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;

/**
 * Abstract super class for all fusers used by a fusion strategy
 * @author Oliver
 *
 * @param <RecordType>
 */
public abstract class AttributeFuser<RecordType extends Matchable & Fusable> {

	/**
	 * fuses the group of records and assigns values to the fused Record
	 * @param group the group of values to be fused (input)
	 * @param fusedRecord the fused record (output)
	 */
	public abstract void fuse(RecordGroup<RecordType> group, RecordType fusedRecord);	
	
	/**
	 * Determines whether the record has a value for the attribute that is used by this fuser. Required for the collection of fusable values.
	 * @param record
	 * @return
	 */
	public abstract boolean hasValue(RecordType record);
	
	/**
	 * Determines if the given group of records has conflicting values
	 * @param group
	 * @param rule
	 * @return
	 */
	public abstract boolean hasConflictingValues(RecordGroup<RecordType> group, EvaluationRule<RecordType> rule);
}
