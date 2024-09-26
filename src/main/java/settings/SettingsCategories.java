package settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import components.Stat;


public class SettingsCategories {
	String displayName ="";
	String objectName ="";
	Boolean isArray = false;
	SettingsCategories innerCat = null;
	HashMap<String, Stat> statsMap = new HashMap<String, Stat>();
	
	
	public SettingsCategories() {	}
	
	public SettingsCategories(String DisplayName, String ObjName, Boolean IsArray, SettingsCategories InnerCat, HashMap<String, Stat> StatsMap) {
		this.displayName = DisplayName;
		this.objectName = ObjName;
		this.isArray = IsArray;
		this.innerCat = InnerCat;
		this.statsMap = StatsMap;
	}
	
	public SettingsCategories(String DisplayName, String ObjName, Boolean IsArray, SettingsCategories InnerCat) {
		this.displayName = DisplayName;
		this.objectName = ObjName;
		this.isArray = IsArray;
		this.innerCat = InnerCat;
	}
	public SettingsCategories(String DisplayName, String ObjName, Boolean IsArray, HashMap<String, Stat> StatsMap) {
		this.displayName = DisplayName;
		this.objectName = ObjName;
		this.isArray = IsArray;
		this.statsMap = StatsMap; 
	}
	
	public void updateActiveListStats(List<String> selected) {
		if (this.innerCat != null) {
			this.innerCat.updateActiveListStats(selected);
			return;
		}
		for (Stat s : this.statsMap.values()) {
			if (selected.contains(s.getObjName()))
				s.setIsActive(true);
			else 
				s.setIsActive(false);
		}
	}
	
	public void updateActiveListNames(List<String> selected) {
		if (this.innerCat != null) {
			this.innerCat.updateActiveListNames(selected);
			return;
		}
		for (String s : this.statsMap.keySet()) {
			if (selected.contains(s))
				this.statsMap.get(s).setIsActive(true);
			else 
				this.statsMap.get(s).setIsActive(false);
		}
		
	}
	
	public List<String> retrieveActiveListNames() {
		if (this.innerCat != null) {
			return this.innerCat.retrieveActiveListNames();
		}
			
		if(statsMap == null)
			return null; //Should this return an empty list? 
		List<String> des = new ArrayList<String>();
		for (String s : this.statsMap.keySet()) {
			if(this.statsMap.get(s).getIsActive())
				des.add(s);
		}
		return des;
	}
	
	public List<String> retrieveActiveListStats() {
		if (this.innerCat != null) 
			return this.innerCat.retrieveActiveListStats();
			
		if(statsMap == null)
			return null; //Should this return an empty list? 
		List<String> des = new ArrayList<String>();
		for (Stat s : this.statsMap.values()) {
			if(s.getIsActive())
				des.add(s.getObjName());
		}
		return des;
	}
	
	public List<String> retrieveCompleteListStats() {
		if(this.innerCat != null)
			return this.innerCat.retrieveCompleteListStats();
		
		List<String> des = new ArrayList<String>();
		for (Stat s : this.statsMap.values()) {
			des.add(s.getObjName());
		}
		return des;
	}
	
	public List<String> retrieveCompleteListNames() {
		if (this.innerCat != null) 
			return this.innerCat.retrieveCompleteListNames();
		List<String> des = new ArrayList<String>();
		for(String s : this.statsMap.keySet()) {
			des.add(s);
		}
		return des;
	}
	
	public HashMap<String, Stat> getStatsMap() {
		return statsMap;
	}
	public void setStatsMap(HashMap<String, Stat> statsMap) {
		this.statsMap = statsMap;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getObjectName() {
		return this.objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	public Boolean getIsArray() {
		return this.isArray;
	}
	public void setIsArray(Boolean isArray) {
		this.isArray = isArray;
	}

	
	public SettingsCategories getInnerCat() {
		return this.innerCat;
	}
	public void setInnerCat(SettingsCategories newInnerCat) {
		this.innerCat = newInnerCat;
	}
}

