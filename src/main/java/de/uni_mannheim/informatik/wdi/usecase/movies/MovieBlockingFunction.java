package de.uni_mannheim.informatik.wdi.usecase.movies;

import de.uni_mannheim.informatik.wdi.identityresolution.blocking.BlockingFunction;

public class MovieBlockingFunction extends BlockingFunction<Movie> {

	@Override
	public String getBlockingKey(Movie instance) {
		return Integer.toString(instance.getDate().getYear() / 10);
	}

}
