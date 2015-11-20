package de.uni_mannheim.informatik.wdi.usecase.companies.blocking;

import de.uni_mannheim.informatik.wdi.identityresolution.blocking.BlockingFunction;
import de.uni_mannheim.informatik.wdi.usecase.companies.Company;

public class CompanyCountryBlockingFunction extends BlockingFunction<Company> {

	@Override
	public String getBlockingKey(Company instance) {
		return instance.getCountries() == null ?
				"" : 
				instance.getCountries();
	}

}
