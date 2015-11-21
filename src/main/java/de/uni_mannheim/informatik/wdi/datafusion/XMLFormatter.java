package de.uni_mannheim.informatik.wdi.datafusion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Abstract super class for specifying how a dataset should be transformed into XML
 * @author Oliver
 *
 * @param <RecordType>
 */
public abstract class XMLFormatter<RecordType> {

	/**
	 * Creates the root element for a collection of records
	 * @param doc
	 * @return
	 */
	public abstract Element createRootElement(Document doc);
	
	/**
	 * Creates an element representing a record
	 * @param record
	 * @param doc
	 * @return
	 */
	public abstract Element createElementFromRecord(RecordType record, Document doc);
	
	/**
	 * Creates a text element with the specified element name and the value as content
	 * @param name
	 * @param value
	 * @param doc
	 * @return
	 */
	protected Element createTextElement(String name, String value, Document doc) {
		Element elem = doc.createElement(name);
		if(value!=null) {
			elem.appendChild(doc.createTextNode(value));
		}
		return elem;
	}
}
