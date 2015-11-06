package de.uni_mannheim.informatik.wdi.identityresolution.blocking;

import java.util.LinkedList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;

/**
 * Implementation of a cross-product blocking strategy (i.e. no blocking).
 * @author Oliver
 *
 * @param <RecordType>
 */
public class CrossProductBlocker<RecordType extends Matchable> extends
		Blocker<RecordType> {

	@Override
	public List<Pair<RecordType, RecordType>> generatePairs(DataSet<RecordType> dataset1,
			DataSet<RecordType> dataset2) {
		List<Pair<RecordType, RecordType>> result = new LinkedList<>();

		// generate full cross product of both data sets
		for (RecordType m1 : dataset1.getRecords()) {
			for (RecordType m2 : dataset2.getRecords()) {
				result.add(new Pair<RecordType, RecordType>(m1, m2));
			}
		}

		calculatePerformance(dataset1, dataset2, result);
		
		return result;
	}

}
