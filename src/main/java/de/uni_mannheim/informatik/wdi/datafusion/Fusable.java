package de.uni_mannheim.informatik.wdi.datafusion;

import java.util.Collection;

/**
 * Interface representing all fusable records
 * @author Oliver
 *
 */
public interface Fusable {

	/**
	 * Returns the names of all attributes of the record. Required for generating the fusion reports.
	 * @return
	 */
	Collection<String> getAttributeNames();
	
	/**
	 * Returns whether the attribute has a value. Required for the calculation of density.
	 * @param attributeName
	 * @return
	 */
	boolean hasValue(String attributeName);

}
