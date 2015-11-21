package de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.SimilarityMeasure;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.TokenizingJaccardSimilarity;

public class TitleEvaluationRule extends EvaluationRule<FusableMovie> {

	SimilarityMeasure<String> sim = new TokenizingJaccardSimilarity();

	@Override
	public boolean isEqual(FusableMovie record1, FusableMovie record2) {
		// the title is correct if all tokens are there, but the order does not matter
		return sim.calculate(record1.getTitle(), record2.getTitle()) == 1.0;
	}

}
