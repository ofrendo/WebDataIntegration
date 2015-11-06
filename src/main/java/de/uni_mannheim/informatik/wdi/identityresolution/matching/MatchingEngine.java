package de.uni_mannheim.informatik.wdi.identityresolution.matching;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joda.time.DateTime;

import au.com.bytecode.opencsv.CSVWriter;
import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.identityresolution.blocking.Blocker;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.GoldStandard;
import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecord;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;
import de.uni_mannheim.informatik.wdi.utils.ProgressReporter;

/**
 * The matching engine that executes a given matching rule on two data sets
 * 
 * @author Oliver
 *
 * @param <RecordType>
 */
public class MatchingEngine<RecordType extends Matchable> {

	private MatchingRule<RecordType> rule;
	private Blocker<RecordType> blocker;

	/**
	 * Creates a matching engine with the specified rule and blocker
	 * 
	 * @param rule
	 * @param blocker
	 */
	public MatchingEngine(MatchingRule<RecordType> rule, Blocker<RecordType> blocker) {
		this.rule = rule;
		this.blocker = blocker;
	}

	/**
	 * Runs the matching on the given data sets
	 * 
	 * @param dataset1
	 *            The first data set
	 * @param dataset2
	 *            The second data set
	 * @return A list of correspondences
	 */
	public List<Correspondence<RecordType>> runMatching(DataSet<RecordType> dataset1,
			DataSet<RecordType> dataset2) {
		long start = System.currentTimeMillis();

		System.out.println(String.format("[%s] Starting Matching",
				new DateTime(start).toString()));

		List<Correspondence<RecordType>> result = new LinkedList<>();

		// use the blocker to generate pairs
		List<Pair<RecordType, RecordType>> allPairs = blocker.generatePairs(dataset1,
				dataset2);

		System.out
				.println(String
						.format("Matching %,d x %,d elements; %,d blocked pairs (reduction ratio: %.2f)",
								dataset1.getSize(), dataset2.getSize(),
								allPairs.size(), blocker.getReductionRatio()));

		// compare the pairs using the matching rule
		ProgressReporter progress = new ProgressReporter(allPairs.size(),
				"Matching");
		for (Pair<RecordType, RecordType> pair : allPairs) {

			// apply the matching rule
			Correspondence<RecordType> cor = rule.apply(pair.getFirst(), pair.getSecond());
			if (cor!=null) {

				// add the correspondences to the result
				result.add(cor);
			}

			// increment and report status
			progress.incrementProgress();
			progress.report();
		}

		// report total matching time
		long end = System.currentTimeMillis();
		long delta = end - start;
		System.out.println(String.format(
				"[%s] Matching finished after %s; found %,d correspondences.",
				new DateTime(end).toString(),
				DurationFormatUtils.formatDurationHMS(delta), result.size()));

		return result;
	}

	/**
	 * Generates a data set containing features that can be used to learn
	 * matching rules.
	 * 
	 * @param dataset1
	 *            The first data set
	 * @param dataset2
	 *            The second data set
	 * @param goldStandard
	 *            The gold standard containing the labels for the generated data
	 *            set
	 * @return
	 */
	public DataSet<DefaultRecord> generateTrainingDataForLearning(
			DataSet<RecordType> dataset1, DataSet<RecordType> dataset2,
			GoldStandard goldStandard) {
		long start = System.currentTimeMillis();

		goldStandard.printBalanceReport();
		
		System.out.println(String.format("[%s] Starting GenerateFeatures",
				new DateTime(start).toString()));

		DataSet<DefaultRecord> result = new DataSet<>();

		ProgressReporter progress = new ProgressReporter(goldStandard
				.getPositiveExamples().size()
				+ goldStandard.getNegativeExamples().size(), "GenerateFeatures");
		
		// create positive examples
		for (Pair<String, String> correspondence : goldStandard
				.getPositiveExamples()) {
			RecordType record1 = dataset1.getRecord(correspondence.getFirst());
			RecordType record2 = dataset2.getRecord(correspondence.getSecond());

			// we don't know which id is from which data set
			if (record1 == null && record2 == null) {
				// so if we didn't find anything, we probably had it wrong ...
				record1 = dataset2.getRecord(correspondence.getFirst());
				record2 = dataset1.getRecord(correspondence.getSecond());
			}

			DefaultRecord features = rule.generateFeatures(record1, record2);
			features.setValue("label", "1");
			result.addRecord(features);

			// increment and report status
			progress.incrementProgress();
			progress.report();
		}

		// create negative examples
		for (Pair<String, String> correspondence : goldStandard
				.getNegativeExamples()) {
			RecordType record1 = dataset1.getRecord(correspondence.getFirst());
			RecordType record2 = dataset2.getRecord(correspondence.getSecond());

			// we don't know which id is from which data set
			if (record1 == null && record2 == null) {
				// so if we didn't find anything, we probably had it wrong ...
				record1 = dataset2.getRecord(correspondence.getFirst());
				record2 = dataset1.getRecord(correspondence.getSecond());
			}

			DefaultRecord features = rule.generateFeatures(record1, record2);
			features.setValue("label", "0");
			result.addRecord(features);

			// increment and report status
			progress.incrementProgress();
			progress.report();
		}

		// report total time
		long end = System.currentTimeMillis();
		long delta = end - start;
		System.out
				.println(String
						.format("[%s] GenerateFeatures finished after %s; created %,d examples.",
								new DateTime(end).toString(),
								DurationFormatUtils.formatDurationHMS(delta),
								result.getSize()));

		return result;
	}
	
	public void writeCorrespondences(List<Correspondence<RecordType>> correspondences, File file) throws IOException {
		CSVWriter w = new CSVWriter(new FileWriter(file));
		
		for(Correspondence<RecordType> c : correspondences) {
			w.writeNext(new String[] { c.getFirstRecord().getIdentifier(), c.getSecondRecord().getIdentifier(), Double.toString(c.getSimilarityScore())});
		}
		
		w.close();
	}
}
