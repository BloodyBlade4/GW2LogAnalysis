package json_Parsing;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import settings.SettingsCategories;

public class JSONUtility {
	static final Logger logger = Logger.getLogger(JSONParsing.class);
	
	//Write information from SettingsCategory
	//TODO: Clean up this recursion mess. 
	public static String categorySettingsWriting(SettingsCategories cat, JSONObject obj) {
		if (cat.getIsArray()) {
			if(cat.getInnerCat() != null) {
				return categorySettingsWriting(cat.getInnerCat(), getJsonObjFromArrayInObj(obj, cat.getObjectName()));
				
			}
			return (getStringsFromObj(getJsonObjFromArrayInObj(obj, cat.getObjectName()), cat.retrieveActiveListStats()));
		}
		else if(cat.getInnerCat() != null) {
			return categorySettingsWriting(cat.getInnerCat(), (JSONObject)obj.get(cat.getObjectName()));
		}
		else {
			return(getStringsFromObj(obj, cat.retrieveActiveListStats()));
		}
	}
	
	//creates a comma separated string based on values from a JSON object whose key is contained in the given list. Only values immediately accessible. 
	public static String getStringsFromObj(JSONObject obj, List<String> list) {
		if (list == null || list.isEmpty())
			return "";
		String result = "";
		
		for (String s : list) {
			result += jsonObjGetString(obj, s) + ",";
		}
		return result + ",";
	}
	
	
	
	
	//Confusing, but this occurs a lot. 
	public static JSONObject getJsonObjFromArrayInObj(JSONObject object, String item) {
		/*
		 * A lot of values are stored in an array, inside an object. This will access the first array and use that as it's own object. 
		 * e.g. accessing the desired info from "allStats": getStringFromJSONArray(player, "allStats", allStats)
		 * {
		 * 		"allStats" {
		 * 			[
		 * 				{Desired info. This is the first object in the array.}
		 * 			]
		 * 		}
		 * }
		 */
		if (object.containsKey(item)) {
			Object o = object.get(item);
			if(o instanceof JSONArray) {
				return (JSONObject) ((JSONArray) o).get(0);
			}
		}
		logger.error("Could not find the JSON object inside the array \"" + item + "\".");
		return null;
	}
	
	public static int jsonObjGetint(JSONObject obj, String item) {
		if (obj.containsKey(item)) {
			Object o = obj.get(item);
		    if (o instanceof Long) {
		    	return ((Long) o).intValue();
		    }
		    if (o instanceof Double) {
		    	return ((Double) o).intValue();
		    }
		}
		logger.warn("Cannot find the item key \"" + item + "\" inside jsonObject.");
		return -1;
	}
	public static double jsonObjGetDouble(JSONObject obj, String item) {
		if (obj.containsKey(item)) {
			Object o = obj.get(item);
		    if (o instanceof Long) {
		    	return ((Long) o).doubleValue();
		    }
		    if (o instanceof Double) {
		    	return (double) o;
		    }
		}
		logger.warn("Cannot find the item key \"" + item + "\" inside jsonObject.");
		return (double) -1;
	}
	
	public static String jsonObjGetString(JSONObject obj, String item) {
		if (obj.containsKey(item)) {
			Object o = obj.get(item);
		    if (o != null) {
		    	return String.valueOf(o);
		    }
		}
		logger.warn("Cannot find the item key \"" + item + "\" inside jsonObject.");
		return "NA";
	}
}
