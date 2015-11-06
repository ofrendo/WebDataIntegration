package de.uni_mannheim.informatik.wdi.identityresolution.similarity.list;

import java.util.List;

import de.uni_mannheim.informatik.wdi.identityresolution.similarity.SimilarityMeasure;

/**
 * Calculates the overlap similarity between two lists of strings.
 * @author Oliver
 *
 */
public class OverlapSimilarity extends SimilarityMeasure<List<String>> {

	@Override
	public double calculate(List<String> first, List<String> second) {
		
		int min = Math.min(first.size(), second.size());
		int matches = 0;
		
		for(String s1 : first) {
			for(String s2 : second) {
				if(s1.equals(s2)) {
					matches++;
					continue;
				}
			}
		}
		
		return (double)matches / (double)min;
	}

}
