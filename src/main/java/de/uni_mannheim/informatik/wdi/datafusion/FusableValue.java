package de.uni_mannheim.informatik.wdi.datafusion;

import org.joda.time.DateTime;

import de.uni_mannheim.informatik.wdi.Matchable;

/**
 * Wrapper for a value during the data fusion process
 * @author Oliver
 *
 * @param <ValueType>
 * @param <RecordType>
 */
public class FusableValue<ValueType, RecordType extends Matchable & Fusable> {

	private ValueType value;
	private RecordType record;
	private FusableDataSet<RecordType> dataset;
	
	/**
	 * Creates a fusable value from the actual value, the source record and the source dataset
	 * @param value
	 * @param record
	 * @param dataset
	 */
	public FusableValue(ValueType value, RecordType record, FusableDataSet<RecordType> dataset) {
		this.value = value;
		this.record = record;
		this.dataset = dataset;
	}
	
	/**
	 * Returns the value
	 * @return
	 */
	public ValueType getValue() {
		return value;
	}
	
	/**
	 * Returns the record that contains the value
	 * @return
	 */
	public RecordType getRecord() {
		return record;
	}
	
	/**
	 * Returns the dataset that contains the value
	 * @return
	 */
	public FusableDataSet<RecordType> getDataset() {
		return dataset;
	}
	
	/**
	 * Returns the score of the dataset that contains the value
	 * @return
	 */
	public double getDataSourceScore() {
		return dataset.getScore();
	}
	
	/**
	 * Returns the date of the dataset that contains the value
	 * @return
	 */
	public DateTime getDateSourceDate() {
		return dataset.getDate();
	}
	
}
