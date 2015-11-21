package de.uni_mannheim.informatik.wdi.datafusion;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import de.uni_mannheim.informatik.wdi.Matchable;

/**
 * Represents a fused value in the data fusion process
 * @author Oliver
 *
 * @param <ValueType>
 * @param <RecordType>
 */
public class FusedValue<ValueType, RecordType extends Matchable & Fusable> {

	private ValueType value;
	private Map<RecordType, FusableDataSet<RecordType>> originalRecords = new HashMap<>();

	/**
	 * Creates a fused value without any provenance information
	 * @param value
	 */
	public FusedValue(ValueType value) {
		this.value = value;
	}

	/**
	 * Creates a fused value with the original record and dataset as provenance information 
	 * @param value
	 */
	public FusedValue(FusableValue<ValueType, RecordType> value) {
		if (value != null) {
			this.value = value.getValue();
			addOriginalRecord(value.getRecord(), value.getDataset());
		}
	}

	/**
	 * Returns the fused value
	 * @return
	 */
	public ValueType getValue() {
		return value;
	}

	/**
	 * Returns a map record -> dataset containing all original records that are represented by this fused value
	 * @return
	 */
	public Map<RecordType, FusableDataSet<RecordType>> getOriginalRecords() {
		return originalRecords;
	}
	
	/**
	 * Returns the IDs of all original records that are represented by this fused value
	 * @return
	 */
	public Collection<String> getOriginalIds() {
		Collection<String> result = new LinkedList<>();
		for(RecordType record : getOriginalRecords().keySet()) {
			result.add(record.getIdentifier());
		}
		return result;
	}

	/**
	 * Adds an original record as provenance information
	 * @param record
	 * @param dataset
	 */
	public void addOriginalRecord(RecordType record,
			FusableDataSet<RecordType> dataset) {
		originalRecords.put(record, dataset);
	}

	/**
	 * Adds an original record as provenance information
	 * @param value
	 */
	public void addOriginalRecord(FusableValue<ValueType, RecordType> value) {
		originalRecords.put(value.getRecord(), value.getDataset());
	}
}
