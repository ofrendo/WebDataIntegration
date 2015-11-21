package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.numeric;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;

public class AverageTest extends TestCase {

	public void testResolveConflictCollectionOfFusableValueOfDoubleRecordType() {

		Average<FusableMovie> crf = new Average<FusableMovie>();
		List<FusableValue<Double, FusableMovie>> cluster1 = new ArrayList<FusableValue<Double, FusableMovie>>();
		cluster1.add(new FusableValue<Double, FusableMovie>(1.0, null, null));
		cluster1.add(new FusableValue<Double, FusableMovie>(2.0, null, null));
		cluster1.add(new FusableValue<Double, FusableMovie>(3.0, null, null));
		FusedValue<Double, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(2.0, resolvedValue.getValue());
	}

	public void testResolveConflictCollectionOfFusableValueOfDoubleRecordType2() {

		Average<FusableMovie> crf = new Average<FusableMovie>();
		List<FusableValue<Double, FusableMovie>> cluster2 = new ArrayList<FusableValue<Double, FusableMovie>>();
		FusedValue<Double, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster2);
		assertEquals(null, resolvedValue.getValue());
	}

}
