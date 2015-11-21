package de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.evaluation;

import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.Actor;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;

public class ActorsEvaluationRule extends EvaluationRule<FusableMovie> {

	@Override
	public boolean isEqual(FusableMovie record1, FusableMovie record2) {
		Set<String> actors1 = new HashSet<>();
		
		for(Actor a : record1.getActors()) {
			// note: evaluating using the actor's name only suffices for simple lists
			// in your project, you should have actor ids which you use here (and in the identity resolution)
			actors1.add(a.getName());
		}
		
		Set<String> actors2 = new HashSet<>();
		for(Actor a : record2.getActors()) {
			actors2.add(a.getName());
		}
		
		return actors1.containsAll(actors2) && actors2.containsAll(actors1);
	}

}
