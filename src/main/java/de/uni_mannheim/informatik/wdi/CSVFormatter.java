package de.uni_mannheim.informatik.wdi;

/**
 * Super class for formatting a Matchable as CSV
 * @author Oliver
 *
 */
public abstract class CSVFormatter<RecordType extends Matchable> {

	public abstract String[] getHeader(RecordType exampleRecord);
	
	public abstract String[] format(RecordType record);
	
}
