package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;

public class VotingTest extends TestCase {

	public void testResolveConflict() {
		Voting<Double, FusableMovie> crf = new Voting<Double, FusableMovie>();
		List<FusableValue<Double, FusableMovie>> cluster1 = new ArrayList<FusableValue<Double, FusableMovie>>();
		cluster1.add(new FusableValue<Double, FusableMovie>(1.0, null, null));
		cluster1.add(new FusableValue<Double, FusableMovie>(1.0, null, null));
		cluster1.add(new FusableValue<Double, FusableMovie>(3.0, null, null));
		FusedValue<Double, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(1.0, resolvedValue.getValue());
	}

	public void testResolveConflict2() {
		Voting<Double, FusableMovie> crf = new Voting<Double, FusableMovie>();
		List<FusableValue<Double, FusableMovie>> cluster1 = new ArrayList<FusableValue<Double, FusableMovie>>();
		cluster1.add(new FusableValue<Double, FusableMovie>(1.0, null, null));
		cluster1.add(new FusableValue<Double, FusableMovie>(1.0, null, null));
		cluster1.add(new FusableValue<Double, FusableMovie>(3.0, null, null));
		cluster1.add(new FusableValue<Double, FusableMovie>(3.0, null, null));
		FusedValue<Double, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(3.0, resolvedValue.getValue());
	}
	
	public void testResolveConflict3() {
		Voting<Double, FusableMovie> crf = new Voting<Double, FusableMovie>();
		List<FusableValue<Double, FusableMovie>> cluster1 = new ArrayList<FusableValue<Double, FusableMovie>>();
		FusedValue<Double, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(null, resolvedValue.getValue());
	}

}
