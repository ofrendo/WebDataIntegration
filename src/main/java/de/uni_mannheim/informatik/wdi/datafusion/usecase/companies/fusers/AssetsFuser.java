package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution.Max;

public class AssetsFuser extends AttributeValueFuser<Double, FusableCompany> {
	
	public AssetsFuser() {
		//super(new FavourSources<Double,FusableCompany>());
		super(new Max<FusableCompany>());
	}
	
	@Override
	protected Double getValue(FusableCompany record) {
		return (double) record.getAssets();
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
		fusedRecord.setAssets(result.longValue());
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.ASSETS, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.ASSETS);
	}
}
