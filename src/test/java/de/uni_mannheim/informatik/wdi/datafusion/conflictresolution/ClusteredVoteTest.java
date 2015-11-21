package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.LevenshteinSimilarity;

public class ClusteredVoteTest extends TestCase {

	public void testResolveConflict() {
		ClusteredVote<String, FusableMovie> crf = new ClusteredVote<String, FusableMovie>(
				new LevenshteinSimilarity(), 0.0);

		List<FusableValue<String, FusableMovie>> cluster1 = new ArrayList<FusableValue<String, FusableMovie>>();
		cluster1.add(new FusableValue<String, FusableMovie>("hi", null, null));
		cluster1.add(new FusableValue<String, FusableMovie>("hi1", null, null));
		cluster1.add(new FusableValue<String, FusableMovie>("hello1", null,
				null));
		cluster1.add(new FusableValue<String, FusableMovie>("hello", null, null));
		cluster1.add(new FusableValue<String, FusableMovie>("hello2", null,
				null));

		FusedValue<String, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals("hello1", resolvedValue.getValue());
	}

	public void testResolveConflict1() {
		ClusteredVote<String, FusableMovie> crf = new ClusteredVote<String, FusableMovie>(
				new LevenshteinSimilarity(), 0.0);

		List<FusableValue<String, FusableMovie>> cluster1 = new ArrayList<FusableValue<String, FusableMovie>>();

		FusedValue<String, FusableMovie> resolvedValue = crf
				.resolveConflict(cluster1);
		assertEquals(null, resolvedValue.getValue());
	}
}
