package de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;

public class DirectorEvaluationRule extends EvaluationRule<FusableMovie> {

	@Override
	public boolean isEqual(FusableMovie record1, FusableMovie record2) {
		return record1.getDirector().equals(record2.getDirector());
	}

}
