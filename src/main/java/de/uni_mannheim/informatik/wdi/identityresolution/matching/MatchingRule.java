package de.uni_mannheim.informatik.wdi.identityresolution.matching;

import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecord;

/**
 * Super class for all matching rules.
 * @author Oliver
 *
 * @param <RecordType>
 */
public abstract class MatchingRule<RecordType> extends Comparator<RecordType> {

	private double finalThreshold;
	
	public double getFinalThreshold() {
		return finalThreshold;
	}
	public void setFinalThreshold(double finalThreshold) {
		this.finalThreshold = finalThreshold;
	}
	
	public MatchingRule(double finalThreshold) {
		this.finalThreshold = finalThreshold;
	}
	
	public Correspondence<RecordType> apply(RecordType record1, RecordType record2) {
		double similarity = compare(record1, record2);
		
		if(similarity >= getFinalThreshold()) {
			return new Correspondence<>(record1, record2, similarity);
		} else {
			return null;
		} 
	}
	
	public abstract DefaultRecord generateFeatures(RecordType record1, RecordType record2);
}
