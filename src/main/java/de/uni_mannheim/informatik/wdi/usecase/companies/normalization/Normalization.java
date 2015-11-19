package de.uni_mannheim.informatik.wdi.usecase.companies.normalization;

import java.util.Arrays;

/**
 * Class for normalizing names, countries and values from DBPedia
 * @author D059373
 *
 */
public class Normalization {

	public static String normalizeValueInDBpedia(String value) {
		String[] arr1 = value.split(";;");
		String result = "";
		if(arr1.length > 1){
			for(int i = 0; i < arr1.length; i++){
				String[] temp = arr1[i].split("/");
				result += temp[temp.length-1];
				if(i != arr1.length-1)
					result += ";;";
			}
		}else{
			String[] temp = arr1[0].split("/");
			result = temp[temp.length-1];
		}
		//lastly trim
		result = result.trim();
		return result;
	}

	
	public static String normalizeName(String name) {
		String[] replacements = {
				"'", "\\.", ",",
				"Group", "Corporation", "Company", 
				"Holdings", "Holding", "Inc", "The", 
				"Industries", "International"
		};
		for (String r : replacements) {
			name = name.replaceAll(r, "");
		}
		//special case _ and double spaces
		name = name.replaceAll("_", " ");
		name = name.replaceAll("  ", " ");
		
		//lastly trim
		name = name.trim();
		return name;
	}
	
	
	/**
	 * @param String like England;;United Kingdom;;null
	 * @return Normalized version of String (in this case United Kingdom)
	 */
	public static String normalizeCountries(String countries) {
		if (countries == null || countries.length() == 0) 
			return null;
		
		String[][] mapping = {
				{"United Kingdom", "UK", "England", "Britain"},
				{"United States of America", "US","USA","U.S","U.S.A","United States","America","Contiguous United States"}
		};
		
		//HashMap<String,String> mapping = new HashMap<>();
		String result = "";
		for (String c : countries.split(";;")) { //for each country part in England;;United Kingdom;;null
			String temp = c;
			for (String[] countryMapping : mapping) { //for each country defined in mapping
				if (Arrays.asList(countryMapping).contains(c)) {
					temp = countryMapping[0];
				}
			}
			if (result.indexOf(temp) == -1 && !temp.equals("null")) {
				result += temp + ";;";
			}
		}
		
		if (result.length() == 0) //for strings like null or null;;null
			return null;
		
		//System.out.println(result);
		result = result.substring(0, result.length()-2); //get rid of last two ;;
		return result;
	}
	
	public static long normalizeProfitOrRevenue(String input) {
		if (input == null) 
			return -1;
		
		Double value = Double.valueOf(input);
		if (value < 100) {
			value *= 10^9;
		}
		
		return value.longValue();
	}
	
	
	
	
}
