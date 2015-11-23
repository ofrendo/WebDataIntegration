package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution.Max;

public class NumberOfEmployeesFuser extends AttributeValueFuser<Double, FusableCompany> {
	
	public NumberOfEmployeesFuser() {
		super(new Max<FusableCompany>());
	}

	@Override
	protected Double getValue(FusableCompany record) {
		return (double) record.getNumberOfEmployees();
	}

	@Override
	public void fuse(RecordGroup<FusableCompany> group, FusableCompany fusedRecord) {
		// get the fused value
		FusedValue<Double, FusableCompany> fused = getFusedValue(group);
		
		//System.out.println("RevenueFuser: " + fused.getValue() + fused.getOriginalIds());
		
		Double result = fused.getValue();
		if (result == null)
			result = (double) 0;
		
		// set the value for the fused record
		fusedRecord.setNumberOfEmployees(result.intValue());
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.NUMBER_OF_EMPLOYEES, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.NUMBER_OF_EMPLOYEES);
	}
}
