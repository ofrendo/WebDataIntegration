package de.uni_mannheim.informatik.wdi.usecase.movies;

import org.joda.time.DateTime;

import de.uni_mannheim.informatik.wdi.Record;

public class Actor extends Record {

/* example entry
			<actor>
				<name>Janet Gaynor</name>
				<birthday>1906-01-01</birthday>
				<birthplace>Pennsylvania</birthplace>
			</actor>
 */
	
	private String name;
	private DateTime birthday;
	private String birthplace;
	
	public Actor(String identifier, String provenance) {
		super(identifier, provenance);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DateTime getBirthday() {
		return birthday;
	}
	public void setBirthday(DateTime birthday) {
		this.birthday = birthday;
	}
	public String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

}
