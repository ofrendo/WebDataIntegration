package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.LevenshteinSimilarity;

public class IntersectionString<RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<String, RecordType> {
	
	private LevenshteinSimilarity sim = new LevenshteinSimilarity();
	
	@Override
	public FusedValue<String, RecordType> resolveConflict(Collection<FusableValue<String, RecordType>> values) {
		//Resulting string is intersection of strings, delimited by ;;

		Set<String> allValues = null;
		for (FusableValue<String, RecordType> value : values) {
			if (value.getDataSourceScore() < 1.0) //Leave out dbpedia locations, which has a score of 0.5
				continue;
			
			String[] parts = value.getValue().split(";;");
			if (allValues == null) {
				allValues = new HashSet<>();
				allValues.addAll(Arrays.asList(parts) );
			}
			else {
				allValues.retainAll(Arrays.asList(parts));
			}
		}
		String result = "";
		if (allValues != null) {
			for (String i : allValues) {
				result += i + ";;";
			}
		}
		
		
		if (result.endsWith(";;"))
			result = result.substring(0, result.length()-2);
		
		FusedValue<String, RecordType> fused = new FusedValue<>(result);

		//Add original values as provenance
		for (FusableValue<String, RecordType> value : values) {
			String industries = value.getValue();
			for (String i : industries.split(";;")) {
				if (result.contains(i)) {
					fused.addOriginalRecord(value);
				}
			}
		}
		
		return fused;
	}
	
	/*private boolean isIndustryContained(String test, String industriesP) {
		String[] industries = industriesP.split(";;");
		for (String i : industries) {
			if (sim.calculate(i, test) > 0.8)
				return true;
		}
		return false;
	}*/
	
}
