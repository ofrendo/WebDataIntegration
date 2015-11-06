package de.uni_mannheim.informatik.wdi.identityresolution.blocking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;

/**
 * Implementation of the sorted neighbourhood blocking strategy.
 * 
 * @author Robert Meusel (robert@dwslab.de)
 * 
 * @param <RecordType>
 */
public class SortedNeighbourhoodBlocker<RecordType extends Matchable> extends
		Blocker<RecordType> {

	private BlockingFunction<RecordType> blockingFunction;
	private int windowSize;

	public SortedNeighbourhoodBlocker(
			BlockingFunction<RecordType> blockingFunction, int windowSize) {
		this.blockingFunction = blockingFunction;
		this.windowSize = windowSize;
	}

	@Override
	public List<Pair<RecordType, RecordType>> generatePairs(DataSet<RecordType> dataset1,
			DataSet<RecordType> dataset2) {
		List<Pair<RecordType, RecordType>> result = new LinkedList<>();

		// add all instancse to one list, and compute the keys
		ArrayList<Pair<String, RecordType>> keyIdentifierList = new ArrayList<Pair<String, RecordType>>();
		for (RecordType record : dataset1.getRecords()) {
			keyIdentifierList.add(new Pair<String, RecordType>(blockingFunction
					.getBlockingKey(record), record));
		}

		for (RecordType record : dataset2.getRecords()) {
			keyIdentifierList.add(new Pair<String, RecordType>(blockingFunction
					.getBlockingKey(record), record));
		}
		// sort the list by the keys
		Comparator<Pair<String, RecordType>> pairComparator = new Comparator<Pair<String, RecordType>>() {

			@Override
			public int compare(Pair<String, RecordType> o1, Pair<String, RecordType> o2) {
				return o1.getFirst().compareTo(o2.getFirst());
			}

		};
		Collections.sort(keyIdentifierList, pairComparator);

		for (int i = 0; i < keyIdentifierList.size() - 1; i++) {
			RecordType r1 = keyIdentifierList.get(i).getSecond();
			int counter = 1;
			int j = i;
			while ((counter < windowSize)
					&& (j < (keyIdentifierList.size() - 1))) {
				RecordType r2 = keyIdentifierList.get(++j).getSecond();
				// check if they belong *not* to the same dataset
				if (!r2.getProvenance().equals(r1.getProvenance())) {
					result.add(new Pair<RecordType, RecordType>(r1, r2));
					counter++;
				}
			}
		}

		calculatePerformance(dataset1, dataset2, result);
		return result;
	}
}
