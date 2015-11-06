package de.uni_mannheim.informatik.wdi.identityresolution.similarity.date;

import org.joda.time.DateTime;

import de.uni_mannheim.informatik.wdi.identityresolution.similarity.SimilarityMeasure;

/**
 * Calculates the similarity of the year parts of two dates with respect to a maximum difference.
 * @author Oliver
 *
 */
public class YearSimilarity extends SimilarityMeasure<DateTime> {

	private int maxDifference;
	
	public YearSimilarity(int maxDifference) {
		this.maxDifference = maxDifference;
	}

	@Override
	public double calculate(DateTime first, DateTime second) {
		if(first==null || second==null) {
			return 0.0;
		} else {
			int diff = Math.abs(first.getYear() - second.getYear());
			
			double norm = Math.min((double)diff / (double)maxDifference, 1.0);
			
			return 1 - norm;
		}
	}
	
}
