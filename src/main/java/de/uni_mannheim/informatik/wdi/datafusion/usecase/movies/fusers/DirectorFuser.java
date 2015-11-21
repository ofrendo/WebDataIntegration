package de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;

public class DirectorFuser extends AttributeValueFuser<String, FusableMovie> {

	public DirectorFuser() {
		super(new FavourSources<String, FusableMovie>());
	}
	
	@Override
	public boolean hasValue(FusableMovie record) {
		return record.hasValue(FusableMovie.DIRECTOR);
	}
	
	@Override
	protected String getValue(FusableMovie record) {
		return record.getDirector();
	}

	@Override
	public void fuse(RecordGroup<FusableMovie> group,
			FusableMovie fusedRecord) {
		FusedValue<String, FusableMovie> fused = getFusedValue(group);
		fusedRecord.setDirector(fused.getValue());
		fusedRecord.setAttributeProvenance(FusableMovie.DIRECTOR, fused.getOriginalIds());
	}

}
