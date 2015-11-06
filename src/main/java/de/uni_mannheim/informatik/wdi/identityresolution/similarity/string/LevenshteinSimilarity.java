package de.uni_mannheim.informatik.wdi.identityresolution.similarity.string;

import com.wcohen.ss.Levenstein;

import de.uni_mannheim.informatik.wdi.identityresolution.similarity.SimilarityMeasure;

/**
 * Calculates the Levenshtein similarity between two strings.
 * @author Oliver
 *
 */
public class LevenshteinSimilarity extends SimilarityMeasure<String> {

	@Override
	public double calculate(String first, String second) {
		if(first==null || second==null) {
			return 0.0;
		} else {
			Levenstein l = new Levenstein();
		
			double score = Math.abs(l.score(first, second));
	        score = score / Math.max(first.length(), second.length());
			
			return 1 - score;
		}
	}

}
