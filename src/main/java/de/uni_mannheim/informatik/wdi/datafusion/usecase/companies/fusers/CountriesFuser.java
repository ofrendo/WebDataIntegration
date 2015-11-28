package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ClusteredVote;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.Voting;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;

public class CountriesFuser extends AttributeValueFuser<String, FusableCompany> {

	public CountriesFuser() {
		super(new Voting<String, FusableCompany>());
	}
	
	@Override
	public void fuse(RecordGroup<FusableCompany> group,
			FusableCompany fusedRecord) {
		
		// get the fused value
		FusedValue<String, FusableCompany> fused = getFusedValue(group);

		// set the value for the fused record
		fusedRecord.setCountries(fused.getValue());
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.COUNTRIES, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.COUNTRIES);
	}
	
	@Override
	protected String getValue(FusableCompany record) {
		return record.getCountries();
	}

}
