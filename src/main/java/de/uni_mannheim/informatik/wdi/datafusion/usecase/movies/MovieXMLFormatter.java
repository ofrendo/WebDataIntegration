package de.uni_mannheim.informatik.wdi.datafusion.usecase.movies;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.wdi.datafusion.XMLFormatter;

public class MovieXMLFormatter extends XMLFormatter<FusableMovie> {

	ActorXMLFormatter actorFormatter = new ActorXMLFormatter();
	
	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("movies");
	}

	@Override
	public Element createElementFromRecord(FusableMovie record, Document doc) {
		Element movie = doc.createElement("movie");

		movie.appendChild(createTextElement("id", record.getIdentifier(), doc));
		
		movie.appendChild(createTextElementWithProvenance("title", record.getTitle(), record.getMergedAttributeProvenance(FusableMovie.TITLE),doc));
		movie.appendChild(createTextElementWithProvenance("director", record.getDirector(), record.getMergedAttributeProvenance(FusableMovie.DIRECTOR),doc));
		movie.appendChild(createTextElementWithProvenance("date", record.getDate().toString(), record.getMergedAttributeProvenance(FusableMovie.DATE),doc));
		
		movie.appendChild(createActorsElement(record, doc));
		
		return movie;
	}

	protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}
	
	protected Element createActorsElement(FusableMovie record, Document doc) {
		Element actorRoot = actorFormatter.createRootElement(doc);
		actorRoot.setAttribute("provenanec", record.getMergedAttributeProvenance(FusableMovie.ACTORS));
		
		for(Actor a : record.getActors()) {
			actorRoot.appendChild(actorFormatter.createElementFromRecord(a, doc));
		}
		
		return actorRoot;
	}
	
}
