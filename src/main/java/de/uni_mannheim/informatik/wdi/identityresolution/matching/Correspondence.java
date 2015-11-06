package de.uni_mannheim.informatik.wdi.identityresolution.matching;

/**
 * Represent a correspondence. Contains two records and their similarity score.
 * @author Oliver
 *
 * @param <RecordType>
 */
public class Correspondence<RecordType> {

	private RecordType firstRecord;
	private RecordType secondRecord;
	private double similarityScore;
	
	/**
	 * returns the first record
	 * @return
	 */
	public RecordType getFirstRecord() {
		return firstRecord;
	}
	
	/**
	 * sets the first record
	 * @param firstRecord
	 */
	public void setFirstRecord(RecordType firstRecord) {
		this.firstRecord = firstRecord;
	}
	
	/**
	 * returns the second record
	 * @return
	 */
	public RecordType getSecondRecord() {
		return secondRecord;
	}
	
	/**
	 * sets the second record
	 * @param secondRecord
	 */
	public void setSecondRecord(RecordType secondRecord) {
		this.secondRecord = secondRecord;
	}
	
	/**
	 * returns the similarity score
	 * @return
	 */
	public double getSimilarityScore() {
		return similarityScore;
	}
	
	/**
	 * sets the similarity score
	 * @param similarityScore
	 */
	public void setsimilarityScore(double similarityScore) {
		this.similarityScore = similarityScore;
	}
	
	public Correspondence(RecordType first, RecordType second, double similarityScore) {
		firstRecord = first;
		secondRecord = second;
		this.similarityScore = similarityScore;
	}
}
