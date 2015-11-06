package de.uni_mannheim.informatik.wdi.identityresolution.blocking;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.xml.sax.SAXException;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.GoldStandard;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;
import de.uni_mannheim.informatik.wdi.usecase.movies.MovieBlockingFunction;
import de.uni_mannheim.informatik.wdi.usecase.movies.Movie;

public class SortedNeighbourhoodBlockerTest extends TestCase {

	private DataSet<Movie> generateDS1() {
		DataSet<Movie> ds = new DataSet<>();
		Movie m1 = new Movie("1", "DS1");
		m1.setDate(DateTime.parse("1980-10-10"));
		ds.addRecord(m1);
		Movie m2 = new Movie("2", "DS1");
		m2.setDate(DateTime.parse("1990-10-10"));
		ds.addRecord(m2);
		Movie m3 = new Movie("3", "DS1");
		m3.setDate(DateTime.parse("1991-10-10"));
		ds.addRecord(m3);
		return ds;
	}

	private DataSet<Movie> generateDS2() {
		DataSet<Movie> ds = new DataSet<>();
		Movie m1 = new Movie("4", "DS2");
		m1.setDate(DateTime.parse("1983-10-10"));
		ds.addRecord(m1);
		Movie m2 = new Movie("5", "DS2");
		m2.setDate(DateTime.parse("1984-10-10"));
		ds.addRecord(m2);
		Movie m3 = new Movie("6", "DS2");
		m3.setDate(DateTime.parse("1995-10-10"));
		ds.addRecord(m3);
		return ds;
	}

	public void testGeneratePairs() throws XPathExpressionException,
			ParserConfigurationException, SAXException, IOException {
		DataSet<Movie> ds = generateDS1();

		DataSet<Movie> ds2 = generateDS2();

		Blocker<Movie> blocker = new SortedNeighbourhoodBlocker<>(
				new MovieBlockingFunction(), 3);

		GoldStandard gs = new GoldStandard();
		gs.loadFromCSVFile(new File(
				"usecase/movie/goldstandard/gs_academy_awards_2_actors.csv"));

		List<Pair<Movie, Movie>> pairs = blocker.generatePairs(ds,
				ds2);

		System.out.println("Pairs: " + pairs.size());
		System.out.println("Reduction Rate: " + blocker.getReductionRatio());
		
		for (Pair<Movie,Movie> p : pairs) {
			System.out.println(p.getFirst().getIdentifier() + " | " + p.getSecond().getIdentifier());
		}
		assertEquals(8, pairs.size());
	}

}
