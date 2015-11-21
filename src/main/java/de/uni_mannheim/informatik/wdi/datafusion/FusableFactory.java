package de.uni_mannheim.informatik.wdi.datafusion;

import de.uni_mannheim.informatik.wdi.Matchable;

/**
 * Specifies the factory method for creating a fused record
 * @author Oliver
 *
 * @param <RecordType>
 */
public interface FusableFactory<RecordType extends Matchable & Fusable> {

	/**
	 * Creates a record that will receive all the values from the data fusion for the given record group 
	 * @param cluster
	 * @return
	 */
	abstract RecordType createInstanceForFusion(RecordGroup<RecordType> cluster);
	
}
