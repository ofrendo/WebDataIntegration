package de.uni_mannheim.informatik.wdi.identityresolution.similarity.string;

import com.wcohen.ss.Jaccard;
import com.wcohen.ss.tokens.SimpleTokenizer;

import de.uni_mannheim.informatik.wdi.identityresolution.similarity.SimilarityMeasure;

/**
 * Calculates the Jaccard similarity between two strings.
 * @author Oliver
 *
 */
public class TokenizingJaccardSimilarity extends SimilarityMeasure<String> {

	@Override
	public double calculate(String first, String second) {
		Jaccard j = new Jaccard(new SimpleTokenizer(true, true));
        return j.score(first, second);
	}

}
