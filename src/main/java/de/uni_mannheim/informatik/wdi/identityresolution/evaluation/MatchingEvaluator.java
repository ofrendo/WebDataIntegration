package de.uni_mannheim.informatik.wdi.identityresolution.evaluation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.Correspondence;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;

/**
 * Evaluates a set of correspondences against a gold standard.
 * @author Oliver
 *
 * @param <RecordType>
 */
public class MatchingEvaluator<RecordType extends Matchable> {

	private boolean verbose = false;
	
	public MatchingEvaluator() {
	}
	
	public MatchingEvaluator(boolean isVerbose) {
		verbose = isVerbose;
	}
	
	/**
	 * Evaluates the given correspondences against the gold standard
	 * @param correspondences the correspondences to evaluate
	 * @param goldStandard the gold standard
	 * @return the result of the evaluation
	 */
	public Performance evaluateMatching(List<Correspondence<RecordType>> correspondences, GoldStandard goldStandard) {
		int correct = 0;
		int matched = 0;
		int correct_max = goldStandard.getPositiveExamples().size();
		
		// keep a list of all unmatched positives for later output
		List<Pair<String, String>> positives = new ArrayList<>(goldStandard.getPositiveExamples());
		
		for(Correspondence<RecordType> correspondence : correspondences) {
			if(goldStandard.containsPositive(correspondence.getFirstRecord(), correspondence.getSecondRecord())) {
				correct++;
				matched++;
				
				if(verbose) {
					System.out.println(String.format("[correct] %s,%s,%s", correspondence.getFirstRecord().getIdentifier(), correspondence.getSecondRecord().getIdentifier(), Double.toString(correspondence.getSimilarityScore())));
					
					// remove pair from positives
					Iterator<Pair<String, String>> it = positives.iterator();
					while(it.hasNext()) {
						Pair<String, String> p = it.next();
						String id1 = correspondence.getFirstRecord().getIdentifier();
						String id2 = correspondence.getSecondRecord().getIdentifier();
						
						if(p.getFirst().equals(id1) && p.getSecond().equals(id2)
								|| p.getFirst().equals(id2) && p.getSecond().equals(id1)) {
							it.remove();
						}
					}
				}
			} else if(goldStandard.containsNegative(correspondence.getFirstRecord(), correspondence.getSecondRecord())) {
				matched++;
				
				if(verbose) {
					System.out.println(String.format("[wrong] %s,%s,%s", correspondence.getFirstRecord().getIdentifier(), correspondence.getSecondRecord().getIdentifier(), Double.toString(correspondence.getSimilarityScore())));
				}
			}
		}
		
		if(verbose) {
			// print all missing positive examples
			for(Pair<String, String> p : positives) {
				System.out.println(String.format("[missing] %s,%s", p.getFirst(), p.getSecond()));
			}
		}
		
		return new Performance(correct, matched, correct_max);
	}

}
