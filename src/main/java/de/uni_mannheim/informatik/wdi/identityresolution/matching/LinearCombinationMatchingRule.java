package de.uni_mannheim.informatik.wdi.identityresolution.matching;

import java.util.LinkedList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.identityresolution.model.DefaultRecord;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

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
	
	private String id1 = null;
	private String id2 = null;
	/**
	 * Use this for printing out values for all comparisons for a given ID pair
	 * @param offset
	 * @param finalThreshold
	 * @param id1
	 * @param id2
	 */
	public LinearCombinationMatchingRule(double offset, double finalThreshold, String id1, String id2) {
		this(offset, finalThreshold);
		this.id1 = id1;
		this.id2 = id2;
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
		
		boolean printThis = (record1.getIdentifier().equals(id1) && record2.getIdentifier().equals(id2)) ||
				 (record1.getIdentifier().equals(id2) && record2.getIdentifier().equals(id1));
		
		if (printThis) {
			System.out.println("Comparing " + record1.getIdentifier() + " AND " + record2.getIdentifier());
			Company c1 = (Company) record1;
			Company c2 = (Company) record2;
			System.out.println("Name1=" + c1.getName() + " Name2=" + c2.getName());
		}

		for(int i = 0; i < comparators.size(); i++) {
			Pair<Comparator<RecordType>, Double> pair = comparators.get(i);
			
			Comparator<RecordType> comp = pair.getFirst();
			
			double similarity = comp.compare(record1, record2);
			double weight = pair.getSecond();
			
			if (printThis) 
				System.out.println(weight + " * " + similarity);
			
			sum += (similarity * weight);
		}
		 
		if (printThis) {
			System.out.println("= " + sum);
		}
			
		
		return sum;
	}

	@Override
	public DefaultRecord generateFeatures(RecordType record1, RecordType record2) {
//		System.out.println("r1: " + record1 + ": " + record1.getIdentifier());
//		System.out.println("r2: " + record2 + ": " + record2.getIdentifier());
		
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
		model.setValue("id1", record1.getIdentifier());
		model.setValue("id2", record2.getIdentifier()); 
		return model;
	}
}
