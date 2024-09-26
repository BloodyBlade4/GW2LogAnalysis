package settings;

import java.util.ArrayList;
import java.util.List;

import components.Buff;

public class SettingsBuff {
	//use constructor to set these value, 
	//the gui will not present the phase option if the boolean is null. 
	Boolean phaseDuration = null;
	Boolean phaseActiveDuration = null;
	Boolean uptime = null;
	Boolean generationSelf = null;
	Boolean generationGroup = null;
	Boolean generationSquad = null;
	String displayName = "";
	String dbName = "";
	
	//buff id and isActive. 
	List<Buff> buffsList = new ArrayList<>();

	public SettingsBuff(){
	
	}
	public SettingsBuff(Boolean phaseDuration, Boolean phaseActiveDuration, Boolean uptime, Boolean generationSelf,
			Boolean generationGroup, Boolean generationSquad, String displayName, 
			String dbName, List<Buff> buffsList) {
		super();
		this.phaseDuration = phaseDuration;
		this.phaseActiveDuration = phaseActiveDuration;
		this.uptime = uptime;
		this.generationSelf = generationSelf;
		this.generationGroup = generationGroup;
		this.generationSquad = generationSquad;
		this.displayName = displayName;
		this.dbName = dbName;
		this.buffsList = buffsList;
	}
	
	
	public List<String> retrieveBuffNamesList() {
		List<String> res = new ArrayList<>();
		for (Buff b : this.buffsList) {
			res.add(b.getDisplayName());
		}
		return res;
	}
	public List<String> retrieveActiveBuffNamesList() {
		List<String> res = new ArrayList<>();
		for (Buff b : this.buffsList) {
			if(b.getIsActive())
				res.add(b.getDisplayName());
		}
		return res;
	}
	public List<Integer> retrieveActiveBuffIDList() {
		List<Integer> res = new ArrayList<>();
		for (Buff b : this.buffsList) {
			if(b.getIsActive())
				res.addAll(b.getIds());
		}
		return res;
	}
	
	public List<Buff> retrieveActiveBuffList() {
		List<Buff> res = new ArrayList<>();
		for(Buff b : this.buffsList) {
			if(b.getIsActive())
				res.add(b);
		}
		return res;
	}
	
	public void updateActiveByNames(List<String> list) {
		for (int i = 0; i < this.buffsList.size(); i++) {
			this.buffsList.get(i).setIsActive(list.contains(
					this.buffsList.get(i).getDisplayName()
					));
		}
	}
	
	
	
	public String getDbName() {
		return dbName;
	}
	public List<Buff> getBuffsList() {
		return buffsList;
	}
	public void setBuffsList(List<Buff> buffsList) {
		this.buffsList = buffsList;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayname) {
		this.displayName = displayname;
	}
	public Boolean getPhaseDuration() {
		return phaseDuration;
	}

	public void setPhaseDuration(Boolean phaseDuration) {
		this.phaseDuration = phaseDuration;
	}

	public Boolean getPhaseActiveDuration() {
		return phaseActiveDuration;
	}

	public void setPhaseActiveDuration(Boolean phaseActiveDuration) {
		this.phaseActiveDuration = phaseActiveDuration;
	}

	public Boolean getUptime() {
		return uptime;
	}

	public void setUptime(Boolean uptime) {
		this.uptime = uptime;
	}

	public Boolean getGenerationSelf() {
		return generationSelf;
	}

	public void setGenerationSelf(Boolean generationSelf) {
		this.generationSelf = generationSelf;
	}

	public Boolean getGenerationGroup() {
		return generationGroup;
	}

	public void setGenerationGroup(Boolean generationGroup) {
		this.generationGroup = generationGroup;
	}

	public Boolean getGenerationSquad() {
		return generationSquad;
	}

	public void setGenerationSquad(Boolean generationSquad) {
		this.generationSquad = generationSquad;
	}
	
	
}
