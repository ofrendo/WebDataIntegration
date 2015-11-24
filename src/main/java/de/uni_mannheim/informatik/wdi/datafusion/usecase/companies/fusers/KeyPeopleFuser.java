package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution.UnionString;

public class KeyPeopleFuser extends AttributeValueFuser<String, FusableCompany>{

	public KeyPeopleFuser() {
		super(new UnionString<FusableCompany>());
	}
	
	@Override
	public void fuse(RecordGroup<FusableCompany> group, FusableCompany fusedRecord) {

		// get the fused value
		FusedValue<String, FusableCompany> fused = getFusedValue(group);
		
		// set the value for the fused record
		fusedRecord.setKeyPeople(fused.getValue());
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.KEY_PEOPLE, fused.getOriginalIds());
		
	}

	@Override
	protected String getValue(FusableCompany record) {
		return record.getKeyPeople();
	}
	
	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.KEY_PEOPLE);
	}
	
}
