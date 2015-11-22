package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution;

import java.util.Collection;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;

public class UnionString<RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<String, RecordType> {

	@Override
	public FusedValue<String, RecordType> resolveConflict(Collection<FusableValue<String, RecordType>> values) {
		//Resulting string is a union of string, delimited by ;;
		String result = "";
		for (FusableValue<String, RecordType> value : values) {
			
			//Only add the value if it doesn't exist already
			String[] parts = value.getValue().split(";;");
			for (String part : parts) {
				if (!result.toLowerCase().contains(part.toLowerCase())) {
					result += part + ";;";
				}
			}
			
		}
		if (result.endsWith(";;"))
			result = result.substring(0, result.length()-2);
		
		FusedValue<String, RecordType> fused = new FusedValue<>(result);

		//Add original values as provenance
		for (FusableValue<String, RecordType> value : values) {
			fused.addOriginalRecord(value);
		}
		
		return fused;
	}

}
