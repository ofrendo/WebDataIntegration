package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.fusers;

import org.joda.time.DateTime;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.FusableCompany;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.companies.conflictresolution.DateResolutionCombination;

public class DateFoundedFuser extends AttributeValueFuser<DateTime, FusableCompany>{
	
	public DateFoundedFuser() {
		//super(new DateResolutionMostCompleteDate());
		//super(new DateResolutionMostCompleteRecord());
		super(new DateResolutionCombination());
	}
	
	@Override
	public void fuse(RecordGroup<FusableCompany> group,
			FusableCompany fusedRecord) {
		
		// get the fused value
		FusedValue<DateTime, FusableCompany> fused = getFusedValue(group);

		// set the value for the fused record
		fusedRecord.setDateFounded(fused.getValue());
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCompany.DATE_FOUNDED, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(FusableCompany record) {
		return record.hasValue(FusableCompany.DATE_FOUNDED);
	}
	
	@Override
	protected DateTime getValue(FusableCompany record) {
		return record.getDateFounded();
	}
	
}
