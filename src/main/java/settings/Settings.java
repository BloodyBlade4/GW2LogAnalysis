package settings;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import components.Constants;

public class Settings {
	//Main menu filters and settings
	int minDuration = 0; //in seconds
	String SettingsName = "Settings01";
	String CharName = null;
	String Location = "Any";
	Integer EncounterID = 0;
	String Profession = "Any";
	String Specialization ="Any";
	
	//Profession buff settings, changes when the player select a new profession. 
	SettingsBuff professionBuffsSettings = new SettingsBuff(true, true, true, null, null, null, "Profession Buffs", "personalBuffs",
			Constants.generateBuffListWithFilter(System.getProperty("user.dir") +"\\src\\resources\\constProfessionBuffIDs.properties", ""));
	//Buff settings
	List<SettingsBuff> buffsList = Constants.buffsCategoriesGenerateDefaults();
	//Stats settings, for many types of categories
	List<SettingsCategories> categories = Constants.categoriesGenerateDefaults();
	Boolean effectsEnabled = true;
	
	
	
	//Settings and file configuration
	String InputFile = ""; //The folder holding all the files to analyze. 
	String OutputFileName = "LogAnalysisOutput"; //The name for the output file. 
	String OutputFile = "";	//The path for the output file. 
	String EIAbsPath = "";	//The absolute path to Elite insights (EI). 
	Boolean UseEI = false;
	Boolean embbedHTML = false; //Will have EI generate HTML, if running, and will search for and embed HTML inside the excel file. 
	Boolean saveJSON = false;	// (ONLY works if "UseEI" is true). if True, will not delete the JSON files after the program ends
	
	
	
	
	//Used in time checks. 
	Boolean datesByLog = false; //If true, will check inside JSON for date range. Otherwise, use file modified timestamp. 
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	LocalDate fromDate = null;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	LocalDate toDate = null;	
	
	//Advanced filtering tab
	Integer minComDistance = 0;
	Integer minSquadSize = 0;
	Integer minPercentTimeAlive = 0; 
	String WepSet1MainHand="Any";
	String WepSet1Offhand="Any";
	String WepSet2MainHand="Any";
	String WepSet2Offhand="Any";
	Boolean WepFilterStrict = false;
	
	
	
	
	public Integer getMinPercentTimeAlive() {
		return minPercentTimeAlive;
	}

	public void setMinPercentTimeAlive(Integer minPercentTimeAlive) {
		this.minPercentTimeAlive = minPercentTimeAlive;
	}

	public Boolean getDatesByLog() {
		return datesByLog;
	}

	public void setDatesByLog(Boolean datesByLog) {
		this.datesByLog = datesByLog;
	}

	public Boolean getSaveJSON() {
		return saveJSON;
	}

	public void setSaveJSON(Boolean saveJSON) {
		this.saveJSON = saveJSON;
	}

	public Boolean getEmbbedHTML() {
		return embbedHTML;
	}

	public void setEmbbedHTML(Boolean embbedHTML) {
		this.embbedHTML = embbedHTML;
	}

	public String getEIAbsPath() {
		return EIAbsPath;
	}

	public void setEIAbsPath(String eIAbsPath) {
		this.EIAbsPath = eIAbsPath;
	}

	public Boolean getUseEI() {
		return UseEI;
	}

	public void setUseEI(Boolean useEI) {
		UseEI = useEI;
	}

	public Integer getMinComDistance() {
		return minComDistance;
	}

	public void setMinComDistance(Integer minComDistance) {
		this.minComDistance = minComDistance;
	}

	public Integer getMinSquadSize() {
		return minSquadSize;
	}

	public void setMinSquadSize(Integer minSquadSize) {
		this.minSquadSize = minSquadSize;
	}

	public Integer getEncounterID() {
		return EncounterID;
	}

	public void setEncounterID(Integer encounterID) {
		EncounterID = encounterID;
	}

	public String getSettingsName() {
		return SettingsName;
	}

	public void setSettingsName(String settingsName) {
		SettingsName = settingsName;
	}

	public List<SettingsBuff> getBuffsList() {
		return buffsList;
	}

	public void setBuffsList(List<SettingsBuff> buffsList) {
		this.buffsList = buffsList;
	}
	
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public Boolean getEffectsEnabled() {
		return effectsEnabled;
	}

	public void setEffectsEnabled(Boolean effectsEnabled) {
		this.effectsEnabled = effectsEnabled;
	}

	public List<SettingsCategories> getCategories() {
		return this.categories;
	}
	
	public void setCategories(List<SettingsCategories> newCategories) {
		this.categories = newCategories;
	}
	
	public Boolean getWepFilterStrict() {
		return this.WepFilterStrict;
	}
	public void setWepFilterStrict(Boolean wepFilterStrict) {
		this.WepFilterStrict = wepFilterStrict;
	}
	public String getWepSet1MainHand() {
		return this.WepSet1MainHand;
	}
	public void setWepSet1MainHand(String wepSet1MainHand) {
		this.WepSet1MainHand = wepSet1MainHand;
	}
	public String getWepSet1Offhand() {
		return this.WepSet1Offhand;
	}
	public void setWepSet1Offhand(String wepSet1Offhand) {
		this.WepSet1Offhand = wepSet1Offhand;
	}
	public String getWepSet2MainHand() {
		return this.WepSet2MainHand;
	}
	public void setWepSet2MainHand(String wepSet2MainHand) {
		this.WepSet2MainHand = wepSet2MainHand;
	}
	public String getWepSet2Offhand() {
		return this.WepSet2Offhand;
	}
	public void setWepSet2Offhand(String wepSet2Offhand) {
		this.WepSet2Offhand = wepSet2Offhand;
	}
	public SettingsBuff getProfessionBuffsSettings() {
		return this.professionBuffsSettings;
	}
	public void setProfessionBuffsSettings(SettingsBuff professionBuffsSettings) {
		this.professionBuffsSettings = professionBuffsSettings;
	}
	public String getProfession() {
		return this.Profession;
	}
	public void setProfession(String profession) {
		this.Profession = profession;
	}
	public String getSpecialization() {
		return this.Specialization;
	}
	public void setSpecialization(String specialization) {
		this.Specialization = specialization;
	}
	
	public String getLocation() {
		return this.Location;
	}
	public void setLocation(String location) {
		this.Location = location;
	}
	public int getMinDuration() {
		return this.minDuration;
	}
	public void setMinDuration(int minDuration) {
		this.minDuration = minDuration;
	}
	public String getCharName() {
		return this.CharName;
	}
	public void setCharName(String charName) {
		this.CharName = charName;
	}
	public String getInputFile() {
		return this.InputFile;
	}
	public void setInputFile(String inputFile) {
		this.InputFile = inputFile;
	}
	public String getOutputFileName() {
		return this.OutputFileName;
	}
	public void setOutputFileName(String outputFileName) {
		this.OutputFileName = outputFileName;
	}
	public String getOutputFile() {
		return this.OutputFile;
	}
	public void setOutputFile(String outputFile) {
		this.OutputFile = outputFile;
	}
}
