package de.uni_mannheim.informatik.wdi.identityresolution.model;

/**
 * A pair of two objects.
 * @author Oliver
 *
 * @param <T>
 * @param <U>
 */
public class Pair<T,U> {

	private T first;
	private U second;
	
	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}
	
	public T getFirst() {
		return first;
	}
	
	public U getSecond() {
		return second;
	}
	
}
