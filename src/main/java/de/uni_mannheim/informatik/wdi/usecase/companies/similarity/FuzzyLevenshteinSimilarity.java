package de.uni_mannheim.informatik.wdi.usecase.companies.similarity;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.wdi.identityresolution.similarity.SimilarityMeasure;

public class FuzzyLevenshteinSimilarity extends SimilarityMeasure<String> {

	@Override
	public double calculate(String value1, String value2) {
		String[] arr1 = value1.toLowerCase().split(";;");
		String[] arr2 = value2.toLowerCase().split(";;");
		
		if (arr1.length > arr2.length) { //arr1 needs to be the shorter array
			String[] temp = arr2;
			arr2 = arr1;
			arr1 = temp;
		}
		
		double intersectionNum = 0;
		for(String s1 : arr1){
			double highestSim = 0;
			for(String s2 : arr2){
				double current = compareByLevenshtein(s1, s2);
				highestSim = (highestSim < current) ? current : highestSim;
				
				//if(compareByLevenshtein(s1,s2) >= 0.7)
					//intersectionNum++;
			}
			intersectionNum += highestSim;
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
