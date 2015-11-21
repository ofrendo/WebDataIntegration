package de.uni_mannheim.informatik.wdi.datafusion.usecase.movies;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.wdi.datafusion.Fusable;

public class FusableMovie extends Movie implements Fusable {

	public static final String TITLE = "Title";
	public static final String DIRECTOR = "Director";
	public static final String DATE = "Date";
	public static final String ACTORS = "Actors";
	
	private Map<String, Collection<String>> provenance = new HashMap<>();
	
	public void setRecordProvenance(Collection<String> provenance) {
		this.provenance.put("RECORD", provenance);
	}
	public Collection<String> getRecordProvenance() {
		return provenance.get("RECORD");
	}
	
	public void setAttributeProvenance(String attribute, Collection<String> provenance) {
		this.provenance.put(attribute, provenance);
	}
	public Collection<String> getAttributeProvenance(String attribute) {
		return provenance.get(attribute);
	}
	public String getMergedAttributeProvenance(String attribute) {
		Collection<String> prov = provenance.get(attribute);
		
		if(prov!=null) {
			return StringUtils.join(prov, "+");
		} else {
			return "";
		}
	}
	
	public FusableMovie(String identifier, String provenance) {
		super(identifier, provenance);
	}

	@Override
	public Collection<String> getAttributeNames() {
		return Arrays.asList(new String[] { TITLE, DIRECTOR, DATE, ACTORS});
	}

	@Override
	public boolean hasValue(String attributeName) {
		switch (attributeName) {
		case TITLE:
			return getTitle()!=null && !getTitle().isEmpty();
		case DIRECTOR:
			return getDirector()!=null && !getDirector().isEmpty();
		case DATE:
			return getDate()!=null;
		case ACTORS:
			return getActors()!=null && getActors().size()>0;
		default:
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("[Movie: %s / %s / %s]", getTitle(), getDirector(), getDate().toString());
	}

}
