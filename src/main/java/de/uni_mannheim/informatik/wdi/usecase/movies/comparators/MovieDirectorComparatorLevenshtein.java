package de.uni_mannheim.informatik.wdi.usecase.movies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.LevenshteinSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.movies.Movie;

public class MovieDirectorComparatorLevenshtein extends Comparator<Movie> {

	private LevenshteinSimilarity sim = new LevenshteinSimilarity();
	
	@Override
	public double compare(Movie entity1, Movie entity2) {
		return sim.calculate(entity1.getDirector(), entity2.getDirector());
	}

}
