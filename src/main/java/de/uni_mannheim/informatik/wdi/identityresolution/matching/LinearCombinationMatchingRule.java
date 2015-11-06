package de.uni_mannheim.informatik.wdi.identityresolution.matching;

import java.util.LinkedList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecord;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;

/**
 * A matching rule that is defined by a linear combination of attribute similarities.
 * @author Oliver
 *
 * @param <RecordType>
 */
public class LinearCombinationMatchingRule<RecordType extends Matchable> extends MatchingRule<RecordType> {

	private List<Pair<Comparator<RecordType>, Double>> comparators;
	private double offset;
	
	/**
	 * Initialises the rule. The finalThreshold determines the matching decision.
	 * @param finalThreshold
	 */
	public LinearCombinationMatchingRule(double finalThreshold) {
		super(finalThreshold);
		comparators = new LinkedList<>();
	}
	
	/**
	 * Initialises the rule. The offset is added to the weighted sum of similarities, the finalThreshold determines the matching decision.
	 * @param offset
	 * @param finalThreshold
	 */
	public LinearCombinationMatchingRule(double offset, double finalThreshold) {
		this(finalThreshold);
		this.offset = offset;
	}
	
	/**
	 * Adds a comparator with the specified weight to this rule. 
	 * @param comparator
	 * @param weight
	 */
	public void addComparator(Comparator<RecordType> comparator, double weight) {
		comparators.add(new Pair<Comparator<RecordType>, Double>(comparator, weight));
	}
	
	@Override
	public double compare(RecordType record1, RecordType record2) {
		double sum = offset;
		
		for(int i = 0; i < comparators.size(); i++) {
			Pair<Comparator<RecordType>, Double> pair = comparators.get(i);
			
			Comparator<RecordType> comp = pair.getFirst();
			
			double similarity = comp.compare(record1, record2);
			double weight = pair.getSecond();
			
			sum += (similarity * weight);
		}
		
		return sum;
	}

	@Override
	public DefaultRecord generateFeatures(RecordType record1, RecordType record2) {
		DefaultRecord model = new DefaultRecord(String.format("%s-%s", record1.getIdentifier(), record2.getIdentifier()), this.getClass().getSimpleName());
		
		double sum = offset;
		
		for(int i = 0; i < comparators.size(); i++) {
			Pair<Comparator<RecordType>, Double> pair = comparators.get(i);
			
			Comparator<RecordType> comp = pair.getFirst();
			
			double similarity = comp.compare(record1, record2);
			double weight = pair.getSecond();
			
			sum += (similarity * weight);
			
			String name = String.format("[%d] %s", i, comp.getClass().getSimpleName());
			model.setValue(name, Double.toString(similarity));
		}
		
		model.setValue("finalValue", Double.toString(sum));
		model.setValue("isMatch", Boolean.toString(sum >= getFinalThreshold()));
		
		return model;
	}
}
