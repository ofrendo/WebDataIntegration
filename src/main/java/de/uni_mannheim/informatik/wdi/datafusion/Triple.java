package de.uni_mannheim.informatik.wdi.datafusion;

/**
 * A class for wrapping three objects of arbitrary type
 * @author Oliver
 *
 * @param <TFirst>
 * @param <TSecond>
 * @param <TThird>
 */
public class Triple<TFirst, TSecond, TThird> {

	private TFirst first;
	private TSecond second;
	private TThird third;
	
	/**
	 * Creates a new triple with the specified objects
	 * @param first
	 * @param second
	 * @param third
	 */
	public Triple(TFirst first, TSecond second, TThird third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	/**
	 * Returns the first object
	 * @return
	 */
	public TFirst getFirst() {
		return first;
	}
	/**
	 * Sets the first object
	 * @param first
	 */
	public void setFirst(TFirst first) {
		this.first = first;
	}
	
	/**
	 * Returns the second object
	 * @return
	 */
	public TSecond getSecond() {
		return second;
	}
	/**
	 * Sets the second object
	 * @param second
	 */
	public void setSecond(TSecond second) {
		this.second = second;
	}
	
	/**
	 * Returns the third object
	 * @return
	 */
	public TThird getThird() {
		return third;
	}
	/**
	 * Sets the third object
	 * @param third
	 */
	public void setThird(TThird third) {
		this.third = third;
	}
}
