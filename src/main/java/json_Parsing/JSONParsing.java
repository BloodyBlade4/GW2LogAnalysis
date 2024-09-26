package json_Parsing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import components.Buff;
import components.Constants;
import components.DialogHelper;
import settings.Settings;
import settings.SettingsBuff;
import settings.SettingsCategories;

/*
 * A class created for each csv file you're creating. ALL writing to file done here.
 */

public class JSONParsing {
	static final Logger logger = Logger.getLogger(JSONParsing.class);
	private BufferedWriter bw;
	private Map<String, List<String>> buffsMap; //Filled when generating headers. 
	private Properties prop;
	public int headerRow;
	
	//Constructor
	public JSONParsing(String fileName, Settings s) throws Exception{
		logger.info("Initializing JSON parsing class.");
		FileWriter file;
		int tryCount = 0;
		while (true) {
			try {
				System.out.println("Trying to open file");
				file = new FileWriter(fileName);
				bw = new BufferedWriter(file);
				break;
			} catch (IOException e) {
				if(tryCount++ > 5) {
					DialogHelper.errorMessage("Runtime Error", "Error creating File Writer: " + e.getMessage() + ".\n This issue appears to continue to persist. Ending the parsing process.");
					throw new Exception(e.getMessage());
				}
				DialogHelper.errorTryAgainMessage("Runtime Error", "Error creating File Writer: " + e.getMessage() +". \nPress \"Yes\" to try creating the file again. Else, exit or click \"No\".");
			}
			catch (Exception e2) {
	        	DialogHelper.errorMessage("Runtime Error", "Unhandled error while creating File Writer: " + e2.getMessage() + ".\n Ending the parsing process.");
				throw new Exception(e2.getMessage());
	        }
		}
		headerRow = 1;
		writeOverview(s);
		buffsMap = new HashMap<String, List<String>>();

		//try (InputStream input = new FileInputStream(System.getProperty("user.dir") +"\\src\\resources\\constBuffIDs.properties")) {
		try (InputStream input = Constants.class.getResourceAsStream("/constBuffIDs.properties")) {
            prop = new Properties();
            prop.load(input);
		}
	    catch (IOException ex) { //should this be fatal?
	        ex.printStackTrace();
	        DialogHelper.errorMessage("Runtime Error", "IOException reading ID properties file: " + ex);
	    }
		
		writeHeaders(s);
	}
	
	//Writes to the top of the csv file, including details such as settings, character, profession, etc. 
	private void writeOverview(Settings s) {
		String result = 
				"Character Name:, " + s.getCharName();
		if(!s.getProfession().equals("Any")) {
			headerRow++;
			result += "\nSpecialization:, " + s.getSpecialization();
		}

		if (s.getEncounterID() != null && s.getEncounterID() != 0) {
			headerRow++;
			result += "\nLocation:, " + Constants.ENCOUNTER_IDS.get(s.getLocation()).get(s.getEncounterID());
		}
		
		writeString(result);
		headerRow += 2;
		writeNewLine();
		writeNewLine();
	}
	
	private static String[] headingForDefaultInformation(Settings s) {
		String sectioning = "";
		String res = "";
		//Location
		if(s.getEncounterID() == null || s.getEncounterID() == 0) {
			sectioning += "Default info, ";
			res += "Fight Name, ";
		}
		if(s.getEmbbedHTML()) {
			sectioning += ",";
			res += "Embedded HTML, ";
		}
		if(s.getProfession().equals("Any")) {
			sectioning += ", ";
			res += "Specialization, ";
		}
		sectioning += ", ";
		res += ", ";
		
		return new String[] {sectioning, res};
	}
	
	private void writeDefaultInformation(JSONObject obj, JSONObject player, String htmlFilePath, Settings s) {
		//Location, Date	
		String res = "";
		if(s.getEncounterID() == null || s.getEncounterID() == 0) {
			res += JSONUtility.jsonObjGetString(obj, "fightName") + ",";
		}
		if(s.getEmbbedHTML()) {
			res += htmlFilePath + ",";
		}
		
		if(s.getProfession().equals("Any")) {
			res += JSONUtility.jsonObjGetString(player, "profession") +",";
		}
		//res += ", ";
		//Too many commas?
		if (!res.isEmpty())
			writeString(res);
	}
	
	//Writes the headings for all the columns/tables.
	private void writeHeaders(Settings s) {
		String sectioning = "";
		String result = "";
		
		String[] gen = headingForDefaultInformation(s);
		sectioning += gen[0];
		result += gen[1];
		
		for (SettingsCategories cat : s.getCategories()) {
			String[] catStr = headingFromSettingsCategories(cat);
			sectioning += catStr[0];
			result+= catStr[1];
		}
		
		for (SettingsBuff buff : s.getBuffsList()) {
			String[] res = headingFromSettingsBuff(buff);
			sectioning += res[0];
			result += res[1];
		}
		
		String[] prof = headingFromSettingsBuff(s.getProfessionBuffsSettings());
		sectioning += prof[0];
		result += prof[1];
		
		if(s.getEffectsEnabled()) {
			sectioning += "Active effects, ,";
			result += "Effects";
		}
		
		//sectioning? spaces between different sections?
		writeString(sectioning);
		writeNewLine();
		writeString(result);
		writeNewLine();
	}
	
	private String buffsListAppend(List<String> list, String str) {
		String res = (list.stream().map(item -> item + ' ' + str).collect(Collectors.toList())).toString();
		return res.substring(1,res.length()-1) + ", , "; //Remove brackets at beginning and end. 
	}
	
	//Creates the heading string for any SettingsBuff class, also fills the buffMap hashMap with what phases each settingsBuff needs. 
	private String[] headingFromSettingsBuff(SettingsBuff s) {
		int count = s.retrieveActiveBuffNamesList().size() + 1;
		if (count == 1)
			return new String[] {"",""};
		List<String> displays = new ArrayList<String>();
		
		String sectioning = "";
		String res ="";
		
		String name = s.getDisplayName();
		List<String> buffs = s.retrieveActiveBuffNamesList();
		
		if (Boolean.TRUE.equals(s.getPhaseDuration())) {
			if (Boolean.TRUE.equals(s.getUptime()) || s.getUptime() == null) {
				sectioning += "Uptime " + name + new String(new char[count]).replace("\0", ", ");
				res += buffsListAppend(buffs, "(Up.)");
				displays.add("buffUptimes");
			}
			if (Boolean.TRUE.equals(s.getGenerationSelf())){
				sectioning += "Generation Self " + name + new String(new char[count]).replace("\0", ", ");
				res += buffsListAppend(buffs, "(Gen. Slf.)");
				displays.add("selfBuffs");
			}
			if (Boolean.TRUE.equals(s.getGenerationGroup())){
				sectioning += "Generation Group " + name + new String(new char[count]).replace("\0", ", ");
				res += buffsListAppend(buffs, "(Gen. G.)");
				displays.add("groupBuffs");
			}
			if (Boolean.TRUE.equals(s.getGenerationSquad())){
				sectioning += "Generation Squad " + name + new String(new char[count]).replace("\0", ", ");
				res += buffsListAppend(buffs, "(Gen. Sq.)");
				displays.add("squadBuffs");
			}
		}
		if (Boolean.TRUE.equals(s.getPhaseActiveDuration())) {
			if (Boolean.TRUE.equals(s.getUptime()) || s.getUptime() == null){
				sectioning += "Active Uptime " + name + new String(new char[count]).replace("\0", ", ");
				res += buffsListAppend(buffs, "(Act. Up.)");
				displays.add("buffUptimesActive");
			}
			if (Boolean.TRUE.equals(s.getGenerationSelf())){
				sectioning += "Active Generation Self " + name + new String(new char[count]).replace("\0", ", ");
				res += buffsListAppend(buffs, "(Act. Gen. Slf)");
				displays.add("selfBuffsActive");
			}
			if (Boolean.TRUE.equals(s.getGenerationGroup())){
				sectioning += "Active Generation Group " + name + new String(new char[count]).replace("\0", ", ");
				res += buffsListAppend(buffs, "(Act. Gen. G)");
				displays.add("groupBuffsActive");
			}
			if (Boolean.TRUE.equals(s.getGenerationSquad())){
				sectioning += "Active Generation Squad " + name + new String(new char[count]).replace("\0", ", ");
				res += buffsListAppend(buffs, "(Act. Gen. Sq)");
				displays.add("squadBuffsActive");
			}
		}
		if(displays.isEmpty()) {
			//If they are purposefully set to false then do nothing. If they are disabled, then just get uptime.
			if(s.getPhaseDuration() == null && s.getPhaseActiveDuration() == null) {
				sectioning += "Active Uptime " + name + new String(new char[count]).replace("\0", ", ");
				res += buffsListAppend(buffs, "(Act. Up.)");
				displays.add("buffUptimesActive");
				buffsMap.put(s.getDisplayName(), displays);
			}
		}
		else
			buffsMap.put(s.getDisplayName(), displays);
		
		return new String[] {sectioning, res};
	}

	//Creates the heading string for any SettingsCategories.
	private String[] headingFromSettingsCategories(SettingsCategories cat) {
		String sectioning = "";
		String result = "";
		List<String> list = cat.retrieveActiveListNames();
		if (list != null && !list.isEmpty()) {
			sectioning += cat.getDisplayName() + ", ";
			for (String str : list) {
				sectioning += ", ";
				result += str + ", ";
			}
			result += ", ";
		}
		return new String[] {sectioning, result};
	}
	
	//A function for the "General" category that is used for two things:
	// 1. read values from the overall json object. 
	// 2. read values that require computation. e.g. duration ms to seconds, or calculating time alive from the duration and the players death time. 
	/*
	 * put("Percent_Time_Alive", new Stat());
				put("Sucess_Status", new Stat("success")); //Useful in raids/fractals
				put("Duration_Sec", new Stat("durationMS")); 
				put("Is_Challenge_Mote", new Stat("isCM"));
				put("Start_Time_Std", new Stat("timeStart"));
				put("End_Time_Std", new Stat("timeEnd"));
				
	public static String getStringsFromObj(JSONObject obj, List<String> list) {
		if (list == null || list.isEmpty())
			return "";
		String result = "";
		
		for (String s : list) {
			result += jsonObjGetString(obj, s) + ",";
		}
		result += ",";
		return result;
	}
	 */
	private String categorySettingsGeneralCat(SettingsCategories cat, JSONObject json, JSONObject player) {
		String result = "";
		for (String s : cat.retrieveActiveListStats()) {
			switch (s) {
				case "success":
					result += JSONUtility.jsonObjGetString(json, s) + ",";
					break;
				case "durationMS": //This is desired to be shown in SECONDS, so do the conversion.
					String dur = JSONUtility.jsonObjGetString(json, s);
					result += dur.substring(0, dur.length()-3) + ",";
					break;
				case "isCM":
					result += JSONUtility.jsonObjGetString(json, s) + ",";
					break;
				case "timeStart":
					result += JSONUtility.jsonObjGetString(json, s) + ",";
					break;
				case "timeEnd":
					result += JSONUtility.jsonObjGetString(json, s) + ",";
					break;
				case "percentTimeAlive": //This needs to be calculated based on the duration and the players DethRecap
					//player didn't die
					if(!player.containsKey("deathRecap")) {
						result += "100.00%,"; 
						break;
					}

					Double TOD = JSONUtility.jsonObjGetDouble(JSONUtility.getJsonObjFromArrayInObj(player, "deathRecap"), "deathTime");
					Double duration = JSONUtility.jsonObjGetDouble(json, "durationMS");
					Double percent = (TOD/duration)*100;
					result += String.valueOf(percent) + "%,";
					System.out.println("time here: " + TOD + ", and " + duration + ", and " + String.valueOf(percent));
					break;
				default:
					result += "NA,";
					
			}
		}
		return result+",";
	}
	
	// function called for each log, from the Analysis class. Writes in all the needed information. 
	public void writeToCSV(JSONObject json, JSONObject player, String htmlFilePath, Settings s) {
		//Write the default infomation for the log, such as location, class, etc. basedo n settings.
		writeDefaultInformation(json, player, htmlFilePath, s);
		
		//SettingsCategories are used to store player setting on retrieving basic, key-value objects inside of one JSONObject. 
		for (SettingsCategories cat : s.getCategories()) {
			if(cat.getObjectName().equals("general"))
				writeString(categorySettingsGeneralCat(cat, json, player));//JSONUtility.categorySettingsWriting(cat, json));
			else
				writeString(JSONUtility.categorySettingsWriting(cat, player));
		}
		
		//SettingBuff class is used to store player settings on retrieving buff data from the log. 
		//buff data requires utilizing different "phase" jsonobjects, and navigating arrays to access the buff info. 
		//TODO: Unfortunately, this currently does searches for each buff category, such as "boons" vs "profession buffs."  this could be optimized.
		for(SettingsBuff cat : s.getBuffsList()) {
			if (buffsMap.containsKey(cat.getDisplayName()))
				buffSettingsWriting(cat, player, buffsMap.get(cat.getDisplayName()));
		}
		
		if(buffsMap.containsKey(s.getProfessionBuffsSettings().getDisplayName()))
			buffSettingsWriting(s.getProfessionBuffsSettings(), player, buffsMap.get(s.getProfessionBuffsSettings().getDisplayName()));
		
		//consumables
		if(s.getEffectsEnabled()) {
			if(player.containsKey("consumables"))
				propertiesWriting("db.effect.", Constants.EFFECT_IDS_FILE, (JSONArray) player.get("consumables"));
			else
				writeString("NA, ");
		}
		writeNewLine();
	}
	

	
	//Write information from SettingsBuff
	private void buffSettingsWriting(SettingsBuff s, JSONObject player, List<String> phasesList) {
		for(String entry : phasesList) {
			if(player.containsKey(entry))
				//writeString(getBuffString((JSONArray)(player.get(entry)), s.retrieveActiveBuffIDList()));
				writeString(getBuffString((JSONArray)(player.get(entry)), s));
			else {
				logger.warn("The player json object does not contain an entry for the key \"" + entry +"\".");
				writeString(new String(new char[s.retrieveActiveBuffIDList().size()-1]).replace("\0", "NA, "));
			}
		}
	}

	//Used for buffs. Searches through the given JSON buff Array to find and return the values based on a given list of ids. 
	private String getBuffString(JSONArray section, SettingsBuff s) {
		List<Buff> activeBuffs = s.retrieveActiveBuffList();
		if(activeBuffs.size() == 0)
			return"";
		List<Integer> activeBuffIDs = s.retrieveActiveBuffIDList();
		
		List<String> list = new ArrayList<String>(Collections.nCopies(activeBuffs.size(), "NA")); //TODO: Change this to zero, then excel can still do computations. "NA" for testing. 
		for (int i = 0; i < section.size(); i++) {
			JSONObject boon = (JSONObject) section.get(i);
			int id = ((Long)boon.get("id")).intValue();
			
			if(activeBuffIDs.contains(id)) {
				JSONObject buffData = JSONUtility.getJsonObjFromArrayInObj(boon, "buffData");//(JSONObject) ((JSONArray) boon.get("buffData")).get(0);
				if(buffData == null) {
					continue;
				}
				
				Double buff = null;
				if (buffData.containsKey("generation"))
					buff= (Double) buffData.get("generation");
				else if (buffData.containsKey("uptime")) 
					buff=(Double) buffData.get("uptime");
				else {
					logger.warn("Buff data doesn't contain a phase key for buff " + id);
					continue;
				}
				
				//get index corresponding to the buff. 
				int index = 0;
				for(int j = 0; j < activeBuffs.size(); j++) {
					if (activeBuffs.get(j).getIds().contains(id)) {
						index = j;
						break;
					}
				}
				
				
				//If already in list, add old value to buff.
				if(!list.get(index).equals("NA")) {
					Double curV = Double.parseDouble(list.get(index).replace("%", ""));
					buff = buff + curV;
				}
				String res = String.valueOf(buff);
				if (Constants.BUFF_IS_PERCENT.contains(id))
					res+='%';
				//If not in list, just add. 
				list.set(index, res);
				continue;
			}
		}
		String result =list.toString();
		result = (result).substring(1,result.length()-1) + ", , ";
		return result;
	}
	
	//TODO: Consumables is good to have, but needs to be handled differently. 
	// Used to find consumables based on the JSON and the effect properties file. Not sure what to do with this yet. 
	private void propertiesWriting(String dbName, String file, JSONArray objArr) {
		String consumablesString = "";
		try (InputStream input = Constants.class.getResourceAsStream("/constEffectIDs.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            
            for (Object o : objArr) {
            	JSONObject obj = (JSONObject) o;
            	String name = null;
            	
        		name = prop.getProperty(dbName + obj.get("id"));
            	if (name == null)  {
            		System.out.println("Couldn't find id for " + obj.get("id"));
            		continue;
            	}
            	consumablesString += name + "... ";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		consumablesString += ",";
		writeString(consumablesString);
	}
	
	public void writeNewLine() {
		try {
			bw.newLine();
		} catch (IOException e) {
			logger.error("Error writing a new line in JSON parsing. " + e);
			e.printStackTrace();
		}
	}
	
	private void writeString(String s) {
		try {
			bw.write(s);
		} catch (IOException e) {
			logger.error("Error writting string into csv: " + e);
			e.printStackTrace();
		}
	}
	
	//Final write before you're done writing the csv. This is needed because we are using the BufferedWriter. 
	public void endParsing() {
		try {
			bw.close();
		} catch (IOException e) {
			logger.error("Error ending JSON parsing to csv." + e);
			e.printStackTrace();
		}
	}
	
}
