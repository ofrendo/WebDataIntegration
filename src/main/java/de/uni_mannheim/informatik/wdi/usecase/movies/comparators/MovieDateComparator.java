package de.uni_mannheim.informatik.wdi.usecase.movies.comparators;

import de.uni_mannheim.informatik.wdi.identityresolution.matching.Comparator;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.date.YearSimilarity;
import de.uni_mannheim.informatik.wdi.usecase.movies.Movie;

public class MovieDateComparator extends Comparator<Movie> {

	private YearSimilarity sim = new YearSimilarity(10);
	
	@Override
	public double compare(Movie entity1, Movie entity2) {
		double similarity = sim.calculate(entity1.getDate(), entity2.getDate());
		
		return similarity * similarity;
	}

}
