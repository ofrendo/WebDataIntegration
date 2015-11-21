package de.uni_mannheim.informatik.wdi.datafusion.usecase.movies;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

import de.uni_mannheim.informatik.wdi.Record;

public class Movie extends Record {

/* example entry
	<movie>
		<id>academy_awards_2</id>
		<title>True Grit</title>
		<director>
			<name>Joel Coen and Ethan Coen</name>
		</director>
		<actors>
			<actor>
				<name>Jeff Bridges</name>
			</actor>
			<actor>
				<name>Hailee Steinfeld</name>
			</actor>
		</actors>
		<date>2010-01-01</date>
	</movie>
 */
	
	public Movie(String identifier, String provenance) {
		super(identifier, provenance);
		actors = new LinkedList<>();
	}

	private String title;
	private String director;
	private DateTime date;
	private List<Actor> actors;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
	}
	public List<Actor> getActors() {
		return actors;
	}
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}


}
