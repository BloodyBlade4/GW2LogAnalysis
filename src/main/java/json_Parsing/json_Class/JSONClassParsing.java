package json_Parsing.json_Class;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import components.Buff;
import components.Constants;
import components.DialogHelper;
import settings.Settings;
import settings.SettingsBuff;
import settings.SettingsCategories;

public class JSONClassParsing {
	static final Logger logger = Logger.getLogger(JSONClassParsing.class);
	private BufferedWriter bw;
	public int headerRow;
	private static Map<String, List<String>> buffsMapPhases; //Filled when generating headers. 
	
	public JSONClassParsing(String fileName, Settings s) throws Exception{
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
		buffsMapPhases = new HashMap<String, List<String>>();

		//try (InputStream input = new FileInputStream(System.getProperty("user.dir") +"\\src\\resources\\constBuffIDs.properties")) {
		/*
		try (InputStream input = Constants.class.getResourceAsStream("/constBuffIDs.properties")) {
            prop = new Properties();
            prop.load(input);
		}
	    catch (IOException ex) { //should this be fatal?
	        ex.printStackTrace();
	        DialogHelper.errorMessage("Runtime Error", "IOException reading ID properties file: " + ex);
	    }
		*/
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
					buffsMapPhases.put(s.getDisplayName(), displays);
				}
			}
			else
				buffsMapPhases.put(s.getDisplayName(), displays);
			
			return new String[] {sectioning, res};
		}
		private String buffsListAppend(List<String> list, String str) {
			String res = (list.stream().map(item -> item + ' ' + str).collect(Collectors.toList())).toString();
			return res.substring(1,res.length()-1) + ", , "; //Remove brackets at beginning and end. 
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
		private void writeDefaultInformation(JSONClass json, String htmlFilePath, Settings s) {
			//Location, Date	
			String res = "";
			if(s.getEncounterID() == null || s.getEncounterID() == 0) {
				res += json.getFightName() + ",";
			}
			if(s.getEmbbedHTML()) {
				res += htmlFilePath + ",";
			}
			
			if(s.getProfession().equals("Any")) {
				res += json.getPlayer().getProfession() +",";
			}
			res += ", ";
			
			if (!res.isEmpty())
				writeString(res);
		}
		
		
		public void writeToCSV(JSONClass json, String htmlFilePath, Settings s) {
			//Write the default information for the log, such as location, class, etc. based on settings.
			writeDefaultInformation(json, htmlFilePath, s);
			
			//SettingsCategories are used to store player setting on retrieving basic, key-value objects inside of one JSONObject. 
			for (SettingsCategories cat : s.getCategories()) {
				if(cat.getObjectName().equals("general"))
					writeString(JSONParsingStringConstruction.categorySettingsGeneralCat(cat, json));//JSONUtility.categorySettingsWriting(cat, json));
				else if (cat.getObjectName().equals("player"))
					System.out.println("Player"); //Get items from the player.... 
				else //TODO: Category Settings need to be changes because they don't need recursion any more!
					writeString(JSONParsingStringConstruction.categorySettingsWriting(cat, json.getPlayer()));
			}
			
			//SettingBuff class is used to store player settings on retrieving buff data from the log. 
			//buff data requires utilizing different "phase" jsonobjects, and navigating arrays to access the buff info. 
			//TODO: Unfortunately, this currently does searches for each buff category, such as "boons" vs "profession buffs."  this could be optimized.
			for(SettingsBuff cat : s.getBuffsList()) {
				if (buffsMapPhases.containsKey(cat.getDisplayName())) 
					for(String phase : buffsMapPhases.get(cat.getDisplayName()))
						writeString(buffSettingsWriting(cat, json.getPlayer(), phase));
			}

			if(buffsMapPhases.containsKey(s.getProfessionBuffsSettings().getDisplayName()))
				for(String phase: buffsMapPhases.get(s.getProfessionBuffsSettings().getDisplayName()))
					writeString(buffSettingsWriting(s.getProfessionBuffsSettings(), json.getPlayer(), phase));
			
			//consumables!
			
		}

		public static String buffSettingsWriting(SettingsBuff cat, JSONPlayer player, String phase) {
			List<Buff> activeBuffs = cat.retrieveActiveBuffList(); 
			List<Integer> activeBuffIDs = cat.retrieveActiveBuffIDList();
			
			Map<Integer, Map<String, Double>> found = player.getBuffCategoryByString(phase);
			if (found == null) {
				System.out.println("Buffs returned null? ");
				return "";
			}
			
			List<String> list = new ArrayList<String>(Collections.nCopies(activeBuffs.size(), "NA")); //TODO: Change this to zero, then excel can still do computations. "NA" for testing. 
			for (int activeID : activeBuffIDs) {
				if(found.containsKey(activeID)) {
					Double value = (found.get(activeID).containsKey("uptime")) ? found.get(activeID).get("uptime") : found.get(activeID).get("generation");
					//JSONObject buffData = JSONUtility.getJsonObjFromArrayInObj(boon, "buffData");//(JSONObject) ((JSONArray) boon.get("buffData")).get(0);
					
					if(value == null) {
						continue;
					}
					
					//get index corresponding to the buff. 
					int index = 0;
					for(int j = 0; j < activeBuffs.size(); j++) {
						if (activeBuffs.get(j).getIds().contains(activeID)) {
							index = j;
							break;
						}
					}
					
					
					//If already in list, add old value to buff.
					if(!list.get(index).equals("NA")) {
						Double curV = Double.parseDouble(list.get(index).replace("%", ""));
						value = value + curV;
					}
					String res = String.valueOf(value);
					if (Constants.BUFF_IS_PERCENT.contains(activeID))
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
