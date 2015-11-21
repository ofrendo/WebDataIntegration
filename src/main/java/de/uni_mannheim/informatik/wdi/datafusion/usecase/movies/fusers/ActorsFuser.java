package de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.fusers;

import java.util.List;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.list.Union;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.Actor;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;

public class ActorsFuser extends AttributeValueFuser<List<Actor>, FusableMovie> {
	
	public ActorsFuser() {
		super(new Union<Actor, FusableMovie>());
	}
	
	@Override
	public boolean hasValue(FusableMovie record) {
		return record.hasValue(FusableMovie.ACTORS);
	}
	
	@Override
	protected List<Actor> getValue(FusableMovie record) {
		return record.getActors();
	}

	@Override
	public void fuse(RecordGroup<FusableMovie> group,
			FusableMovie fusedRecord) {
		FusedValue<List<Actor>, FusableMovie> fused = getFusedValue(group);
		fusedRecord.setActors(fused.getValue());
		fusedRecord.setAttributeProvenance(FusableMovie.ACTORS, fused.getOriginalIds());
	}

}
