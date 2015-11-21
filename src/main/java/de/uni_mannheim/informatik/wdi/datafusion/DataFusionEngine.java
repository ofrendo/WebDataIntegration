package de.uni_mannheim.informatik.wdi.datafusion;

import java.util.HashMap;
import java.util.Map;

import de.uni_mannheim.informatik.wdi.Matchable;

/**
 * Executes the data fusion process
 * @author Oliver
 *
 * @param <RecordType>
 */
public class DataFusionEngine<RecordType extends Matchable & Fusable> {

	private DataFusionStrategy<RecordType> strategy;
	
	/**
	 * Creates a new instance that uses the specified data fusion strategy.
	 * @param strategy
	 */
	public DataFusionEngine(DataFusionStrategy<RecordType> strategy) {
		this.strategy = strategy;
	}
	
	/**
	 * Runs the data fusion process on the provided set of correspondences
	 * @param correspondences
	 * @return
	 */
	public FusableDataSet<RecordType> run(CorrespondenceSet<RecordType> correspondences) {
		FusableDataSet<RecordType> fusedDataSet = new FusableDataSet<>();
		
		for(RecordGroup<RecordType> clu : correspondences.getRecordGroups()) {
			RecordType fusedRecord = strategy.apply(clu);
			fusedDataSet.addRecord(fusedRecord);
			
			for(RecordType record : clu.getRecords()) {
				fusedDataSet.addOriginalId(fusedRecord, record.getIdentifier());
			}
		}
		
		return fusedDataSet;
	}
	
	/**
	 * Calculates the consistencies of the attributes of the records in the given correspondence set according to the data fusion strategy
	 * @param correspondences
	 * @return
	 */
	public Map<String, Double> getAttributeConsistencies(CorrespondenceSet<RecordType> correspondences) {
		Map<String, Integer> nonConflictingValues = new HashMap<>();
		
		for(RecordGroup<RecordType> clu : correspondences.getRecordGroups()) {
		
			Map<String, Integer> values = strategy.getNumberOfNonConflictingValues(clu);
			
			for(String att : values.keySet()) {
				Integer cnt = nonConflictingValues.get(att);
				if(cnt==null) {
					cnt = 0;
				}
				nonConflictingValues.put(att, cnt+values.get(att));
			}

		}
		
		Map<String, Double> result = new HashMap<>();
		for(String att : nonConflictingValues.keySet()) {
			double consistency = (double)nonConflictingValues.get(att) / (double)correspondences.getRecordGroups().size();
			result.put(att, consistency);
		}
		
		return result;
	}
	
	/**
	 * Calculates the consistencies of the attributes of the records in the given correspondence set according to the data fusion strategy and prints the results to the console
	 * @param correspondences
	 */
	public void printClusterConsistencyReport(CorrespondenceSet<RecordType> correspondences) {
		System.out.println("Attribute Consistencies:");
		Map<String, Double> consistencies = getAttributeConsistencies(correspondences);
		for(String att : consistencies.keySet()) {
			System.out.println(String.format("\t%s: %.2f", att, consistencies.get(att)));
		}
	}
}
