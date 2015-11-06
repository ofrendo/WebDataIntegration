package de.uni_mannheim.informatik.wdi.identityresolution.similarity;

/**
 * A similarity measure that checks two recrods for equality
 * @author Oliver
 *
 * @param <DataType>
 */
public class EqualsSimilarity<DataType> extends SimilarityMeasure<DataType> {

	@Override
	public double calculate(DataType first, DataType second) {
		return first.equals(second) ? 1.0 : 0.0;
	}

}
