package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.usecase.companies.Location;

public class LocationIntersection extends ConflictResolutionFunction<List<Location>, FusableCompany>  {

	@Override
	public FusedValue<List<Location>, FusableCompany> resolveConflict(
			Collection<FusableValue<List<Location>, FusableCompany>> values) {
		
		ArrayList<Location> resultLocations = null;
		
		
		// Strategy is to do a intersection (on name attribute)
		// If conflicts on name arise: MostComplete
		
		for (FusableValue<List<Location>, FusableCompany> value : values) {
			if (resultLocations == null) {
				resultLocations = new ArrayList<Location>();
				resultLocations.addAll(value.getValue());
			}
			
			resultLocations = getIntersection(resultLocations, value.getValue());			
		}
		
		FusedValue<List<Location>, FusableCompany> fused = new FusedValue<>(resultLocations);
		
		for (Location l : resultLocations) {
			for (FusableValue<List<Location>, FusableCompany> value : values) {
				if (value.getValue().contains(l)) {
					fused.addOriginalRecord(value);
				}
			}
		}
		
		return fused;
	}
	
	private ArrayList<Location> getIntersection(List<Location> l1s, List<Location> l2s) {
		ArrayList<Location> result = new ArrayList<Location>();
		for (Location l1 : l1s) {
			for (Location l2 : l2s) {
				if (l1.getName().equals(l2.getName())) {
					Location addThis = l1.getCompleteness() > l2.getCompleteness() ?
							l1 : l2;
					result.add(addThis);
				}
			}
		}
		return result;
	}
	
	private Location existsInList(Location test, List<Location> list) {
		for (Location l : list) {
			if (l.getName().equals(test.getName())) {
				return l;
			}
		}
		return null;
	}

}
