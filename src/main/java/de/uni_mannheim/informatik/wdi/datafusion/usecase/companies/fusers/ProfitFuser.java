package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution.Max;

public class ProfitFuser extends AttributeValueFuser<Double, FusableCompany>  {

	public ProfitFuser() {
		//super(new Max<FusableCompany>());
		super(new FavourSources<>());
	}
	
	@Override
	protected Double getValue(FusableCompany record) {
		return (double) record.getProfit();
	}
	
	@Override
	public void fuse(RecordGroup<FusableCompany> group, FusableCompany fusedRecord) {
		// get the fused value
		FusedValue<Double, FusableCompany> fused = getFusedValue(group);
		
		//System.out.println("RevenueFuser: " + fused.getValue() + fused.getOriginalIds());
		
		Double result = fused.getValue();
		if (result == null)
			result = 0.0;
		
		// set the value for the fused record
		fusedRecord.setProfit(result.longValue());
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.PROFIT, fused.getOriginalIds());
	}
	
	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.PROFIT);
	}
}
