package de.uni_mannheim.informatik.wdi;


/**
 * The super class for all models, should be extended by all model classes.
 * @author Oliver
 *
 */
public abstract class Record implements Matchable {

	private String id;
	private String provenance;
	
	public Record(String identifier, String provenance) {
		id = identifier;
		this.provenance = provenance;
	}
	
	@Override
	public String getIdentifier() {
		return id;
	}

	@Override
	public String getProvenance() {
		return provenance;
	}

}
