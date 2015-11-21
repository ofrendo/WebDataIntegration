package de.uni_mannheim.informatik.wdi.datafusion.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import de.uni_mannheim.informatik.wdi.datafusion.Triple;

/**
 * implements the CENTER clustering algorithm, which finds star-shaped clusters in an undirected, weighted graph
 * @author Oliver
 *
 */
public class CentreClusterer<T> {

	/**
	 * applies the clustering algorithm and returns a map of centre -> cluster
	 */
	public Map<T, Collection<T>> cluster(Collection<Triple<T, T, Double>> similarityGraph) {
		
		ArrayList<Triple<T, T, Double>> sorted = new ArrayList<>(similarityGraph);
		
		Collections.sort(sorted, new Comparator<Triple<T, T, Double>>() {

			@Override
			public int compare(Triple<T, T, Double> o1, Triple<T, T, Double> o2) {
				return -Double.compare(o1.getThird(), o2.getThird());
			}
			
		});
		
		HashSet<T> assignedNodes = new HashSet<>();
		Map<T,Collection<T>> clusters = new HashMap<>(); 
		
        for(Triple<T, T, Double> trip : sorted) {
        	
            if(!assignedNodes.contains(trip.getFirst()) && !assignedNodes.contains(trip.getSecond())) {
            	
            	// create a new cluster
            	Collection<T> clu = new LinkedList<>();
            	clu.add(trip.getFirst());
            	clu.add(trip.getSecond());
            	clusters.put(trip.getFirst(), clu);
            	
                assignedNodes.add(trip.getFirst());
                assignedNodes.add(trip.getSecond());
            } else {
            	
            	if(!assignedNodes.contains(trip.getFirst())) {
            		// only first is unassigned, add it to the cluster if second is a centre
            		Collection<T> clu = clusters.get(trip.getSecond());
            		
            		if(clu!=null) {
            			clu.add(trip.getFirst());
            			assignedNodes.add(trip.getFirst());
            		}
            	} else if(!assignedNodes.contains(trip.getSecond())) {
            		// only second is unassigned, add it to the cluster if first is a centre
        			Collection<T> clu = clusters.get(trip.getFirst());
            		
            		if(clu!=null) {
            			clu.add(trip.getSecond());
            			assignedNodes.add(trip.getSecond());
            		}
            	}
            	
            }
        }
		
        return clusters;
	}
	
}
