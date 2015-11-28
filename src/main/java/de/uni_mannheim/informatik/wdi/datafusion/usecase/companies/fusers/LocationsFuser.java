package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import java.util.List;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution.LocationIntersection;
import de.uni_mannheim.informatik.wdi.usecase.companies.Location;

public class LocationsFuser extends AttributeValueFuser<List<Location>, FusableCompany>  {
	
	public LocationsFuser() {
		super(new LocationIntersection());
		//super(new LocationUnion());
	}

	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.LOCATIONS);
	}
	
	@Override
	protected List<Location> getValue(FusableCompany record) {
		return record.getLocations();
	}

	@Override
	public void fuse(RecordGroup<FusableCompany> group,
			FusableCompany fusedRecord) {
		FusedValue<List<Location>, FusableCompany> fused = getFusedValue(group);
		fusedRecord.setLocations(fused.getValue());
		fusedRecord.setAttributeProvenance(FusableCompany.LOCATIONS, fused.getOriginalIds());
	}
	
}
