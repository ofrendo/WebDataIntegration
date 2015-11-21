package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;

public class UnionTest extends TestCase {

	public void testResolveConflictCollectionOfFusableValueOfListOfValueTypeRecordType() {
		Union<String, FusableMovie> crf = new Union<String, FusableMovie>();
		List<FusableValue<List<String>, FusableMovie>> cluster1 = new ArrayList<FusableValue<List<String>, FusableMovie>>();
		cluster1.add(new FusableValue<List<String>, FusableMovie>(
				new ArrayList<String>(), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(
				new ArrayList<String>(), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(
				new ArrayList<String>(), null, null));
		FusedValue<List<String>, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(0, resolvedValue.getValue().size());
	}

	public void testResolveConflictCollectionOfFusableValueOfListOfValueTypeRecordType1() {
		Union<String, FusableMovie> crf = new Union<String, FusableMovie>();
		List<FusableValue<List<String>, FusableMovie>> cluster1 = new ArrayList<FusableValue<List<String>, FusableMovie>>();
		cluster1.add(new FusableValue<List<String>, FusableMovie>(Arrays
				.asList("h0", "h1"), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(Arrays
				.asList("h0", "h1"), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(Arrays
				.asList("h0", "h1"), null, null));
		FusedValue<List<String>, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(2, resolvedValue.getValue().size());

	}

	public void testResolveConflictCollectionOfFusableValueOfListOfValueTypeRecordType2() {
		Union<String, FusableMovie> crf = new Union<String, FusableMovie>();
		List<FusableValue<List<String>, FusableMovie>> cluster1 = new ArrayList<FusableValue<List<String>, FusableMovie>>();
		cluster1.add(new FusableValue<List<String>, FusableMovie>(new ArrayList<String>(), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(Arrays
				.asList("h0", "h1"), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(Arrays
				.asList("h2", "h1"), null, null));
		FusedValue<List<String>, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(3, resolvedValue.getValue().size());

	}
}
