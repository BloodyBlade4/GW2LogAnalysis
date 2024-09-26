import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import components.Constants;
import components.DialogHelper;
import components.FileHelper;
import elite_Insights_Parsing.EIParsing;
import elite_Insights_Parsing.EISettings;
import gui_Components.FileBar;
import gui_Components.LoadingScreen;
import json_Parsing.JSONParsing;
import json_Parsing.JSONUtility;
import json_Parsing.json_Class.JSONClass;
import json_Parsing.json_Class.JSONClassParsing;
import settings.Settings;
import xlsx_Parsing.XLSXFromCSV;

/*
 * TODO:
 * -finish putting in the JSONClass/ Jackson parsing. 
 * File deletion isn't working because they are still being used by another process. 
 * - If the "Excel document is currently open, would you like to try again" window's "no"option is clicked, it doesn't exit. 
 */

public class Analysis {
	private Settings curSettings;
	private JSONParsing parser;
	private JSONClassParsing classParser;
	static final Logger logger = Logger.getLogger(Analysis.class); 
	
	Analysis() {}
	
	public void analize(Settings s) throws Exception {
		logger.info("Starting analysis of logs.");
		curSettings = s;
		
		String outputFileLocation = curSettings.getOutputFile() + "\\" + curSettings.getOutputFileName();
		
		Path JSONDirectory = (s.getUseEI()) ? 
								EIParsing.createDirectory(s.getOutputFile()) : Paths.get(s.getInputFile()); 
		if (s.getUseEI() && JSONDirectory == null)
			return;
		
		//Get the file list 
		PathMatcher matcher = (s.getUseEI()) ?
				FileSystems.getDefault().getPathMatcher("glob:*.zevtc") 
				:
				FileSystems.getDefault().getPathMatcher("glob:*.json");
		
		//Generate the loading screen with a list of "FileBar" objects, used to hold state and path.
		LoadingScreen loadingScreen = new LoadingScreen(
				//Open a Stream<Path> for the input directory
				Files.list(Paths.get(s.getInputFile())) 
					//Filter by desired extension, also by date if necessary.
		    		.filter(f -> (matcher.matches(f.getFileName()) && (curSettings.getDatesByLog() || FileHelper.checkTimeStamps(f, curSettings.getFromDate(), curSettings.getToDate())) ))
		    		//create and load in the fileBar objects.
		    		.map(f -> new FileBar(f.toAbsolutePath().toString())).toArray(FileBar[]::new)
				);
		if (loadingScreen.getFileCount() == 0) {
			DialogHelper.errorMessage("File Error", "There are no files here. If you are using date filtering, double check your settings. Else, select a different directory.");
			return;
		}
		
		//TESTING fasterxml JSON parsing. 
		//JSONClass json = new JSONClass(curSettings.getCharName());
		//Load JSON parsing class. Exception handling done internally, just return in case of failure. 
		try {
			//Default JSON parsing parser. 
			parser = new JSONParsing(outputFileLocation + ".csv", s);
			
			//TESTING fasterxml JSON Parsing.
			//classParser = new JSONClassParsing(outputFileLocation + ".csv", s);
		}
		catch (Exception e) {
			return;
		}
		
		logger.info("Starting to parse the " + loadingScreen.getFileCount() + " files that match application filtering.");
		SwingWorker<?,?> sw = new SwingWorker<>() {
			@Override 
			protected String doInBackground() throws Exception 	{
				return batchRead(loadingScreen, JSONDirectory);
			}
			
			@Override protected void done() {
				if (!isCancelled()) {
					try {
						get();
						
						parser.endParsing();
						XLSXFromCSV.csvParseToXLSX(outputFileLocation, parser.headerRow);
						//TESTING Fasterxml json parsing. 
						//classParser.endParsing();
						//XLSXFromCSV.csvParseToXLSX(outputFileLocation, classParser.headerRow);
						
						//TODO: Handle file deletion better!
						if(curSettings.getUseEI() && !curSettings.getSaveJSON()) {
							FileHelper.deleteAllPathInDirectory(JSONDirectory); //Ensure the folder is empty
							if(Files.list(JSONDirectory).findAny().isPresent())
								DialogHelper.errorMessage("File Error", "Unable to delete files in the directory " + JSONDirectory.getFileName().toString());
							else
								Files.delete(JSONDirectory); //delete.
						}
					} catch(Exception e) {
						DialogHelper.errorMessage("Runtime Error", "Cannot create Elite Insights configuration file. Exception: " + e);
						System.out.println("Get resulted in an exception. " + e);
					}
				}
				else {
					//DialogHelper.errorMessage("Runtime Error", "Cannot create Elite Insights configuration file. Exception: " + e);
					System.out.println("The process was canceled successfully! ");
				}
					
				loadingScreen.endLoading(outputFileLocation);
			}
		};
		sw.execute();
	}
	
	private void updateBatchState(FileBar[] fileList, FileBar.FileState state) {
		for(FileBar f : fileList) {
			f.setState(state);
		}
	}
	
	private String batchRead(LoadingScreen loadingScreen, Path jsonDirectory) throws Exception {
		//Generate EI conf file, if needed. Will bubble exception upwards to cancel the thread worker.
		String confFile = null;
		if (curSettings.getUseEI()) {
			confFile = EISettings.generateEIConf(curSettings, jsonDirectory.toAbsolutePath().toString());
			if (confFile == null)
				throw new Exception("Unable to generate config file!");
		}
		
		while(!loadingScreen.isDone() && loadingScreen.visible) {
			FileBar[] batch = loadingScreen.getBatch();

			//EI Parsing. 
			if(curSettings.getUseEI()) {
				updateBatchState(batch, FileBar.FileState.PARSING_ZEVTC);

				//Run the files into EI. Will bubble exception upwards to cancel the thread worker.
				EIParsing.runEIBatch(Arrays.stream(batch).map(fb -> fb.getFilePath().toString()).collect(Collectors.toList()), 
									curSettings.getEIAbsPath(), confFile);

				/*
				 * Match the newly generated files to their corresponding FileBar. O(BATCH_SIZE*BATCH_SIZE) 
				 * If the files don't match, the EI didn't generate any JSON, but this can be purposeful. These File Bars are set to null and failed. 
				 */
				PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.json");
				for(FileBar fb : batch) {
					String name = fb.generateFileName();
					name = name.substring(0, name.length()-6); //Gets name, without ".json".
					fb.setFilePath(null); //In the case that the json file is never found. 
					
					for(Path p : Files.newDirectoryStream(jsonDirectory)) {
						if (p.getFileName().toString().contains(name) && matcher.matches(p.getFileName())) {
							fb.setFilePath(p.toAbsolutePath().toString());
							break;
						}
					}
				}
			}
			
			//Read JSON.
			updateBatchState(batch, FileBar.FileState.READING_JSON);
			for (FileBar fb : batch) {
				if (fb.getFilePath() == null) 
					fb.setState(FileBar.FileState.DNE);
				else {
					Path jsonFile = Paths.get(fb.getFilePath());
					if (Files.exists(jsonFile)) {//Double check that the file exists.
						fb.finishedFile(readJSON(jsonFile));
						
						//TESTING Using fasterxml to do the parsing. 
						//fb.finishedFile(parseJSON(jsonFile));
					}
					else {
						logger.error("JSON file is supposed to exist, yet it is not found. " + fb.getFilePath());
						fb.setState(FileBar.FileState.ERROR);
					}
				}
				loadingScreen.increaseProgress();
			}
			
			if(curSettings.getUseEI() && !curSettings.getSaveJSON()) {
				if(curSettings.getEmbbedHTML()) {
					PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.html");
					FileHelper.deleteAllPathInDirectoryThatDoesNotMatch(jsonDirectory, matcher);
				}
				else
					FileHelper.deleteAllPathInDirectory(jsonDirectory);
			}
			loadingScreen.nextBatch();
		}
		return "finished batchEIRead";
	}
	
	//TESTING using jackson.
	public String parseJSON(Path pa) {
		
		try {
			ObjectMapper objectMapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			InputStream input = Files.newInputStream(pa);
			JSONClass json = objectMapper.readValue(input, JSONClass.class);
			if(json != null) {
				//System.out.println("the healing object is : " + json.getPlayer().getHealingStats().keySet().toString());
				System.out.println("The dps object is: " + json.getPlayer().getSupport().keySet().toString());
				classParser.writeToCSV(json, "htmlpath", curSettings); 
				System.out.println("Found json with: " + json.getFightName());
				//System.out.println("Found json with: " + json.getSuccess());
				//System.out.println("Found json with: " + json.getIsCM());
				System.out.println("The player object contains: " + json.getPlayer().getAccount() + ", " + json.getPlayer().getProfession());
				System.out.println("Buffs is " + json.getPlayer().buffUptimes.toString());
				//System.out.println("Player stats all: " + json.getPlayer().statsAll.keySet().toString());
			} else
				System.out.println("No json???");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to create object.");
		}
		
		return "Trying here.";
	}
	
	//passed an individual log file. Returns results based on your settings. 
	public String readJSON(Path pa) {
		File file = new File(pa.toAbsolutePath().toString());
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(file));
			String filterOK = simpleFilterOkay(obj);
			if (!filterOK.equals("SUCCESS")) 
				return filterOK;
			
			//find desired player inside of players.
			JSONObject player = null;
			for (Object p : (JSONArray) obj.get("players")) {
				JSONObject curPlayer = (JSONObject) p;
				if (curPlayer.get("name").equals(curSettings.getCharName()) ||
						curPlayer.get("account").equals(curSettings.getCharName())) {
					player = curPlayer;
					break;
				}
			}
			if (player == null) 
				return "Failed: Player not found.";
			
			filterOK = advancedFilterOkay(player, JSONUtility.jsonObjGetDouble(obj, "durationMS"));
			if (!filterOK.equals("SUCCESS")) 
				return filterOK;
			
			//Get html, if active. 
			String htmlFilePath = "NA";
			if (curSettings.getEmbbedHTML()) {
				String path = file.getAbsolutePath();
				path = path.substring(0, path.length()-4) + "HTML";
				if((new File(path)).exists()) {
					htmlFilePath = path;
				}
			}

			//send information off to CustomJSONParsing to be put into the CSV. 
			logger.info("Starting json parsing for the file \"" + file.getName() + "\"." );
			parser.writeToCSV(obj, player, htmlFilePath, curSettings);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			logger.error("Failed to read json due to file/parsing issue: " + e);
			return "Failed: Check logs for info.";
		} catch (NullPointerException nE) {
			nE.printStackTrace();
			logger.warn("Log file is missing a required key for parsing: " + nE);
			return "Failed: log is missing a key required for parsing.";
		}
		return "SUCCESS";
	}
	
	private String simpleFilterOkay(JSONObject obj)  throws NullPointerException{
		//duration.
		int durationMS = JSONUtility.jsonObjGetint(obj, "durationMS");
		if (((curSettings.getMinDuration() !=0) && (durationMS < (curSettings.getMinDuration() *100)))	) 
			return "Failed to pass minimum duration.";
		
		//Location
		String location = curSettings.getLocation();
		if(!location.equals("Any")){ 
			int ei = JSONUtility.jsonObjGetint(obj, "eiEncounterID");
			if(ei == 0x010000) //Unsupported encounter id. 
				return "Failed: Log has an unsupported encounter id.";
			
			if ( !Constants.ENCOUNTER_IDS.get(location).containsKey(ei) || //The encounter is not found in the encounter id list... or
					(curSettings.getEncounterID() != 0 && curSettings.getEncounterID() != ei) ) //The encounter is set to something, and this JSON encounter isn't the same. 
				return "Failed: Incorrect location.";
		}
		
		//Recorded date range checks
		if ( curSettings.getDatesByLog()) {
			String timeStart = ((String)obj.get("timeStart")).substring(0, 10); //Grab the date, but only this part: "yyyy-MM-dd".
			LocalDate date = LocalDate.parse(timeStart, DateTimeFormatter.ISO_LOCAL_DATE);
			if (curSettings.getFromDate()!=null && curSettings.getFromDate().compareTo(date) > 0) 
				return "Failed: Log is older than your filter settings allow.";
			
			if(curSettings.getToDate()!=null && curSettings.getToDate().compareTo(date) < 0) 
				return "Failed: Log is newer than your filter settings allow.";
		}
		
		//Number of players in squad
		int squadSize = ((JSONArray)obj.get("players")).size();
		if(squadSize < curSettings.getMinSquadSize())
			return "Only " + squadSize + " players in the logs' squad. Filtering for minimum of " + curSettings.getMinSquadSize() + ".";
		
		return "SUCCESS";
	}
	
	private String advancedFilterOkay(JSONObject obj, double duration) throws NullPointerException{
		//profession/specialization check
		if (!curSettings.getProfession().equals("Any") && !obj.get("profession").equals(curSettings.getSpecialization())) 
			return "Failed: The profession/specialization you selected doesn't match. This character is running " + obj.get("profession");
		
		//statsAll object
		JSONObject statsAll = (JSONObject) ((JSONArray)obj.get("statsAll")).get(0);
		
		//Distance to tag.
		if(	curSettings.getMinComDistance() != 0 && ((Double)statsAll.get("distToCom")).intValue() > curSettings.getMinComDistance() ) 
			return "Failed: Distance to commander is greater than your current settings allow. Log shows: " + ((Double)statsAll.get("distToCom")).intValue();
		
		//Percent time alive
		if( curSettings.getMinPercentTimeAlive() != 0 && obj.containsKey("deathRecap")) {
			Double TOD = JSONUtility.jsonObjGetDouble(JSONUtility.getJsonObjFromArrayInObj(obj, "deathRecap"), "deathTime");
			Double percent = (TOD/duration)*100;
			if(percent < curSettings.getMinPercentTimeAlive())
				return "Failed: Percentage of time alive during the fight is less than settings allow.";
		}

		
		//Weapons check:
		Object weps = obj.get("weapons");
		List<String> objWeapons = new ArrayList<>();
		if (weps instanceof List<?>) {
			((List<?>) weps).forEach(o -> {
				if(o instanceof String)
					objWeapons.add((String)o);
				else
					objWeapons.add("");
			});

		}
			
		if (objWeapons.size() < 4) {
			if(curSettings.getWepFilterStrict())
				return "Failed: Weapon(s) unidentifiable. Due to strict filtering, this log is discarded.";
			return "SUCCESS";
		}
		if (!weaponFoundCheck(String.valueOf(objWeapons.get(0)), curSettings.getWepSet1MainHand()) ||
			!weaponFoundCheck(String.valueOf(objWeapons.get(1)), curSettings.getWepSet1Offhand()) ||
			!weaponFoundCheck(String.valueOf(objWeapons.get(2)), curSettings.getWepSet2MainHand()) ||
			!weaponFoundCheck(String.valueOf(objWeapons.get(3)), curSettings.getWepSet2Offhand())) 
			return "Failed: Equipped weapons do not match the weapon filter.";
		
		return "SUCCESS";
	}
	
	private Boolean weaponFoundCheck(String objWeapon, String weapon) {
		if (!weapon.equals("Any") && !objWeapon.equals(weapon)) {
			if ( !((objWeapon.equals("Unknown")) && !curSettings.getWepFilterStrict()) ) //~("unknown" && ~S)
				return false;
		}
		return true;
	}

	
}


