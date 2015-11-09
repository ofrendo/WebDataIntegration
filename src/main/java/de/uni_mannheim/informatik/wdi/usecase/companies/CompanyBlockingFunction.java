package de.uni_mannheim.informatik.wdi.usecase.companies;

import de.uni_mannheim.informatik.wdi.identityresolution.blocking.BlockingFunction;

public class CompanyBlockingFunction extends BlockingFunction<Company> {

	@Override
	public String getBlockingKey(Company instance) {
		// TODO Auto-generated method stub
		return instance.getCountries();
	}

}
