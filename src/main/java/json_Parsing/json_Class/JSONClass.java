package json_Parsing.json_Class;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import json_Parsing.json_Class.deserializers.JSONClassDeserializer;

/*
 * IDEAS:
 * 1. Format the classes yourself. Huge list of getters and setters. They will be filled automatically by the deserializer. 
 * 		However, you would need to set booleans inside each constructor/class object based on user settings before running the parser.
 * 		This can be done with a default JSONClass call that takes in user settings. 
 * 
 * 2. Use a non-default constructor, as shown here https://stackoverflow.com/questions/19796730/java-how-to-create-a-class-instance-from-user-input, 
 * 		This wouldn't give as much control but the settings classes are kind of already set up for this. pass in the settings classes for these things.  
 * 
 *  
 *  
 *  Optimizations: 
 *  - Currently, JSONPlayer is using an object mapper to get every object array. This is a priority to fix. 
 *  
 *  */



//I'm not sure.. Thinking this one out. 
@JsonDeserialize(using = JSONClassDeserializer.class)
public class JSONClass {
	static private String playerName;
	
	public JSONClass(String name) {
		playerName = name;
	}
	
	public JSONClass() {
		
	}
	public void setPlayerName(String name) {
		JSONClass.playerName = name;
	}
	public String getPlayerName() {
		return JSONClass.playerName;
	}
	
	private String fightName = "NA";
	private String recordedBy = "NA";
	private String timeStart = "NA";
	private String timeEnd = "Na";
	private String timeStartStd = "NA";
	private String timeEndStd = "NA";
	private String duration = "NA";
	private long durationMS = -1;
	private Boolean success = false;
	private Boolean isCM = false;
	private JSONPlayer player = null;
	private int squadSize = 0;
	
	
	public int getSquadSize() {
		return squadSize;
	}
	public void setSquadSize(int size) {
		this.squadSize = size;
	}
	public JSONPlayer getPlayer() {
		return player;
	}
	public void setPlayer(JSONPlayer player) {
		this.player = player;
	}
	public String getFightName() {
		return fightName;
	}
	public Boolean getSuccess() {
		return success;
	}
	public Boolean getIsCM() {
		return isCM;
	}
	public void setFightName(String fightName) {
		this.fightName = fightName;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public void setIsCM(Boolean isCM) {
		this.isCM = isCM;
	}

	public String getRecordedBy() {
		return recordedBy;
	}

	public void setRecordedBy(String recordedBy) {
		this.recordedBy = recordedBy;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getTimeStartStd() {
		return timeStartStd;
	}

	public void setTimeStartStd(String timeStartStd) {
		this.timeStartStd = timeStartStd;
	}

	public String getTimeEndStd() {
		return timeEndStd;
	}

	public void setTimeEndStd(String timeEndStd) {
		this.timeEndStd = timeEndStd;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public long getDurationMS() {
		return durationMS;
	}

	public void setDurationMS(long durationMS) {
		this.durationMS = durationMS;
	}
	
	
	
	
}









