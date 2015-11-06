package de.uni_mannheim.informatik.wdi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * A Data set contains a set of records.
 * 
 * @author Oliver
 * 
 * @param <RecordType>
 */
public class DataSet<RecordType extends Matchable> {

	private Map<String, RecordType> records;

	public DataSet() {
		records = new HashMap<>();
	}

	/**
	 * Loads a data set from an XML file
	 * 
	 * @param dataSource
	 *            the XML file containing the data
	 * @param entryFactory
	 *            the Factory that creates the Model instances from the XML
	 *            nodes
	 * @param entriesPath
	 *            the XPath to the XML nodes representing the entries
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws XPathExpressionException
	 */
	public void loadFromXML(File dataSource,
			MatchableFactory<RecordType> modelFactory, String recordPath)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {
		// create objects for reading the XML file
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		Document doc = builder.parse(dataSource);

		// prepare the XPath that selects the entries
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();
		XPathExpression expr = xpath.compile(recordPath);

		// execute the XPath to get all entries
		NodeList list = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

		if (list.getLength() == 0) {
			System.out.println("ERROR: no elements matching the XPath ("
					+ recordPath + ") found in the input file "
					+ dataSource.getAbsolutePath());
		} else {
			System.out.println(String.format("Loading %d elements from %s",
					list.getLength(), dataSource.getName()));

			// create entries from all nodes matching the XPath
			for (int i = 0; i < list.getLength(); i++) {

				// create the entry, use file name as provenance information
				RecordType record = modelFactory.createModelFromElement(list.item(i),
						dataSource.getName());

				if (record != null) {
					// add it to the data set
					addRecord(record);
				} else {
					System.out.println(String.format(
							"Could not generate entry for ", list.item(i)
									.getTextContent()));
				}
			}
		}
	}

	/**
	 * Returns a collection with all entries of this data set.
	 * 
	 * @return
	 */
	public Collection<RecordType> getRecords() {
		return records.values();
	}

	/**
	 * Returns the entry with the specified identifier or null, if it is not
	 * found.
	 * 
	 * @param identifier
	 *            The identifier of the entry that should be returned
	 * @return
	 */
	public RecordType getRecord(String identifier) {
		return records.get(identifier);
	}

	/**
	 * Returns the number of entries in this data set
	 * 
	 * @return
	 */
	public int getSize() {
		return records.size();
	}

	/**
	 * Adds an entry to this data set. Any existing entry with the same
	 * identifier will be replaced.
	 * 
	 * @param entry
	 */
	public void addRecord(RecordType record) {
		records.put(record.getIdentifier(), record);
	}

	/**
	 * Returns a random record from the data set
	 * @return
	 */
	public RecordType getRandomRecord() {
		Random r = new Random();

		List<RecordType> allRecords = new ArrayList<>(records.values());

		int index = r.nextInt(allRecords.size());

		return allRecords.get(index);
	}

	/**
	 * Writes the data set to a CSV file
	 * @param file
	 * @param formatter
	 * @throws IOException
	 */
	public void writeCSV(File file, CSVFormatter<RecordType> formatter)
			throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(file));

		String[] headers = formatter.getHeader(getRandomRecord());
		writer.writeNext(headers);

		for (RecordType record : records.values()) {
			String[] values = formatter.format(record);

			writer.writeNext(values);
		}

		writer.close();
	}
}
