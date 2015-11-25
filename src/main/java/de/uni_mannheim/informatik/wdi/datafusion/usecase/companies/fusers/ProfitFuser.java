package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;

public class ProfitFuser extends AttributeValueFuser<Long, FusableCompany>  {

	public ProfitFuser() {
		super(new FavourSources<>());
	}
	
	@Override
	protected Long getValue(FusableCompany record) {
		return record.getProfit();
	}
	
	@Override
	public void fuse(RecordGroup<FusableCompany> group, FusableCompany fusedRecord) {
		// get the fused value
		FusedValue<Long, FusableCompany> fused = getFusedValue(group);
		
		//System.out.println("RevenueFuser: " + fused.getValue() + fused.getOriginalIds());
		
		Long result = fused.getValue();
		if (result == null)
			result = 0L;
		
		// set the value for the fused record
		fusedRecord.setProfit(result);
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.PROFIT, fused.getOriginalIds());
	}
	
	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.PROFIT);
	}
}
