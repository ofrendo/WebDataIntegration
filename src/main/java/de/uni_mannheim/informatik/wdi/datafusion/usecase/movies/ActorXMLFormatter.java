package de.uni_mannheim.informatik.wdi.datafusion.usecase.movies;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.wdi.datafusion.XMLFormatter;

public class ActorXMLFormatter extends XMLFormatter<Actor> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("actors");
	}

	@Override
	public Element createElementFromRecord(Actor record, Document doc) {
		Element actor = doc.createElement("actor");
		
		actor.appendChild(createTextElement("title", record.getName(), doc));
		
		return actor;
	}

}
