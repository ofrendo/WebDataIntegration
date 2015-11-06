package de.uni_mannheim.informatik.wdi.identityresolution.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.uni_mannheim.informatik.wdi.Record;

/**
 * A default model that represents a record as a set of key/value pairs.
 * @author Oliver
 *
 */
public class DefaultRecord extends Record {

	private Map<String, String> values;
	private Map<String, List<String>> lists;
	
	public DefaultRecord(String identifier, String provenance) {
		super(identifier, provenance);
		values = new HashMap<>();
		lists = new HashMap<>();
	}
	
	public String getValue(String attributeName) {
		return values.get(attributeName);
	}

	public List<String> getList(String attributeName) {
		return lists.get(attributeName);
	}
	
	public void setValue(String attributeName, String value) {
		values.put(attributeName, value);
	}
	
	public void setList(String attributeName, List<String> list) {
		lists.put(attributeName, list);
	}
	
	public Set<String> getAttributeNames() {
		return values.keySet();
	}
	
	public Set<String> getListAttributeNames() {
		return lists.keySet();
	}
}
