package de.uni_mannheim.informatik.wdi.datafusion;

import java.util.HashMap;
import java.util.Map;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;

/**
 * Defines which fusers should be applied and which evaluation rules should be used during data fusion
 * @author Oliver
 *
 * @param <RecordType>
 */
public class DataFusionStrategy<RecordType extends Matchable & Fusable> {

	private Map<String, AttributeFuser<RecordType>> attributeFusers;
	private Map<String, EvaluationRule<RecordType>> evaluationRules;
	private FusableFactory<RecordType> factory;
	
	/**
	 * Creates a new instance and specifies which factory to use when creating fused records
	 * @param factory
	 */
	public DataFusionStrategy(FusableFactory<RecordType> factory) {
		attributeFusers = new HashMap<>();
		evaluationRules = new HashMap<>();
		this.factory = factory;
	}
	
	/**
	 * Adds a combination of fuser and evaluation rule. The evaluation rule will be used to evaluate the result of the fuser.
	 * @param attributeName
	 * @param fuser
	 * @param rule
	 */
	public void addAttributeFuser(String attributeName, AttributeFuser<RecordType> fuser, EvaluationRule<RecordType> rule) {
		attributeFusers.put(attributeName, fuser);
		evaluationRules.put(attributeName, rule);
	}

	/**
	 * Applies the strategy (i.e. all specified fusers) to the given group of records
	 * @param group
	 * @return
	 */
	public RecordType apply(RecordGroup<RecordType> group) {
		RecordType fusedRecord = factory.createInstanceForFusion(group);
		
		for(AttributeFuser<RecordType> f : attributeFusers.values()) {
			f.fuse(group, fusedRecord);
		}
		
		return fusedRecord;
	}
	
	/**
	 * returns the evaluation rules specified for this strategy
	 * @return
	 */
	public Map<String, EvaluationRule<RecordType>> getEvaluationRules() {
		return evaluationRules;
	}
	
	/**
	 * returns the fusers specified for this strategy
	 * @return
	 */
	public Map<String, AttributeFuser<RecordType>> getAttributeFusers() {
		return attributeFusers;
	}
	
	/**
	 * calculates the number of non-conflicting values for the given group of records, according the fusers of this strategy
	 * @param group
	 * @return
	 */
	public Map<String, Integer> getNumberOfNonConflictingValues(RecordGroup<RecordType> group) {
		Map<String, Integer> counts = new HashMap<>();
		
		for(String att : attributeFusers.keySet()) {
			
			AttributeFuser<RecordType> fuser = attributeFusers.get(att);
			EvaluationRule<RecordType> rule = evaluationRules.get(att);
		
			Integer cnt = counts.get(att);
			if(cnt==null) {
				cnt = 0;
			}
			
			if(!fuser.hasConflictingValues(group, rule)) {
				cnt++;
			}
			
			counts.put(att, cnt);
		}
		
		return counts;
	}
	
}
