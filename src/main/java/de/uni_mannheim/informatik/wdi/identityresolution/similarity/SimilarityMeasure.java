package de.uni_mannheim.informatik.wdi.identityresolution.similarity;

/**
 * The super class for all similarity measures
 * @author Oliver
 *
 * @param <DataType>
 */
public abstract class SimilarityMeasure<DataType> {

	/**
	 * Calculates the similarity of first and second
	 * @param first the first record
	 * @param second the second record
	 * @return the similarity of first and second
	 */
	public abstract double calculate(DataType first, DataType second);
	
}
