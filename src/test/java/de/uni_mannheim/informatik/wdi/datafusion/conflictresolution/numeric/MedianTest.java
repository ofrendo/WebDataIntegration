package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.numeric;

import java.util.ArrayList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;
import junit.framework.TestCase;

public class MedianTest extends TestCase {

	public void testResolveConflictCollectionOfFusableValueOfDoubleRecordType() {
		Median<FusableMovie> crf = new Median<FusableMovie>();
		List<FusableValue<Double, FusableMovie>> cluster1 = new ArrayList<FusableValue<Double, FusableMovie>>();
		cluster1.add(new FusableValue<Double, FusableMovie>(1.0, null, null));
		cluster1.add(new FusableValue<Double, FusableMovie>(1.0, null, null));
		cluster1.add(new FusableValue<Double, FusableMovie>(3.0, null, null));
		FusedValue<Double, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(1.0, resolvedValue.getValue());

	}

	public void testResolveConflictCollectionOfFusableValueOfDoubleRecordType2() {
		Median<FusableMovie> crf = new Median<FusableMovie>();
		List<FusableValue<Double, FusableMovie>> cluster2 = new ArrayList<FusableValue<Double, FusableMovie>>();
		FusedValue<Double, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster2);
		assertEquals(null, resolvedValue.getValue());
	}

}
