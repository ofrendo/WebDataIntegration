package de.uni_mannheim.informatik.wdi.identityresolution.matching;

import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecord;

/**
 * A comparator that check two attributes for equality
 * @author Oliver
 *
 */
public class EqualsComparator extends Comparator<DefaultRecord> {

	private String attributeName;
	
	public EqualsComparator(String attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	public double compare(DefaultRecord record1, DefaultRecord record2) {
		return record1.getValue(attributeName).equals(record2.getValue(attributeName)) ? 1.0 : 0.0;
	}
	
	
	
}
