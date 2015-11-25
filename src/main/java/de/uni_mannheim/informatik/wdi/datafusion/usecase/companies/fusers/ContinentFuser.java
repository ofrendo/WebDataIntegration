package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution.SingleSourceResolution;

public class ContinentFuser extends AttributeValueFuser<String, FusableCompany>  {
	
	public ContinentFuser() {
		super(new SingleSourceResolution<String, FusableCompany>());
	}
	
	@Override
	public void fuse(RecordGroup<FusableCompany> group,
			FusableCompany fusedRecord) {
		
		// get the fused value
		FusedValue<String, FusableCompany> fused = getFusedValue(group);

		// set the value for the fused record
		fusedRecord.setContinent(fused.getValue());
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.CONTINENT, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.CONTINENT);
	}
	
	@Override
	protected String getValue(FusableCompany record) {
		return record.getContinent();
	}
}
