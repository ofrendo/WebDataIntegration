package de.uni_mannheim.informatik.wdi.usecase.movies;

import org.joda.time.DateTime;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.wdi.MatchableFactory;

public class ActorFactory extends MatchableFactory<Actor> {

	@Override
	public Actor createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");
		
		// create the object with id and provenance information
		Actor actor = new Actor(id, provenanceInfo);
		
		// fill the attributes
		actor.setName(getValueFromChildElement(node, "name"));
		actor.setBirthplace(getValueFromChildElement(node, "birthplace"));
		
		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "birthday");
			if(date!=null) {
				DateTime dt = DateTime.parse(date);
				actor.setBirthday(dt);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return actor;
	}

}
