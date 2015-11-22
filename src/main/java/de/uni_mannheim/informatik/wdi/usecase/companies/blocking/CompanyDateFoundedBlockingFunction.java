package de.uni_mannheim.informatik.wdi.usecase.companies.blocking;

import org.joda.time.DateTime;

import de.uni_mannheim.informatik.wdi.identityresolution.blocking.BlockingFunction;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

public class CompanyDateFoundedBlockingFunction extends BlockingFunction<Company>{

	@Override
	public String getBlockingKey(Company c) {
		DateTime dateFounded = c.getDateFounded();
		
		return dateFounded == null ?
				"" :
				Integer.toString(c.getDateFounded().getYear() / 10);
	}

}
