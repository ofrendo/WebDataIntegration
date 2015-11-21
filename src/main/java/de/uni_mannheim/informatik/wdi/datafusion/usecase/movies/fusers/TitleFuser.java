package de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.string.LongestString;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;

public class TitleFuser extends AttributeValueFuser<String, FusableMovie> {

	public TitleFuser() {
		super(new LongestString<FusableMovie>());
	}
	
	@Override
	public void fuse(RecordGroup<FusableMovie> group,
			FusableMovie fusedRecord) {
		
		// get the fused value
		FusedValue<String, FusableMovie> fused = getFusedValue(group);
		
		// set the value for the fused record
		fusedRecord.setTitle(fused.getValue());
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableMovie.TITLE, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(FusableMovie record) {
		return record.hasValue(FusableMovie.TITLE);
	}
	
	@Override
	protected String getValue(FusableMovie record) {
		return record.getTitle();
	}

}
