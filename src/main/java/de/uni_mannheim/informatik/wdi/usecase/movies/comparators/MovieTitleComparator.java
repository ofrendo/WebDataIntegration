package de.uni_mannheim.informatik.wdi.usecase.movies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.LevenshteinSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.movies.Movie;

public class MovieTitleComparator extends Comparator<Movie> {

	private LevenshteinSimilarity sim = new LevenshteinSimilarity();
	
	@Override
	public double compare(Movie entity1, Movie entity2) {
		double similarity = sim.calculate(entity1.getTitle(), entity2.getTitle());
		
		return similarity;
	}

}
