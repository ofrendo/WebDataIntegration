package de.uni_mannheim.informatik.wdi.identityresolution.matching;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.identityresolution.blocking.Blocker;
import de.uni_mannheim.informatik.wdi.identityresolution.blocking.CrossProductBlocker;
import de.uni_mannheim.informatik.wdi.identityresolution.evaluation.GoldStandard;
import de.uni_mannheim.informatik.wdi.usecase.movies.MovieFactory;
import de.uni_mannheim.informatik.wdi.usecase.movies.Movie;
import de.uni_mannheim.informatik.wdi.usecase.movies.comparators.MovieDateComparator;
import de.uni_mannheim.informatik.wdi.usecase.movies.comparators.MovieDirectorComparatorLevenshtein;
import de.uni_mannheim.informatik.wdi.usecase.movies.comparators.MovieTitleComparator;
import junit.framework.TestCase;

public class MatchingEngineTest extends TestCase {

	public void testRunMatching() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		DataSet<Movie> ds = new DataSet<>();
		File sourceFile1 = new File("usecase/movie/input/actors.xml");
		ds.loadFromXML(sourceFile1, new MovieFactory(), "/movies/movie");
		
		DataSet<Movie> ds2 = new DataSet<>();
		File sourceFile2 = new File("usecase/movie/input/academy_awards.xml");
		ds2.loadFromXML(sourceFile2, new MovieFactory(), "/movies/movie");
		
		LinearCombinationMatchingRule<Movie> rule = new LinearCombinationMatchingRule<>(0, 0);
		rule.addComparator(new MovieTitleComparator(), 0.5);
		rule.addComparator(new MovieDirectorComparatorLevenshtein(), 0.25);
		rule.addComparator(new MovieDateComparator(), 0.25);
		
		Blocker<Movie> blocker = new CrossProductBlocker<>();
		MatchingEngine<Movie> engine = new MatchingEngine<>(rule, blocker);
		
		engine.runMatching(ds, ds2);
	}

	public void testGenerateFeaturesForOptimisation() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		DataSet<Movie> ds = new DataSet<>();
		File sourceFile1 = new File("usecase/movie/input/actors.xml");
		ds.loadFromXML(sourceFile1, new MovieFactory(), "/movies/movie");
		
		DataSet<Movie> ds2 = new DataSet<>();
		File sourceFile2 = new File("usecase/movie/input/academy_awards.xml");
		ds2.loadFromXML(sourceFile2, new MovieFactory(), "/movies/movie");
		
		LinearCombinationMatchingRule<Movie> rule = new LinearCombinationMatchingRule<>(0, 0);
		rule.addComparator(new MovieTitleComparator(), 0.5);
		rule.addComparator(new MovieDirectorComparatorLevenshtein(), 0.25);
		rule.addComparator(new MovieDateComparator(), 0.25);
		
		Blocker<Movie> blocker = new CrossProductBlocker<>();
		MatchingEngine<Movie> engine = new MatchingEngine<>(rule, blocker);
		
		GoldStandard gs = new GoldStandard();
		gs.loadFromCSVFile(new File(
				"usecase/movie/goldstandard/gs_academy_awards_2_actors.csv"));
		
		engine.generateTrainingDataForLearning(ds, ds2, gs);
	}

}
