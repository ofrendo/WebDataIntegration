package de.uni_mannheim.informatik.wdi.identityresolution.similarity.numeric;

import de.uni_mannheim.informatik.wdi.identityresolution.similarity.SimilarityMeasure;

/**
 * Calculates a numeric similarity based on the percental difference between the two numbers
 * @author Oliver
 *
 */
public class PercentageSimilarity extends SimilarityMeasure<Double> {

	private double max_percentage = 0.0;
	
	/**
	 * Creates a new instance of the similarity measure
	 * @param max_percental_difference the max percental difference between two values. Higher differences lead to a similarity value of 0.0.
	 */
	public PercentageSimilarity(double max_percental_difference) {
		this.max_percentage = max_percental_difference;
	}
	
	@Override
	public double calculate(Double first, Double second) {
		if(first==null || second==null) {
			return 0.0;
		} else {
			double pc = Math.abs(first-second)/Math.max(first, second);
			
			if(pc < max_percentage) {
				return 1 - pc/max_percentage;
			} else {
				return 0.0;
			}
		}
	}

	
	
}
