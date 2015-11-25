package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;

public class RevenueFuser extends AttributeValueFuser<Long, FusableCompany> {

	public RevenueFuser() {
		super(new FavourSources<Long,FusableCompany>());
	}

	@Override
	protected Long getValue(FusableCompany record) {
		return record.getRevenue();
	}

	@Override
	public void fuse(RecordGroup<FusableCompany> group, FusableCompany fusedRecord) {
		// get the fused value
		FusedValue<Long, FusableCompany> fused = getFusedValue(group);
		
		//System.out.println("RevenueFuser: " + fused.getValue() + fused.getOriginalIds());
		
		Long result = fused.getValue();
		if (result == null)
			result = (long) 0;
		
		// set the value for the fused record
		fusedRecord.setRevenue(result);
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.REVENUE, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.REVENUE);
	}

}
