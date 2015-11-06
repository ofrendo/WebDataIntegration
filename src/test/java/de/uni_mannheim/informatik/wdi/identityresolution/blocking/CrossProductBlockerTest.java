package de.uni_mannheim.informatik.wdi.identityresolution.blocking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.GoldStandard;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.MatchingEvaluator;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.Performance;
import de.uni_mannheim.informatik.wdi.identityresolution.matching.Correspondence;
import de.uni_mannheim.informatik.wdi.identityresolution.model.Pair;
import de.uni_mannheim.informatik.wdi.usecase.movies.MovieFactory;
import de.uni_mannheim.informatik.wdi.usecase.movies.Movie;
import junit.framework.TestCase;

public class CrossProductBlockerTest extends TestCase {

	public void testGeneratePairs() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		DataSet<Movie> ds = new DataSet<>();
		File sourceFile1 = new File("usecase/movie/input/actors.xml");
		ds.loadFromXML(sourceFile1, new MovieFactory(), "/movies/movie");
		
		DataSet<Movie> ds2 = new DataSet<>();
		File sourceFile2 = new File("usecase/movie/input/academy_awards.xml");
		ds2.loadFromXML(sourceFile2, new MovieFactory(), "/movies/movie");
		
		Blocker<Movie> blocker = new CrossProductBlocker<>();
		
		GoldStandard gs = new GoldStandard();
		gs.loadFromCSVFile(new File(
				"usecase/movie/goldstandard/gs_academy_awards_2_actors.csv"));
		
		List<Pair<Movie, Movie>> pairs = blocker.generatePairs(ds, ds2);
		List<Correspondence<Movie>> correspondences = new ArrayList<>(pairs.size());
		
		// transform pairs into correspondences
		for(Pair<Movie, Movie> p : pairs) {
			correspondences.add(new Correspondence<Movie>(p.getFirst(), p.getSecond(), 1.0));
		}
		
		// check if all examples from the gold standard were in the pairs
		MatchingEvaluator<Movie> eval = new MatchingEvaluator<>(true);
		
		Performance perf = eval.evaluateMatching(correspondences, gs);
		
		assertEquals(1.0, perf.getRecall());
	}

}
