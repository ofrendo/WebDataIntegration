package de.uni_mannheim.informatik.wdi;

/**
 * An interface defining the basic requirements for everything that can be matched with this framework.
 * @author Oliver
 *
 */
public interface Matchable {
	
	/**
	 * Returns the (unique) identifier for this record.
	 * @return
	 */
	String getIdentifier();
	
	/**
	 * Returns the provenance information for this record.
	 * @return
	 */
	String getProvenance();
}
