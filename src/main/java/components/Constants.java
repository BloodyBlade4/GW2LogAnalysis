package components;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import settings.SettingsBuff;
import settings.SettingsCategories;

public final class Constants {
	public final static String DATE_FORMAT = "yyyy-mm-dd";
	
	public final static String EFFECT_IDS_FILE = System.getProperty("user.dir") +"\\src\\resources\\constEffectIDs.properties";
	
	
	public final static String GW2_APP_DATA_FILE = System.getenv("APPDATA") + "\\GW2LogAnalysis";
	
	public static int retrieveEncounterIDInt(String location, String key) {
		for (Entry<Integer, String> entry : ENCOUNTER_IDS.get(location).entrySet()) {
	        if (key.equals(entry.getValue())) 
	            return entry.getKey();
	    }
		return 0;
	}
	
	public static Map<String, HashMap<Integer, String>> ENCOUNTER_IDS = new HashMap<String, HashMap<Integer, String>>(){
		private static final long serialVersionUID = 1L;

	{
		put("Any", new HashMap<Integer, String>() {
			private static final long serialVersionUID = 1L;

		{
			put(0,"");
		}});
		put("Raids", 
			new HashMap<Integer, String>() {
				private static final long serialVersionUID = 1L;

			{
				put(0,"Any");
				//Wing 1
				put(0x020101,"Vale Guardian");
				put(0x020102,"Gorseval");
				put(0x020103,"Sabetha");
					//Wing 2
				put(0x020201,"Slothasor");
				put(0x020202,"Bandit Trio");
				put(0x020203,"Matthias");
					//Wing 3
				put(0x020301,"Escort");
				put(0x020302,"Keep Construct");
				put(0x020303,"Twisted Castle");
				put(0x020304,"Xera");
					//Wing 4
				put(0x020401,"Cairn");
				put(0x020402,"Mursaat Overseer");
				put(0x020403,"Samarog");
				put(0x020404,"Deimos");
					//Wing 5
				put(0x020501,"Soulless Horror");
				put(0x020502,"River of Souls");
				put(0x020503,"Broken King");
				put(0x020504,"Soul Eater");
				put(0x020505,"Eye of Judgement/Eye of Fate");
				put(0x020506,"Dhuum");
					//Wing 6
				put(0x020601,"Conjured Amalgamate");
				put(0x020602,"Twin Largos");
				put(0x020603,"Qadim");
					//Wing 7
				put(0x020701,"Adina");
				put(0x020702,"Sabir");
				put(0x020703,"Qadim the Peerless");
			}}
		);
		put("Fractals", 
			new HashMap<Integer, String>() {
				private static final long serialVersionUID = 1L;

			{
				put(0,"Any");
					//Nightmare
				put(0x030101,"MAMA");
				put(0x030102,"Siax");
				put(0x030103,"Ensolyss");
					//Shattered Observatory
				put(0x030201,"Skorvald");
				put(0x030202,"Artsariiv");
				put(0x030203,"Arkk");
					//Sunqua Peak
				put(0x030301,"Ai, Elemental and Dark");
				put(0x030302,"Ai, Elemental Only");
				put(0x030303,"Ai, Dark Only");
			}}
		);
		put("Strikes", 
			new HashMap<Integer, String>() {
				private static final long serialVersionUID = 1L;

			{
				put(0,"Any");
				//Festivals
				put(0x040101,"Freezie");
					//Icebrood Saga
				put(0x040201,"Icebrood Construct");
				put(0x040202,"Fraenir of Jormag");
				put(0x040203,"Freezie");
				put(0x040204,"Boneskinner");
				put(0x040205,"Whisper of Jormag");
				put(0x040206,"Varinia Stormsounder");
					//End of Dragons
				put(0x040301,"Aetherblade Hideout");
				put(0x040302,"Xunlai Jade Junkyard");
				put(0x040303,"Kaineng Overlook");
				put(0x040304,"Harvest Temple");
				put(0x040305,"Old Lion's Court");
			}}
		);
		put("Open World", 
				new HashMap<Integer, String>() {
					private static final long serialVersionUID = 1L;

				{
					put(0,"Any");
					put(0x050401,"Soo-Won");
				}}
			);
		put("Story Instance", 
				new HashMap<Integer, String>() {
					private static final long serialVersionUID = 1L;

				{
					put(0,"Any");
					put(0x060201,"Mordremoth");
				}}
			);
		put("World vs. World", 
			new HashMap<Integer, String>() {
				private static final long serialVersionUID = 1L;

			{
				put(0,"Any");
				put(0x070100,"Eternal Battlegrounds");
				put(0x070200,"Green Alpine Borderlands");
				put(0x070300,"Blue Alpine Borderlands");
				put(0x070400,"Red Desert Borderlands");
				put(0x070500,"Obsidian Sanctum");
				put(0x070600,"Edge of the Mists");
				put(0x070700,"Armistice Bastion");
			}}
		);
		put("Golem", 
			new HashMap<Integer, String>() {
				private static final long serialVersionUID = 1L;

			{
				put(0,"Any");
				put(0x080101,"Massive Golem 10M");
				put(0x080102,"Massive Golem 4M");
				put(0x080103,"Massive Golem 1M");
				put(0x080104,"Vital Golem");
				put(0x080105,"Average Golem");
				put(0x080106,"Standard Golem");
				put(0x080107,"Condition Golem");
				put(0x080108,"Power Golem");
				put(0x080109,"Large Golem");
				put(0x08010A,"Medium Golem");
			}}
		);
	}};
	
	public static List<String> WEAPONS = Arrays.asList("Any","Axe","Dagger","Mace","Pistol","Sword","Scepter","Focus","Shield","Torch","Warhorn","Greatsword","Hammer",
			"Longbow","Rifle","Short bow","Staff"/* aquatic weapons,"Harpoon gun","Spear","Trident"*/);
	
	//Professions and specializations:
	public static Map<String, List<String>> profSpec = new HashMap<String, List<String>>() {
		private static final long serialVersionUID = 1L;

	{
		put("Any", Arrays.asList(""));
		put("Guardian", Arrays.asList("Guardian","Dragonhunter","Firebrand","Willbender"));
		put("Revenant",Arrays.asList("Revenant", "Herald","Renegade","Vindicator"));
		put("Warrior",Arrays.asList("Warrior","Berserker","Spellbreaker","Bladesworn"));
		
		put("Engineer",Arrays.asList("Engineer","Scrapper","Holosmith","Mechanist"));
		put("Ranger",Arrays.asList("Ranger","Druid","Soulbeast","Untamed"));
		put("Thief",Arrays.asList("Thief","Daredevil","Deadeye","Specter"));
		
		put("Elementalist", Arrays.asList("Elementalist", "Tempest", "Weaver", "Catalyst"));
		put("Mesmer",Arrays.asList("Mesmer","Chronomancer","Mirage","Virtuoso"));
		put("Necromancer",Arrays.asList("Necromancer","Reaper","Scourge","Harbinger"));
	}};
	
	public static List<Buff> generateBuffListWithFilter(String directory, String filter) {
		List<Buff> buffList = new ArrayList<>();	
		try(InputStream input = Constants.class.getResourceAsStream("/constProfessionBuffIDs.properties")){
            Properties prop = new Properties();
            prop.load(input);
            for (Object key : prop.keySet()) {
            	String name = key.toString();
            	if (filter.equals("Any") || name.contains(filter.toLowerCase()))
            		buffList.add( new Buff(Integer.parseInt(name.replaceAll("[^0-9]", "")), prop.getProperty(name)) );
            }
		}
	    catch (IOException ex) {
	        ex.printStackTrace();
	    }
		return buffList;
	}
	
	public static List<SettingsCategories> categoriesGenerateDefaults() {		
		return Arrays.asList(
			new SettingsCategories("General", "general", false, new HashMap<String, Stat>() {
				private static final long serialVersionUID = 1L;

			{
				//put("Fight_Name", new Stat("fightName"));Moved to be inserted as default if not selected.
				put("Percent_Time_Alive", new Stat("percentTimeAlive"));
				put("Sucess_Status", new Stat("success")); //Useful in raids/fractals
				put("Duration_Sec", new Stat("durationMS")); 
				put("Is_Challenge_Mote", new Stat("isCM"));
				put("Start_Time_Std", new Stat("timeStart"));
				put("End_Time_Std", new Stat("timeEnd"));
			}}),
			new SettingsCategories("Player", "player", false, new HashMap<String, Stat>() {
				private static final long serialVersionUID = 1L;

			{
				put("Has_Commander_Tag", new Stat("hasCommanderTag"));
			}} ),
			new SettingsCategories("General_2", "statsAll", true, new HashMap<String, Stat>() {
				private static final long serialVersionUID = 1L;

			{
				put("Wasted_Count", new Stat("wasted", "Number of times you interrupted your cast."));
				put("Time_Wasted", new Stat("timeWasted", "Time wasted(in sec) interupting your own skill casts"));
				
				
				put("Distance_to_Commander", new Stat("distToCom"));
				put("Distance_to_Stack", new Stat("stackDist")); //Distance to center of squad
				put("Average_Boons", new Stat("avgBoons"));
				put("Average_Conditions", new Stat("avgConditions"));
				put("Weapon_Swap_Count", new Stat("swapCount"));
				put("total_Damage", new Stat("totalDmg")); //? taken?
				put("Flanking_Rate", new Stat("flankingRate"));
				put("Missed", new Stat("missed"));			//?
				put("Evaded_by_Enemies", new Stat("evaded")); //?
				put("Blocked_by_Enemies", new Stat("blocked")); //?
				put("Interrupts", new Stat("interrupts")); //?
				put("Killed", new Stat("killed")); //??
				put("Downed", new Stat("downed")); //?
			}} ),
			new SettingsCategories("Defenses", "defenses", true, new HashMap<String, Stat>() {
				private static final long serialVersionUID = 1L;

			{
				put("Damage_Taken", new Stat("damageTaken"));
				put("Blocked_Count", new Stat("blockedCount"));
				put("Evaded_Count", new Stat("evadedCount"));
				put("Missed_Count", new Stat("missedCount"));
				put("Dodge_Count", new Stat("dodgeCount"));
				put("Invuled_Count", new Stat("invulnedCount"));
				put("Damage_Barrier", new Stat("damageBarrier"));
				put("Self_Downed_Count", new Stat("downCount"));
				put("Self_Downed_Duration_ms", new Stat("downDuration"));
				put("Self_Dead_Count", new Stat("deadCount"));
				put("Self_Dead_Duration_ms", new Stat("deadDuration"));
				put("Boon_Strips_Taken", new Stat("boonStrips")); //?
				put("Boon_Strips_Taken_Time", new Stat("boonStripsTime")); //?
				put("Condition_Cleanses_Taken", new Stat("conditionCleanses")); //?
				put("Condition_Cleanses_Taken_Time", new Stat("conditionCleansesTime")); //?
				
			}} ),
			
			new SettingsCategories("Extended_Healing_Stats", "extHealingStats", false, 
				new SettingsCategories("Outgoing_Healing", "outgoingHealing", true, new HashMap<String, Stat>() {
					private static final long serialVersionUID = 1L;

				{
					put("HPS", new Stat("hps"));
					put("Healing", new Stat("healing"));
					put("Healing_Power_HPS", new Stat("healingPowerHps"));
					put("Healing_Power_Healing", new Stat("healingPowerHealing"));
					put("Conversion_HPS", new Stat("conversionHps"));
					put("Conversion_Healing", new Stat("conversionHealing"));
					put("hybrid_HPS", new Stat("hybridHps"));
					put("Hybrid_Healing", new Stat("hybridHealing"));
					put("Downed_HPS", new Stat("downedHps"));
					put("Downed_Healing", new Stat("downedHealing"));
				}} )
			),
			new SettingsCategories("Support", "support", true, new HashMap<String, Stat>() {
				private static final long serialVersionUID = 1L;

			{
				put("Resurrects", new Stat("resurrects"));
				put("Resurrect_Time", new Stat("resurrectTime"));
				put("Condition_Cleanse_total", new Stat("condiCleanse")); //This total?
				put("Condition_Cleanse_Time", new Stat("condiCleanseTime")); //?
				put("Condition_Cleanse_Self", new Stat("condiCleanseSelf"));
				put("Condition_Cleanse_Time_Self", new Stat("condiCleanseTimeSelf")); //?
				put("Boon_Strips", new Stat("boonStrips")); //?
				put("Boon_Strips_Time", new Stat("boonStripsTime")); //?
			}} ),
			new SettingsCategories("Damage", "dpsAll", true, new HashMap<String, Stat>() {
					private static final long serialVersionUID = 1L;

				{
					put("DPS", new Stat("dps"));
					put("Damage_Total", new Stat("damage"));
					put("Condition_DPS", new Stat("condiDps"));
					put("Condition_Damage", new Stat("condiDamage"));
					put("Power_DPS", new Stat("powerDps"));
					put("Power_Damage", new Stat("powerDamage"));
					put("Breakbar_Damage", new Stat("breakbarDamage"));
				}} 
			)
				
		);
	}
	
	public static List<SettingsBuff> buffsCategoriesGenerateDefaults() {

		List<SettingsBuff> res = new ArrayList<>();
		HashMap<String, List<Buff>> cats = new HashMap<String, List<Buff>>();
		
		
		//try (InputStream input = new FileInputStream(System.getProperty("user.dir") +"\\src\\resources\\constBuffIDs.properties")) {
		try (InputStream input = Constants.class.getResourceAsStream("/constBuffIDs.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            for (Object key: prop.keySet()) {
            	String name = key.toString();
            	
            	String catName = name.replaceAll("\\d","");
            	int idInt = Integer.parseInt(name.replaceAll("[^0-9]", ""));
            	Boolean found = false;
            	
            	
            	if(cats.containsKey(catName)) {
            		for(Buff b : cats.get(catName)) {
            			if(b.getDisplayName().equals(prop.getProperty(name))) {
            				//System.out.println("The id is found for " + idInt);
            				b.addId(idInt);
            				found = true;
            				break;
            			}
            		}
            	}
            	if(!found) {
            	cats.computeIfAbsent(catName, k -> new ArrayList<>())
            		.add(new Buff(idInt, prop.getProperty(name)));
            	}
            }
		}
	    catch (IOException ex) {
	        ex.printStackTrace();
	    }
		
		for(String key : cats.keySet()) {
			if (key.contains("boons") || key.contains("offensiveBuffs") || key.contains("supportBuffs") || key.contains("defensiveBuffs")) {
				res.add(new SettingsBuff(true, true, true, true, true, true, key.substring(3, key.length()-1),key, cats.get(key)));
			}
			else if(key.contains("conditions") || key.contains("gearBuffs") || key.contains("Debuffs")) {
				res.add(new SettingsBuff(true, true, null, null, null, null, key.substring(3, key.length()-1),key, cats.get(key)));
			}
			else {
				res.add(new SettingsBuff(null, null, null, null, null, null, key.substring(3, key.length()-1),key, cats.get(key)));
			}
		}
		return res;
	}
	
	public static HashMap<String, List<String>> buffsCategoriesgetMap() {
		HashMap<String, List<String>> cats = new HashMap<String, List<String>>();
		
		try (InputStream input = new FileInputStream(System.getProperty("user.dir") +"\\src\\resources\\constBuffIDs.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            for (Object key: prop.keySet()) {
            	String name = key.toString();
            	cats.computeIfAbsent(name.replaceAll("\\d",""), k -> new ArrayList<>()).add(prop.getProperty(name));
            }
		}
	    catch (IOException ex) {
	        ex.printStackTrace();
	    }
		
		//SettingsBuffs(Boolean phaseDuration, Boolean phaseActiveDuration, Boolean uptime, Boolean generationSelf,
		//Boolean generationGroup, Boolean generationSquad, Boolean display, List<String> buffs)
		return cats;
		
		
	}

	
	public static List<Integer> BUFF_IS_PERCENT = Arrays.asList(
			//boons
			725,
			1187,
			30328,
			717,
			16537,
			718,17495,17674,16538,
			39825,
			726,16540,
			743,
			719,768,
			26980,
			873,
			
			//offensive buffs
			31487,
			
			//support buffs
			13017,26142,32747,58026,
			10269,
			890,
			5974,
			10332,
			5677,
			5579,
			25518,
			68927,
			17848,
			5684,
			5577,
			52014,
			52740,
			52218,
			33794,
			32477,
			33249,
			33375,
			33791,
			32928,
			33010,
			33594,
			20893,
			51674,
			10346,
			
			//defensive buffs
			26596, 33330,
			27737,
			//a lot more to add here.
			30285,
			29726,
			5587,
			24304,
			31337,
			
			
			//conditions
			720,
			722,
			721,
			791,896,
			727,
			26766,
			742
			
			);
}
