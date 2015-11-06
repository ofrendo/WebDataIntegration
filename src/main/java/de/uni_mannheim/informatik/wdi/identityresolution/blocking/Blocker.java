package de.uni_mannheim.informatik.wdi.identityresolution.blocking;

import java.util.List;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;

/**
 * The super class for all blocking strategies
 * @author Oliver
 *
 * @param <RecordType>
 */
public abstract class Blocker<RecordType extends Matchable> {

	private double reductionRatio = 1.0;
	
	/**
	 * Returns the reduction ratio of the last blocking operation. Only available after calculatePerformance(...) has been called.
	 * @return the reduction ratio
	 */
	public double getReductionRatio() {
		return reductionRatio;
	}
	
	/**
	 * Generates the pairs of records that should be compared according to this blocking strategy.
	 * @param dataset1 the first data set
	 * @param dataset2 the second data set
	 * @return the list of pairs that resulted from the blocking
	 */
	public abstract List<Pair<RecordType, RecordType>> generatePairs(
			DataSet<RecordType> dataset1, DataSet<RecordType> dataset2);
	
	/**
	 * Calculates the reduction ratio. Must be called by all sub classes in generatePairs(...).
	 * @param dataset1 the first data set
	 * @param dataset2 the second data set
	 * @param blockedPairs the list of pairs that resulted from the blocking
	 */
	protected void calculatePerformance(DataSet<RecordType> dataset1,
			DataSet<RecordType> dataset2, List<Pair<RecordType, RecordType>> blockedPairs) {
		long maxPairs = (long)dataset1.getSize() * (long)dataset2.getSize();
		
		reductionRatio = (double)maxPairs / (double)blockedPairs.size();
	}
}
