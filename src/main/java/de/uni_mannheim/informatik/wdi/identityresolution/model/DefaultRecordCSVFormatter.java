package de.uni_mannheim.informatik.wdi.identityresolution.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.uni_mannheim.informatik.wdi.CSVFormatter;

/**
 * Formats a DefaultModel record as CSV
 * @author Oliver
 *
 */
public class DefaultRecordCSVFormatter extends CSVFormatter<DefaultRecord> {

	@Override
	public String[] getHeader(DefaultRecord exampleRecord) {
		List<String> names = new ArrayList<>(exampleRecord.getAttributeNames());
		Collections.sort(names);
		
		return names.toArray(new String[names.size()]);
	}
	
	@Override
	public String[] format(DefaultRecord record) {
		List<String> values = new ArrayList<>(record.getAttributeNames().size());
		
		List<String> names = new ArrayList<>(record.getAttributeNames());
		Collections.sort(names);
		
		for(String name : names) {
			values.add(record.getValue(name));
		}
		
		return values.toArray(new String[values.size()]);
	}

}
