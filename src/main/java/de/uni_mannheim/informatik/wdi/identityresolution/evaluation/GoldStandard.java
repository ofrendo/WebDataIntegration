package de.uni_mannheim.informatik.wdi.identityresolution.evaluation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;
import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;

/**
 * Contains a gold standard.
 * 
 * @author Oliver
 *
 */
public class GoldStandard {

	private List<Pair<String, String>> positiveExamples;
	private List<Pair<String, String>> negativeExamples;
	private Set<String> canonicalPositiveExamples;
	private Set<String> canonicalNegativeExamples;

	/**
	 * Returns all positive examples in this gold standard
	 * 
	 * @return
	 */
	public List<Pair<String, String>> getPositiveExamples() {
		return positiveExamples;
	}

	/**
	 * Returns all negative examples in this gold standard
	 * 
	 * @return
	 */
	public List<Pair<String, String>> getNegativeExamples() {
		return negativeExamples;
	}

	/**
	 * Adds a positive example to the gold standard
	 * 
	 * @param correspondence
	 */
	public void addPositiveExample(Pair<String, String> example) {
		positiveExamples.add(example);
		canonicalPositiveExamples.add(getCanonicalExample(example.getFirst(),
				example.getSecond()));
	}

	/**
	 * Adds a negative example to the gold standard
	 * 
	 * @param example
	 */
	public void addNegativeExample(Pair<String, String> example) {
		negativeExamples.add(example);
		canonicalNegativeExamples.add(getCanonicalExample(example.getFirst(),
				example.getSecond()));
	}

	/**
	 * Checks if the gold standard contains the given combination of record ids
	 * as a positive example
	 * 
	 * @param correspondence
	 *            the correspondence
	 * @return
	 */
	public boolean containsPositive(String id1, String id2) {
		String c = getCanonicalExample(id1, id2);

		return canonicalPositiveExamples.contains(c);
	}

	/**
	 * Checks if the gold standard contains the given combination of records as
	 * a positive example
	 * 
	 * @param record1
	 *            the first record
	 * @param record2
	 *            the second record
	 * @return
	 */
	public boolean containsPositive(Matchable record1, Matchable record2) {
		String c = getCanonicalExample(record1, record2);

		return canonicalPositiveExamples.contains(c);
	}

	/**
	 * Checks if the gold standard contains the given combination of record ids
	 * as a negative example
	 * 
	 * @param correspondence
	 *            the correspondence
	 * @return
	 */
	public boolean containsNegative(String id1, String id2) {
		String c = getCanonicalExample(id1, id2);

		return canonicalNegativeExamples.contains(c);
	}

	/**
	 * Checks if the gold standard contains the given combination of records as
	 * a negative example
	 * 
	 * @param record1
	 *            the first record
	 * @param record2
	 *            the second record
	 * @return
	 */
	public boolean containsNegative(Matchable record1, Matchable record2) {
		String c = getCanonicalExample(record1, record2);

		return canonicalNegativeExamples.contains(c);
	}

	public GoldStandard() {
		positiveExamples = new LinkedList<>();
		negativeExamples = new LinkedList<>();
		canonicalPositiveExamples = new HashSet<>();
		canonicalNegativeExamples = new HashSet<>();
	}

	/**
	 * Loads a gold standard from a CSV file
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void loadFromCSVFile(File file) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(file));

		String[] values = null;
		
		while ((values = reader.readNext()) != null) {

			if (values.length == 3) {

				boolean isPositive = Boolean.parseBoolean(values[2]);

				Pair<String, String> example = new Pair<String, String>(
						values[0], values[1]);

				if (isPositive) {
					addPositiveExample(example);
				} else {
					addNegativeExample(example);
				}

			} else {
				System.err.println(String.format("Skipping malformed line: %s", values.toString()));
			}
		}

		reader.close();

		printGSReport();
	}

	private void printGSReport() {
		int numPositive = getPositiveExamples().size();
		int numNegative = getNegativeExamples().size();
		int ttl = numPositive + numNegative;
		double positivePerCent = (double) numPositive / (double) ttl;
		double negativePerCent = (double) numNegative / (double) ttl;

		System.out
				.println(String
						.format("The gold standard has %d examples\n\t%d positive examples (%.2f%%)\n\t%d negative examples (%.2f%%)",
								ttl, numPositive, positivePerCent, numNegative,
								negativePerCent));
		
		// check for duplicates
		if(getPositiveExamples().size()!=canonicalPositiveExamples.size()) {
			System.err.println("The gold standard contains duplicate positive examples!");
		}
		if(getNegativeExamples().size()!=canonicalNegativeExamples.size()) {
			System.err.println("The gold standard contains duplicate negative examples!");
		}
		
		// check if any example was labeled as positive and negative
		HashSet<String> allExamples = new HashSet<>();
		allExamples.addAll(canonicalPositiveExamples);
		allExamples.addAll(canonicalNegativeExamples);
		
		if(allExamples.size() != ttl) {
			System.err.println("The gold standard contains an example that is both labelled as positive and negative!");
		}
		
	}
	
	public void printBalanceReport() {
		int numPositive = getPositiveExamples().size();
		int numNegative = getNegativeExamples().size();
		int ttl = numPositive + numNegative;
		double positivePerCent = (double) numPositive / (double) ttl;
		double negativePerCent = (double) numNegative / (double) ttl;

		if (Math.abs(positivePerCent - negativePerCent) > 0.2) {
			System.err.println("The gold standard is imbalanced!");
		}
	}

	private String getCanonicalExample(String id1, String id2) {
		String first, second;

		if (id1.compareTo(id2) <= 0) {
			first = id1;
			second = id2;
		} else {
			first = id2;
			second = id1;
		}

		return first + "|" + second;
	}

	private String getCanonicalExample(Matchable record1, Matchable record2) {
		String first, second;

		if (record1.getIdentifier().compareTo(record2.getIdentifier()) <= 0) {
			first = record1.getIdentifier();
			second = record2.getIdentifier();
		} else {
			first = record2.getIdentifier();
			second = record1.getIdentifier();
		}

		return first + "|" + second;
	}
}
