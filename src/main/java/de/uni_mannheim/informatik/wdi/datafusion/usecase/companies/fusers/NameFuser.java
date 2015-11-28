package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.Voting;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.string.LongestString;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;

public class NameFuser extends AttributeValueFuser<String, FusableCompany> {
	
	public NameFuser() {
		super(new FavourSources<>());
		//super(new Voting<>());
		//super(new LongestString<FusableCompany>());
	}
	
	@Override
	public void fuse(RecordGroup<FusableCompany> group,
			FusableCompany fusedRecord) {
		
		// get the fused value
		FusedValue<String, FusableCompany> fused = getFusedValue(group);
		
		// set the value for the fused record
		fusedRecord.setName(fused.getValue());
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.NAME, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.NAME);
	}
	
	@Override
	protected String getValue(FusableCompany record) {
		return record.getName();
	}
}
