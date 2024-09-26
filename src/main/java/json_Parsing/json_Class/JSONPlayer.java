package json_Parsing.json_Class;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import json_Parsing.json_Class.deserializers.*;

@JsonDeserialize(using = PlayerDeserializer.class)
public class JSONPlayer {
	private String account = "";
	private int group;
	private Boolean hasCommanderTag;
	private String profession;
	private Boolean friendlyNPC;
	private Boolean notInSquad;
	private String guildID;
	
	private List<String> weapons;
	
	private String name = "";
	// TODO: Other values here, but check what type they are!.
	
	
	//The buff fields. The "offGroupBuffs" fields are not really needed.
	//Organized as Map<id, buffData<Item, value>>. 
	//So, if you want boon id 10's generation: buffUptimes.get(10).get("generation"). 
	@JsonDeserialize(using = MapBuffDeserializer.class)
	public  Map<Integer, Map<String, Double>> buffUptimes = null;
	
	@JsonDeserialize(using = MapBuffDeserializer.class)
	private Map<Integer, Map<String, Double>> buffUptimesActive = null;
	
	@JsonDeserialize(using = MapBuffDeserializer.class)
	private Map<Integer, Map<String, Double>> selfBuffs = null;
	
	@JsonDeserialize(using = MapBuffDeserializer.class)
	private Map<Integer, Map<String, Double>> selfBuffsActive = null;
	
	@JsonDeserialize(using = MapBuffDeserializer.class)
	private Map<Integer, Map<String, Double>> groupBuffs = null;
	
	@JsonDeserialize(using = MapBuffDeserializer.class)
	private Map<Integer, Map<String, Double>> groupBuffsActive = null;
	
	@JsonDeserialize(using = MapBuffDeserializer.class)
	private Map<Integer, Map<String, Double>> squadBuffs = null;
	
	@JsonDeserialize(using = MapBuffDeserializer.class)
	private Map<Integer, Map<String, Double>> squadBuffsActive = null;
	
	
	
	//The major objects inside of arrays. 	
	//@JsonDeserialize(using = MapDeserializer.class)
	private Map<String, Object> support = null;
	
	private Map<String, Object> dpsAll = null;
	
	private Map<String, Object> statsAll = null;

	private Map<String, Object> defenses = null;
	
	//outgoing Healing stats, extracted from extHealingStats. 
	@JsonDeserialize(using = MapHealingDeserializer.class)
	private Map<String, Object> extHealingStats = null;
	public Map<String, Object> getHealingStats() {
		return extHealingStats;
	}
	
	//deathTime
	private long deathTime = 0; //Time of death, in MS.


 	public Map<Integer, Map<String, Double>> getBuffCategoryByString(String str) {
		switch (str) {
		case "buffUptimes":
			return buffUptimes;
		case "buffUptimesActive":
			return buffUptimesActive;
		case "selfBuffs":
			return selfBuffs;
		case "selfBuffsActive":
			return selfBuffsActive;
		case "groupBuffs":
			return groupBuffs;
		case "groupBuffsActive":
			return groupBuffsActive;
		case "squadBuffs":
			return squadBuffs;
		case "squadBuffsActive":
			return squadBuffsActive;
		default:
			return null;
		}
	}

	//TODO: healing stats?
	public Map<String, Object> getCategoryMapByString(String str) {
		switch (str) {
		case "support":
			return support;
		case "dpsAll":
			System.out.println("Hey, you should be returning dpsAll...");
			return dpsAll;
		case "statsAll":
			return statsAll;
		case "defenses":
			return defenses;
		default:
			return null;
		}
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public Boolean getHasCommanderTag() {
		return hasCommanderTag;
	}

	public void setHasCommanderTag(Boolean hasCommanderTag) {
		this.hasCommanderTag = hasCommanderTag;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Boolean getFriendlyNPC() {
		return friendlyNPC;
	}

	public void setFriendlyNPC(Boolean friendlyNPC) {
		this.friendlyNPC = friendlyNPC;
	}

	public Boolean getNotInSquad() {
		return notInSquad;
	}

	public void setNotInSquad(Boolean notInSquad) {
		this.notInSquad = notInSquad;
	}

	public String getGuildID() {
		return guildID;
	}

	public void setGuildID(String guildID) {
		this.guildID = guildID;
	}

	public List<String> getWeapons() {
		return weapons;
	}

	public void setWeapons(List<String> weapons) {
		this.weapons = weapons;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Integer, Map<String, Double>> getBuffUptimes() {
		return buffUptimes;
	}

	public void setBuffUptimes(Map<Integer, Map<String, Double>> buffUptimes) {
		this.buffUptimes = buffUptimes;
	}

	public Map<Integer, Map<String, Double>> getBuffUptimesActive() {
		return buffUptimesActive;
	}

	public void setBuffUptimesActive(Map<Integer, Map<String, Double>> buffUptimesActive) {
		this.buffUptimesActive = buffUptimesActive;
	}

	public Map<Integer, Map<String, Double>> getSelfBuffs() {
		return selfBuffs;
	}

	public void setSelfBuffs(Map<Integer, Map<String, Double>> selfBuffs) {
		this.selfBuffs = selfBuffs;
	}

	public Map<Integer, Map<String, Double>> getSelfBuffsActive() {
		return selfBuffsActive;
	}

	public void setSelfBuffsActive(Map<Integer, Map<String, Double>> selfBuffsActive) {
		this.selfBuffsActive = selfBuffsActive;
	}

	public Map<Integer, Map<String, Double>> getGroupBuffs() {
		return groupBuffs;
	}

	public void setGroupBuffs(Map<Integer, Map<String, Double>> groupBuffs) {
		this.groupBuffs = groupBuffs;
	}

	public Map<Integer, Map<String, Double>> getGroupBuffsActive() {
		return groupBuffsActive;
	}

	public void setGroupBuffsActive(Map<Integer, Map<String, Double>> groupBuffsActive) {
		this.groupBuffsActive = groupBuffsActive;
	}

	public Map<Integer, Map<String, Double>> getSquadBuffs() {
		return squadBuffs;
	}

	public void setSquadBuffs(Map<Integer, Map<String, Double>> squadBuffs) {
		this.squadBuffs = squadBuffs;
	}

	public Map<Integer, Map<String, Double>> getSquadBuffsActive() {
		return squadBuffsActive;
	}

	public void setSquadBuffsActive(Map<Integer, Map<String, Double>> squadBuffsActive) {
		this.squadBuffsActive = squadBuffsActive;
	}

	public Map<String, Object> getSupport() {
		return support;
	}

	public void setSupport(Map<String, Object> support) {
		this.support = support;
	}

	public Map<String, Object> getDpsAll() {
		return dpsAll;
	}

	public void setDpsAll(Map<String, Object> dpsAll) {
		this.dpsAll = dpsAll;
	}

	public Map<String, Object> getStatsAll() {
		return statsAll;
	}

	public void setStatsAll(Map<String, Object> statsAll) {
		this.statsAll = statsAll;
	}

	public Map<String, Object> getDefenses() {
		return defenses;
	}

	public void setDefenses(Map<String, Object> defenses) {
		this.defenses = defenses;
	}

	public Map<String, Object> getExtHealingStats() {
		return extHealingStats;
	}

	public void setExtHealingStats(Map<String, Object> extHealingStats) {
		this.extHealingStats = extHealingStats;
	}

	public long getDeathTime() {
		return deathTime;
	}

	public void setDeathTime(long deathTime) {
		this.deathTime = deathTime;
	}

	
	
}
