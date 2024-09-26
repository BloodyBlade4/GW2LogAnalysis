package json_Parsing.json_Class;

import java.util.List;
import java.util.Map;

import settings.SettingsCategories;

public class JSONParsingStringConstruction {
	public static String categorySettingsGeneralCat(SettingsCategories cat, JSONClass json) {
		String result = "";
		for (String s : cat.retrieveActiveListStats()) {
			switch (s) {
				case "success":
					result += json.getSuccess() + ",";
					break;
				case "durationMS": //This is desired to be shown in SECONDS, so do the conversion.
					String dur = String.valueOf(json.getDurationMS());
					result += dur.substring(0, dur.length()-3) + ",";
					break;
				case "isCM":
					result += json.getIsCM().toString() + ",";
					break;
				case "timeStart":
					result += json.getTimeStart() + ",";
					break;
				case "timeEnd":
					result += json.getTimeEnd() + ",";
					break;
				case "percentTimeAlive": //This needs to be calculated based on the duration and the players DethRecap
					//player didn't die
					if(json.getPlayer().getDeathTime() == 0) {
						result += "100.00%,"; 
						break;
					}

					Double TOD = (double) json.getPlayer().getDeathTime();
					Double duration = (double) json.getDurationMS();
					Double percent = (TOD/duration)*100;
					result += String.valueOf(percent) + "%,";
					System.out.println("time here: " + TOD + ", and " + duration + ", and " + String.valueOf(percent));
					break;
				default:
					result += "NA,";
					
			}
		}
		return result.isEmpty() ? result : result+",";
	}
	
	//There is no longer a reason for settingscategories to be recursive. These items are now stored in an hashmap!
	//TODO: Fix settings categories!
	public static String categorySettingsWriting(SettingsCategories cat, JSONPlayer player) {
		return stringFromListComparison(cat.retrieveActiveListStats() ,player.getCategoryMapByString(cat.getObjectName()));
		/*
		return "THERE SHOULD BE STUFF HERE!,";
		
		if (cat.getIsArray()) {
			if(cat.getInnerCat() != null) {
				//return categorySettingsWriting(cat.getInnerCat(), );//getJsonObjFromArrayInObj(obj, cat.getObjectName()));
				return "innercat...";
			}
			return stringFromListComparison(cat.retrieveActiveListNames() ,player.getCategoryMapByString(cat.getObjectName()));
			//return (getStringsFromObj(getJsonObjFromArrayInObj(obj, cat.getObjectName()), cat.retrieveActiveListStats()));
		}
		else if(cat.getInnerCat() != null) {
			//return categorySettingsWriting(cat.getInnerCat(), (JSONObject)obj.get(cat.getObjectName()));
		}
		else {
			//return(getStringsFromObj(obj, cat.retrieveActiveListStats()));
		}*/
	}
	
	public static String stringFromListComparison(List<String> find, Map<String, Object> list) {
		if (list == null || list.isEmpty())
			return "";
		if (find == null || find.isEmpty())
			return new String(new char[list.size()]).replace("\0", "Not found, ");
		
		String res = "";
		for (String des : find) {
			if (list.containsKey(des)) {
				res += list.get(des).toString() + ",";
			}
		}
		System.out.println("Found a value and res is: " + res);
		return res.isBlank() ? "" : res + ",";
	}


}
