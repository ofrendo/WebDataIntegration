package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.string;

import java.util.ArrayList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;
import junit.framework.TestCase;

public class ShortestStringTest extends TestCase {

	public void testResolveConflictCollectionOfFusableValueOfStringRecordType() {
		ShortestString<FusableMovie> crf = new ShortestString<FusableMovie>();
		List<FusableValue<String, FusableMovie>> cluster1 = new ArrayList<FusableValue<String, FusableMovie>>();
		cluster1.add(new FusableValue<String, FusableMovie>("hello", null, null));
		cluster1.add(new FusableValue<String, FusableMovie>("hello", null, null));
		cluster1.add(new FusableValue<String, FusableMovie>("hello moto", null,
				null));
		FusedValue<String, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals("hello", resolvedValue.getValue());
	}

	public void testResolveConflictCollectionOfFusableValueOfStringRecordType2() {
		ShortestString<FusableMovie> crf = new ShortestString<FusableMovie>();
		List<FusableValue<String, FusableMovie>> cluster1 = new ArrayList<FusableValue<String, FusableMovie>>();
		cluster1.add(new FusableValue<String, FusableMovie>("hello", null, null));
		cluster1.add(new FusableValue<String, FusableMovie>("", null, null));
		cluster1.add(new FusableValue<String, FusableMovie>("hello moto", null,
				null));
		FusedValue<String, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals("", resolvedValue.getValue());
	}

	public void testResolveConflictCollectionOfFusableValueOfStringRecordType3() {
		ShortestString<FusableMovie> crf = new ShortestString<FusableMovie>();
		List<FusableValue<String, FusableMovie>> cluster1 = new ArrayList<FusableValue<String, FusableMovie>>();
		FusedValue<String, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(null, resolvedValue.getValue());
	}

}
