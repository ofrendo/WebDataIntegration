package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;

public class IntersectionTest extends TestCase {

	public void testResolveConflictCollectionOfFusableValueOfListOfValueTypeRecordType() {
		Intersection<String, FusableMovie> crf = new Intersection<String, FusableMovie>();
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

	public void testResolveConflictCollectionOfFusableValueOfListOfValueTypeRecordType2() {
		Intersection<String, FusableMovie> crf = new Intersection<String, FusableMovie>();
		List<FusableValue<List<String>, FusableMovie>> cluster1 = new ArrayList<FusableValue<List<String>, FusableMovie>>();
		cluster1.add(new FusableValue<List<String>, FusableMovie>(Arrays
				.asList("h0", "h1"), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(Arrays
				.asList("h1", "h2"), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(Arrays
				.asList("h2", "h3"), null, null));
		FusedValue<List<String>, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(0, resolvedValue.getValue().size());

	}

	public void testResolveConflictCollectionOfFusableValueOfListOfValueTypeRecordType3() {
		Intersection<String, FusableMovie> crf = new Intersection<String, FusableMovie>();
		List<FusableValue<List<String>, FusableMovie>> cluster1 = new ArrayList<FusableValue<List<String>, FusableMovie>>();
		cluster1.add(new FusableValue<List<String>, FusableMovie>(Arrays
				.asList("h0", "h1"), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(Arrays
				.asList("h0", "h1"), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(Arrays
				.asList("h0", "h2"), null, null));
		FusedValue<List<String>, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(1, resolvedValue.getValue().size());

	}

	public void testResolveConflictCollectionOfFusableValueOfListOfValueTypeRecordType4() {
		Intersection<String, FusableMovie> crf = new Intersection<String, FusableMovie>();
		List<FusableValue<List<String>, FusableMovie>> cluster1 = new ArrayList<FusableValue<List<String>, FusableMovie>>();

		ArrayList<String> list = new ArrayList<String>();
		list.add("h1");
		list.add("h2");
		cluster1.add(new FusableValue<List<String>, FusableMovie>(
				new ArrayList<String>(list), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(
				new ArrayList<String>(list), null, null));
		cluster1.add(new FusableValue<List<String>, FusableMovie>(
				new ArrayList<String>(list), null, null));
		FusedValue<List<String>, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(2, resolvedValue.getValue().size());

	}
}
