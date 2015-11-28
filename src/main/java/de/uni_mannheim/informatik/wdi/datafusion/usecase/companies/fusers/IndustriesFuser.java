package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution.UnionString;

public class IndustriesFuser extends AttributeValueFuser<String, FusableCompany> {

	public IndustriesFuser() {
		//super(new IntersectionString<FusableCompany>());
		super(new UnionString<FusableCompany>());
	}


	@Override
	public void fuse(RecordGroup<FusableCompany> group, FusableCompany fusedRecord) {

		// get the fused value
		FusedValue<String, FusableCompany> fused = getFusedValue(group);
		
		// set the value for the fused record
		fusedRecord.setIndustries(fused.getValue());
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.INDUSTRIES, fused.getOriginalIds());
		
	}

	@Override
	protected String getValue(FusableCompany record) {
		return record.getIndustries();
	}
	
	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.INDUSTRIES);
	}

}
