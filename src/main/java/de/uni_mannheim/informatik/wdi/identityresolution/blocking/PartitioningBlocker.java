package de.uni_mannheim.informatik.wdi.identityresolution.blocking;

import java.util.LinkedList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;

/**
 * Implementation of a simple blocking strategy based on blocking keys.
 * @author Oliver
 *
 * @param <RecordType>
 */
public class PartitioningBlocker<RecordType extends Matchable> extends Blocker<RecordType> {

	private BlockingFunction<RecordType> blockingFunction;
	
	public PartitioningBlocker(BlockingFunction<RecordType> blockingFunction) {
		this.blockingFunction = blockingFunction;
	}

	@Override
	public List<Pair<RecordType, RecordType>> generatePairs(DataSet<RecordType> dataset1,
			DataSet<RecordType> dataset2) {
		List<Pair<RecordType, RecordType>> result = new LinkedList<>();
		
		for(RecordType r1 : dataset1.getRecords()) {
			String key1 = blockingFunction.getBlockingKey(r1);
			for(RecordType r2 : dataset2.getRecords()) {
				String key2 = blockingFunction.getBlockingKey(r2);
				if(r1!=r2 && key1.equals(key2)) {
					result.add(new Pair<RecordType, RecordType>(r1, r2));
				}
			}
		}
		
		calculatePerformance(dataset1, dataset2, result);
		
		return result;
	}
	
}
