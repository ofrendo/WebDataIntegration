package de.uni_mannheim.informatik.wdi.identityresolution.evaluation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.Correspondence;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

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
		
		double lowestCorrect = Integer.MAX_VALUE;
		double highestWrong = -1;
		
		for(Correspondence<RecordType> correspondence : correspondences) {
			
			RecordType r1 = correspondence.getFirstRecord();
			RecordType r2 = correspondence.getSecondRecord();	
			
			if(goldStandard.containsPositive(correspondence.getFirstRecord(), correspondence.getSecondRecord())) {
				correct++;
				matched++;
				lowestCorrect = (correspondence.getSimilarityScore() < lowestCorrect) ? 
							correspondence.getSimilarityScore() :
							lowestCorrect;
				
				if(verbose) {
					if (correspondence.getFirstRecord() instanceof Company) {
						String name2 = ((Company) r2).getName() != null ?
								((Company) r2).getName() :
								((Company) r2).getLocations().get(0).getName();
						
						System.out.println(String.format("[correct] %s,%s,%s, %s, %S", 
								r1.getIdentifier(), 
								r2.getIdentifier(), 
								Double.toString(correspondence.getSimilarityScore()),
								((Company) r1).getName(),
								name2));
					}
					else {
						System.out.println(String.format("[correct] %s,%s,%s", 
								correspondence.getFirstRecord().getIdentifier(), 
								correspondence.getSecondRecord().getIdentifier(), 
								Double.toString(correspondence.getSimilarityScore())));
					}
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
				highestWrong = (correspondence.getSimilarityScore() > highestWrong) ? 
						correspondence.getSimilarityScore() :
						highestWrong;
				
				if(verbose) {
					String name2 = ((Company) r2).getName() != null ?
							((Company) r2).getName() :
							((Company) r2).getLocations().get(0).getName();
					
					if (correspondence.getFirstRecord() instanceof Company) {
						System.out.println(String.format("[wrong] %s,%s,%s, %s, %S", 
								r1.getIdentifier(), 
								r2.getIdentifier(), 
								Double.toString(correspondence.getSimilarityScore()),
								((Company) r1).getName(),
								name2));
					}
					else {
						System.out.println(String.format("[wrong] %s,%s,%s", 
								correspondence.getFirstRecord().getIdentifier(), 
								correspondence.getSecondRecord().getIdentifier(), 
								Double.toString(correspondence.getSimilarityScore())));
					}
				}
			}
		}
		
		if(verbose) {
			// print all missing positive examples
			for(Pair<String, String> p : positives) {
				System.out.println(String.format("[missing] %s,%s", p.getFirst(), p.getSecond()));
			}
			System.out.println("lowestCorrect: " + lowestCorrect);
			System.out.println("highestWrong: " + highestWrong);
		}
		
		return new Performance(correct, matched, correct_max);
	}

}
