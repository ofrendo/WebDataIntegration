package de.uni_mannheim.informatik.wdi.usecase.companies.similarity;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.wdi.identityresolution.similarity.SimilarityMeasure;

public class FuzzyLevenshteinSimilarity extends SimilarityMeasure<String> {

	@Override
	public double calculate(String value1, String value2) {
		String[] arr1 = value1.toLowerCase().split(";;");
		String[] arr2 = value2.toLowerCase().split(";;");
		double intersectionNum = 0.0;
		for(String s1 : arr1){
			for(String s2 : arr2){
				//if(compareByLevenshtein(s1,s2) >= 0.7)
					//intersectionNum++;
				intersectionNum += compareByLevenshtein(s1, s2);
			}
		}
		
		double similarity = intersectionNum / ((arr1.length+arr2.length)-intersectionNum);
		
		return similarity;
	}
	
	private double compareByLevenshtein(String first, String second){
		double maxLength = first.length() > second.length()? first.length() : second.length();
		if(maxLength == 0.0)
			return 0.0;
		double result = 1 - StringUtils.getLevenshteinDistance(first, second) / maxLength;
		return result;
	}

}
