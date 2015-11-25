package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution.SingleSourceResolution;

public class MarketValueFuser extends AttributeValueFuser<Long, FusableCompany> {
	
	public MarketValueFuser() {
		super(new SingleSourceResolution<Long,FusableCompany>());
	}
	
	@Override
	protected Long getValue(FusableCompany record) {
		return record.getMarketValue();
	}

	@Override
	public void fuse(RecordGroup<FusableCompany> group, FusableCompany fusedRecord) {
		// get the fused value
		FusedValue<Long, FusableCompany> fused = getFusedValue(group);
		
		Long result = fused.getValue();
		if (result == null)
			result = 0L;
		
		// set the value for the fused record
		fusedRecord.setMarketValue(result);
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.MARKET_VALUE, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.MARKET_VALUE);
	}
}
