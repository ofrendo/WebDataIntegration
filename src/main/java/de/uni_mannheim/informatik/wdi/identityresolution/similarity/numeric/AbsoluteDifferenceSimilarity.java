package de.uni_mannheim.informatik.wdi.identityresolution.similarity.numeric;

import de.uni_mannheim.informatik.wdi.identityresolution.similarity.SimilarityMeasure;

/**
 * Calculates a numeric similarity based on the absolute difference between the two numbers
 * @author Oliver
 *
 */
public class AbsoluteDifferenceSimilarity extends SimilarityMeasure<Double> {

	private double diff_max = 0.0;
	
	/**
	 * Creates a new instance of the similarity measure
	 * @param max_absolute_difference the max absolute difference between two values. Higher differences lead to a similarity value of 0.0.
	 */
	public AbsoluteDifferenceSimilarity(double max_absolute_difference) {
		this.diff_max = max_absolute_difference;
	}
	
	@Override
	public double calculate(Double first, Double second) {
		if(first==null || second==null) {
			return 0.0;
		} else {			
			double diff = Math.abs(first - second);
			
			if(diff < diff_max) {
				return 1 - diff/diff_max;
			} else {
				return 0.0;
			}
		}
	}

	
	
}
