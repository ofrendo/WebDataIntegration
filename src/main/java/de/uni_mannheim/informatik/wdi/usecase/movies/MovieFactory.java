package de.uni_mannheim.informatik.wdi.usecase.movies;

import java.util.List;

import org.joda.time.DateTime;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.wdi.MatchableFactory;

public class MovieFactory extends MatchableFactory<Movie> {

	@Override
	public Movie createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");
		
		// create the object with id and provenance information
		Movie movie = new Movie(id, provenanceInfo);
		
		// fill the attributes
		movie.setTitle(getValueFromChildElement(node, "title"));
		movie.setDirector(getValueFromChildElement(node, "director"));

		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "date");
			if(date!=null && !date.isEmpty()) {
				DateTime dt = DateTime.parse(date);
				movie.setDate(dt);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// load the list of actors
		List<Actor> actors = getObjectListFromChildElement(node, "actors", "actor", new ActorFactory(), provenanceInfo);
		movie.setActors(actors);
		
		return movie;
	}

}
