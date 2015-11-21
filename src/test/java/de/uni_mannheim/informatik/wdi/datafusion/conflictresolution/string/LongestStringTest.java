package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.string;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;

public class LongestStringTest extends TestCase {

	public void testResolveConflictCollectionOfFusableValueOfStringRecordType() {
		LongestString<FusableMovie> crf = new LongestString<FusableMovie>();
		List<FusableValue<String, FusableMovie>> cluster1 = new ArrayList<FusableValue<String, FusableMovie>>();
		cluster1.add(new FusableValue<String, FusableMovie>("hello", null, null));
		cluster1.add(new FusableValue<String, FusableMovie>("hello", null, null));
		cluster1.add(new FusableValue<String, FusableMovie>("hello moto", null,
				null));
		FusedValue<String, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals("hello moto", resolvedValue.getValue());

	}

	public void testResolveConflictCollectionOfFusableValueOfStringRecordType2() {
		LongestString<FusableMovie> crf = new LongestString<FusableMovie>();
		List<FusableValue<String, FusableMovie>> cluster2 = new ArrayList<FusableValue<String, FusableMovie>>();
		FusedValue<String, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster2);
		assertEquals(null, resolvedValue.getValue());

	}

}
