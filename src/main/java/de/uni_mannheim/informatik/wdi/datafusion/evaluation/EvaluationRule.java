package de.uni_mannheim.informatik.wdi.datafusion.evaluation;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;

/**
 * Abstract super class for all evaluation rules
 * @author Oliver
 *
 * @param <RecordType>
 */
public abstract class EvaluationRule<RecordType extends Matchable & Fusable> {

	/**
	 * Returns whether the values of the given records are equal according to this evaluation rule
	 * @param record1
	 * @param record2
	 * @return
	 */
	public abstract boolean isEqual(RecordType record1, RecordType record2);
	
}
