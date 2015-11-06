package de.uni_mannheim.informatik.wdi.identityresolution.evaluation;

/**
 * Contains the evaluation performance.
 * @author Oliver
 *
 */
public class Performance {

	private int correct;
	private int created;
	private int correct_total;
	
	public Performance(int correct, int created, int correct_total) {
		this.correct = correct;
		this.created = created;
		this.correct_total = correct_total;
	}
	
	/**
	 * Returns the Precision
	 * @return
	 */
	public double getPrecision() {
		return (double)correct / (double)created;
	}
	
	/**
	 * Returns the Recall
	 * @return
	 */
	public double getRecall() {
		return (double)correct / (double)correct_total;
	}
	
	/** 
	 * Returns the F1-Measure
	 * @return
	 */
	public double getF1() {
		return (2 * getPrecision() * getRecall()) / (getPrecision() + getRecall());
	}
	
}
