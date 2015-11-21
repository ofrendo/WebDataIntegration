package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.meta;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import de.uni_mannheim.informatik.wdi.datafusion.FusableDataSet;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;

public class FavourSourcesTest extends TestCase {

	public void testResolveConflict() {

		FavourSources<Double, FusableMovie> crf = new FavourSources<Double, FusableMovie>();
		List<FusableValue<Double, FusableMovie>> cluster1 = new ArrayList<FusableValue<Double, FusableMovie>>();
		FusableDataSet<FusableMovie> ds1 = new FusableDataSet<>();
		ds1.setScore(1.0);
		cluster1.add(new FusableValue<Double, FusableMovie>(1.0, null, ds1));
		FusableDataSet<FusableMovie> ds2 = new FusableDataSet<>();
		ds2.setScore(0.5);
		cluster1.add(new FusableValue<Double, FusableMovie>(2.0, null, ds2));
		FusableDataSet<FusableMovie> ds3 = new FusableDataSet<>();
		ds3.setScore(0.1);
		cluster1.add(new FusableValue<Double, FusableMovie>(3.0, null, ds3));
		FusedValue<Double, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(1.0, resolvedValue.getValue());

	}

	public void testResolveConflict1() {

		FavourSources<Double, FusableMovie> crf = new FavourSources<Double, FusableMovie>();
		List<FusableValue<Double, FusableMovie>> cluster1 = new ArrayList<FusableValue<Double, FusableMovie>>();
		FusedValue<Double, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(null, resolvedValue.getValue());

	}
	
	public void testResolveConflict2() {

		FavourSources<Double, FusableMovie> crf = new FavourSources<Double, FusableMovie>();
		List<FusableValue<Double, FusableMovie>> cluster1 = new ArrayList<FusableValue<Double, FusableMovie>>();
		FusableDataSet<FusableMovie> ds1 = new FusableDataSet<>();
		ds1.setScore(1.0);
		cluster1.add(new FusableValue<Double, FusableMovie>(1.0, null, ds1));
		FusableDataSet<FusableMovie> ds2 = new FusableDataSet<>();
		ds2.setScore(0.5);
		cluster1.add(new FusableValue<Double, FusableMovie>(2.0, null, ds2));
		FusableDataSet<FusableMovie> ds3 = new FusableDataSet<>();
		ds3.setScore(10.1);
		cluster1.add(new FusableValue<Double, FusableMovie>(3.0, null, ds3));
		FusedValue<Double, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(3.0, resolvedValue.getValue());

	}

}
