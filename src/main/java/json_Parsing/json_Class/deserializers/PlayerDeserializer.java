package json_Parsing.json_Class.deserializers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import json_Parsing.json_Class.JSONPlayer;
import json_Parsing.json_Class.deserializers.MapBuffDeserializer.BuffDetails;

public class PlayerDeserializer  extends StdDeserializer<JSONPlayer> {

    private static final long serialVersionUID = 1L;

    protected PlayerDeserializer() {
        super(Map.class);
    }

    //The object with be an item inside of "extHealingStats" 
    @Override
    public JSONPlayer deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException { 
		ObjectMapper mapper = new ObjectMapper();
		//return objectMapper
	      //.convertValue(obj, new TypeReference<Map<String, Object>>() {});
		
    	JSONPlayer player = new JSONPlayer();
    	try {
    	while(jp.nextToken() != JsonToken.END_OBJECT) {
    		if(JsonToken.FIELD_NAME.equals(jp.getCurrentToken())) {
				System.out.println("current name " + jp.currentName());
    			switch (jp.currentName()) {
    			case "account":
    				jp.nextToken();
    				player.setAccount(jp.currentName());
    				break;
    			case "group":
    				jp.nextToken();
    				player.setGroup(jp.getIntValue());
    				break;
    			case "hasCommanderTag":
    				jp.nextToken();
    				player.setHasCommanderTag(jp.getBooleanValue());
    				break;
    			case "profession":
    				jp.nextToken();
    				player.setProfession(jp.currentName());
    				break;
    			case "friendlyNPC":
    				jp.nextToken();
    				player.setFriendlyNPC(jp.getBooleanValue());
    				break;
    			case "notInSquad":
    				jp.nextToken();
    				player.setNotInSquad(jp.getBooleanValue());
    				break;
    			case "guildID":
    				jp.nextToken();
    				player.setGuildID(jp.getCurrentName());
    				break;
    			case "weapons": //A simple array.
    				jp.nextToken(); //array start token
    				player.setWeapons(mapper.readValue(jp, new TypeReference<List<String>>() {}));
    				break;
    				
    			//Misc data types
    			case "deathRecap":
    				jp.nextToken(); //Array start.
    				jp.nextToken(); //object start.
    				player.setDeathTime(jp.readValueAs(DeathRecap.class).deathTime);
    				jp.nextToken(); //Array end.
    				break;
    				
    			//Buff types
    			case "buffUptimes":
    				jp.nextToken(); //Array start
    				System.out.println("before buff: " + jp.getCurrentToken() + ", " + jp.currentName());
    		    	player.setBuffUptimes(buffMap(jp));
    		    	System.out.println("buff map is " + player.getBuffUptimes().keySet().toString());
    		    	System.out.println("after buff; " + jp.getCurrentToken() + ", " + jp.currentName());
    		    	break;
    			case "buffUptimesActive":
    				jp.nextToken();
    				player.setBuffUptimesActive(buffMap(jp));
    				break;
    			case "selfBuffs":
    				jp.nextToken();
    				player.setSelfBuffs(buffMap(jp));
    				break;
    			case "selfBuffsActive":
    				jp.nextToken();
    				player.setSelfBuffsActive(buffMap(jp));
    				break;
    			case "groupBuffs":
    				jp.nextToken();
    				player.setGroupBuffs(buffMap(jp));
    				break;
    			case "groupBuffsActive":
    				jp.nextToken();
    				player.setGroupBuffsActive(buffMap(jp));
    				break;
    			case "squadBuffs":
    				jp.nextToken();
    				player.setSquadBuffs(buffMap(jp));
    				break;
    			case "squadBuffsActive":
    				jp.nextToken();
    				player.setSquadBuffsActive(buffMap(jp));
    				break;

        		//Coming to objects
    			case "support":
    				jp.nextToken(); //array start
    				jp.nextToken(); //object start
    				player.setSupport(mapper.readValue(jp, new TypeReference<Map<String, Object>>() {}));
    				jp.nextToken(); //go to end array.
    				break;
    			case "dpsAll":
    				jp.nextToken(); //array start
    				jp.nextToken(); //object start
    				player.setDpsAll(mapper.readValue(jp, new TypeReference<Map<String, Object>>() {}));
    				jp.nextToken(); //go to end array.
    				break;
    			case "statsAll":
    				jp.nextToken(); //array start
    				jp.nextToken(); //object start
    				player.setStatsAll(mapper.readValue(jp, new TypeReference<Map<String, Object>>() {}));
    				jp.nextToken(); //go to end array.
    				break;
    			case "defenses":
    				jp.nextToken(); //array start
    				jp.nextToken(); //object start
    				player.setDefenses(mapper.readValue(jp, new TypeReference<Map<String, Object>>() {}));
    				jp.nextToken(); //go to end array.
    				break;
    			//Default
    			default: 
    				jp.nextToken();
    				jp.skipChildren();
    				break;
    				
    				
    			}
    		}
    		else {
    			jp.nextToken();
    			//if (JsonToken.FIELD_NAME.equals(jp.getCurrentToken()))
    			jp.skipChildren();
    		}
    	}
    	} catch (Exception e) {
    		System.out.println("Error in layer deserializer!" + e);
    		e.printStackTrace();
    	}
    	return player;
    }
    public static class DeathRecap {
    	public long deathTime = 0;
    }
    
    private Map<Integer, Map<String, Double>> buffMap(JsonParser jp) 
            throws IOException, JsonProcessingException {
    	Map<Integer, Map<String, Double>> buffMap = new HashMap<Integer, Map<String, Double>>();
    	
    	BuffDetails[] buffDetails = jp.readValueAs(BuffDetails[].class);
    	for(BuffDetails bd : buffDetails) {
    		buffMap.put((int) bd.id, bd.buffData);
    	}
    	return buffMap;
    }
    
    public static class BuffDetails {
    	public long id;
    	public  Map<String, Double> buffData;
    	public void setBuffData(Object[] o) {
    		//if (o[0] instanceof Map)
			buffData =  (Map<String, Double>) o[0];
    	}
    }
}
