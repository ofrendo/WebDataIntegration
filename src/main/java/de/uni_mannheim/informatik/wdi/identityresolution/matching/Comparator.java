package de.uni_mannheim.informatik.wdi.identityresolution.matching;

/**
 * Super class for all record comparators.
 * 
 * @author Oliver
 *
 * @param <RecordType>
 */
public abstract class Comparator<RecordType> {

	/**
	 * Compares two records and returns a similarity value
	 * 
	 * @param record1
	 *            the first record
	 * @param record2
	 *            the second record
	 * @return the similarity of the records
	 */
	public abstract double compare(RecordType record1, RecordType record2);

}
