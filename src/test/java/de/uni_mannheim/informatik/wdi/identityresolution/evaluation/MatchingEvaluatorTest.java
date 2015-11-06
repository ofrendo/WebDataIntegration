package de.uni_mannheim.informatik.wdi.identityresolution.evaluation;

import java.util.LinkedList;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Correspondence;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;

import java.util.List;

import de.uni_mannheim.informatik.wdi.usecase.movies.Movie;
import junit.framework.TestCase;

public class MatchingEvaluatorTest extends TestCase {

	public void testEvaluateMatching() {
		MatchingEvaluator<Movie> evaluator = new MatchingEvaluator<>();
		List<Correspondence<Movie>> correspondences = new LinkedList<>();
		GoldStandard gold = new GoldStandard();
		
		Movie movie1 = new Movie("movie1", "test");
		Movie movie2 = new Movie("movie2", "test");
		Movie movie3 = new Movie("movie3", "test2");
		Movie movie4 = new Movie("movie4", "test2");

		correspondences.add(new Correspondence<Movie>(movie1, movie3, 1.0));
		correspondences.add(new Correspondence<Movie>(movie1, movie2, 1.0));
		
		gold.addPositiveExample(new Pair<String, String>(movie3.getIdentifier(), movie1.getIdentifier()));
		gold.addPositiveExample(new Pair<String, String>(movie2.getIdentifier(), movie4.getIdentifier()));
		gold.addNegativeExample(new Pair<String, String>(movie1.getIdentifier(), movie2.getIdentifier()));
		gold.addNegativeExample(new Pair<String, String>(movie3.getIdentifier(), movie4.getIdentifier()));
		
		Performance p = evaluator.evaluateMatching(correspondences, gold);
		
		assertEquals(0.5, p.getPrecision());
		assertEquals(0.5, p.getRecall());
	}

}
