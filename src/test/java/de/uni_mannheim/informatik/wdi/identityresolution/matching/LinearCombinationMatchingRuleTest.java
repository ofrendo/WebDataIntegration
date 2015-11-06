package de.uni_mannheim.informatik.wdi.identityresolution.matching;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.xml.sax.SAXException;

import de.uni_mannheim.informatik.wdi.usecase.movies.Movie;
import de.uni_mannheim.informatik.wdi.usecase.movies.comparators.MovieDateComparator;
import de.uni_mannheim.informatik.wdi.usecase.movies.comparators.MovieDirectorComparatorLevenshtein;
import de.uni_mannheim.informatik.wdi.usecase.movies.comparators.MovieTitleComparator;
import junit.framework.TestCase;

public class LinearCombinationMatchingRuleTest extends TestCase {

	public void testApply() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		Movie movie1 = new Movie("movie1", "test");
		Movie movie2 = new Movie("movie2", "test");
		Movie movie3 = new Movie("movie3", "test");
		
		movie1.setTitle("Star Wars IV");
		movie2.setTitle("Star Wars V");
		movie3.setTitle("Star Wars IV");
		
		movie1.setDirector("George Lucas");
		movie2.setDirector("Irvin Kershner");
		movie3.setDirector("Irvin Kershner");
		
		movie1.setDate(DateTime.parse("1977-05-25"));
		movie2.setDate(DateTime.parse("1980-05-21"));
		movie3.setDate(DateTime.parse("1977-05-25"));
		
		LinearCombinationMatchingRule<Movie> rule1 = new LinearCombinationMatchingRule<>(0.0, 1.0);
		rule1.addComparator(new MovieTitleComparator(), 1.0);
		assertNotNull(rule1.apply(movie1, movie3));
		assertNull(rule1.apply(movie1, movie2));
		
		LinearCombinationMatchingRule<Movie> rule2 = new LinearCombinationMatchingRule<>(0.0, 0.9);
		rule2.addComparator(new MovieTitleComparator(), 0.1);
		rule2.addComparator(new MovieDirectorComparatorLevenshtein(), 0.9);
		assertNotNull(rule2.apply(movie2, movie3));
		assertNull(rule2.apply(movie1, movie2));
		
		LinearCombinationMatchingRule<Movie> rule3 = new LinearCombinationMatchingRule<>(0.0, 0.8);
		rule3.addComparator(new MovieTitleComparator(), 0.1);
		rule3.addComparator(new MovieDirectorComparatorLevenshtein(), 0.1);
		rule3.addComparator(new MovieDateComparator(), 0.8);
		assertNotNull(rule3.apply(movie1, movie3));
		assertNull(rule3.apply(movie2, movie3));		
	}

}
