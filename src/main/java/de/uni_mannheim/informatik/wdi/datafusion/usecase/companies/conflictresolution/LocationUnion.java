package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.usecase.companies.Location;

public class LocationUnion extends ConflictResolutionFunction<List<Location>, FusableCompany> {

	@Override
	public FusedValue<List<Location>, FusableCompany> resolveConflict(
			Collection<FusableValue<List<Location>, FusableCompany>> values) {
		
		ArrayList<Location> resultLocations = new ArrayList<>();
		FusedValue<List<Location>, FusableCompany> fused = new FusedValue<>(resultLocations);
		
		// Strategy is to do a union without any duplicates (on name attribute)
		// If conflicts on name arise: MostComplete
		for (FusableValue<List<Location>, FusableCompany> value : values) {
			for (Location l : value.getValue()) {
				
				Location addThis = l;
				Location alreadyExists = existsInList(l, resultLocations);
				if (alreadyExists != null) {
					if (alreadyExists.getCompleteness() < l.getCompleteness()) {
						//addThis = l;
						resultLocations.remove(alreadyExists);
					}
					else {
						continue; //don't add this location because the one already in list is better/just as good
					}
					
					//resultLocations.remove(l);
				}
				
				if (!resultLocations.contains(addThis)) {
					resultLocations.add(addThis);
					fused.addOriginalRecord(value);
				}
			}
		}
		
		return fused;
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
