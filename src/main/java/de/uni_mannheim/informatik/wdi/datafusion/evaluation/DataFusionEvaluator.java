package de.uni_mannheim.informatik.wdi.datafusion.evaluation;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.AttributeFuser;
import de.uni_mannheim.informatik.wdi.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;

/**
 * Evalute a data fusion result based on a given data fusion strategy
 * @author Oliver
 *
 * @param <RecordType>
 */
public class DataFusionEvaluator<RecordType extends Matchable & Fusable> {
	
	private DataFusionStrategy<RecordType> strategy;
	private boolean verbose = false;
	
	/**
	 * Returns whether additional information will be written to the console 
	 * @return
	 */
	public boolean isVerbose() {
		return verbose;
	}
	/**
	 * Sets whether additional information will be written to the console
	 * @param verbose
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	/**
	 * Creates a new instance with the provided strategy
	 * @param strategy
	 */
	public DataFusionEvaluator(DataFusionStrategy<RecordType> strategy) {
		this.strategy = strategy;
	}
	
	/**
	 * Evaluates the the data fusion result against a gold standard
	 * @param dataset
	 * @param goldStandard
	 * @return the accuracy of the data fusion resut
	 */
	public double evaluate(DataSet<RecordType> dataset, DataSet<RecordType> goldStandard) {
		
		int correctValues = 0;
		int totalValues = goldStandard.getSize() * strategy.getEvaluationRules().size();
		
		for(RecordType record : goldStandard.getRecords()) {
			RecordType fused = dataset.getRecord(record.getIdentifier());
			//System.out.println(fused);
			if(fused!=null) {
				for(String attribute : strategy.getEvaluationRules().keySet()) {
					EvaluationRule<RecordType> r = strategy.getEvaluationRules().get(attribute);
					AttributeFuser<RecordType> f = strategy.getAttributeFusers().get(attribute);
					
					if((!f.hasValue(record) && !f.hasValue(fused)) // neither gs nor fused record has a value
						|| (f.hasValue(record) && f.hasValue(fused) && r.isEqual(fused, record))) { // both have a value and it's the same value
						correctValues++;
					} else if(verbose) {
						String attributeName = r.getClass().getSimpleName().replaceAll("EvaluationRule", "");
						String[] fusedLines = fused.toString().split("\n");
						String[] recordLines = record.toString().split("\n");
						
						String resultFused = "";
						for (String fLine : fusedLines) {
							if ((fLine.contains("Provenance") || fLine.contains("ID") || fLine.contains("Name") || fLine.contains(attributeName)) 
									&& !fLine.toLowerCase().contains("original")) {
								resultFused += "\n" + fLine;
							}
						}
						String resultRecord = "";
						for (String rLine : recordLines) {
							if ((rLine.contains("Provenance") || rLine.contains("ID") || rLine.contains("Name") || rLine.contains(attributeName))
									&& !rLine.toLowerCase().contains("original")) {
								resultRecord += "\n" + rLine;
							}
						}
						System.out.println("=================================");
						System.out.println(String.format("[error] %s: %s <> %s", r.getClass().getSimpleName(), resultFused, resultRecord));
					}
				}
			}
		}
		
		return (double)correctValues / (double)totalValues;
	}
}
