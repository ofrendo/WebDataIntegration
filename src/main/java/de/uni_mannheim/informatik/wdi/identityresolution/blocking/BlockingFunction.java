package de.uni_mannheim.informatik.wdi.identityresolution.blocking;

import de.uni_mannheim.informatik.wdi.Matchable;

/**
 * Super class for all blocking functions. A blocking function returns the blocking key for a given record.
 * @author Oliver
 *
 * @param <RecordType>
 */
public abstract class BlockingFunction<RecordType extends Matchable> {

	/**
	 * Returns the blocking key for the given record
	 * @param entity
	 * @return
	 */
	public abstract String getBlockingKey(RecordType instance);
	
}
