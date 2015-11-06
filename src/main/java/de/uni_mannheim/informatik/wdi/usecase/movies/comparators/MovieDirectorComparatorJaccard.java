package de.uni_mannheim.informatik.wdi.usecase.movies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.TokenizingJaccardSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.movies.Movie;

public class MovieDirectorComparatorJaccard extends Comparator<Movie> {

	TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
	
	@Override
	public double compare(Movie entity1, Movie entity2) {
		
		// preprocessing
		String s1 = entity1.getDirector().toLowerCase();
		String s2 = entity2.getDirector().toLowerCase();
		
		// calculate similarity
		double similarity = sim.calculate(s1, s2);
		
		// postprocessing
		if(similarity<=0.3) {
			similarity = 0;
		}
		
		similarity *= similarity;
		
		return similarity;
	}

}
