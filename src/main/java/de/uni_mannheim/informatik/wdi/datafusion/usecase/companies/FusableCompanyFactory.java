package de.uni_mannheim.informatik.wdi.datafusion.usecase.companies;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.wdi.MatchableFactory;
import de.uni_mannheim.informatik.wdi.datafusion.FusableFactory;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.usecase.companies.CompanyFactory;

public class FusableCompanyFactory extends MatchableFactory<FusableCompany> implements FusableFactory<FusableCompany> {
	
	private CompanyFactory companyFactory = new CompanyFactory(null, null, null);
	
	
	@Override
	public FusableCompany createModelFromElement(Node node, String provenanceInfo) {
		return (FusableCompany) companyFactory.createModelFromElement(node, provenanceInfo);
	}
	
	@Override
	public FusableCompany createInstanceForFusion(RecordGroup<FusableCompany> cluster) {
		List<String> ids = new LinkedList<>();
		
		for(FusableCompany c : cluster.getRecords()) {
			ids.add(c.getIdentifier());
		}
		
		Collections.sort(ids);
		
		String mergedId = StringUtils.join(ids, '+');
		
		return new FusableCompany(mergedId, "fused");
	}

	

}
