package json_Parsing.json_Class.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import json_Parsing.json_Class.JSONClass;
import json_Parsing.json_Class.JSONPlayer;

public class JSONClassDeserializer extends JsonDeserializer<JSONClass> {

    @Override
    public JSONClass deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    	JSONClass json = new JSONClass();
    	ObjectMapper mapper = new ObjectMapper()
    			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	while (!jp.isClosed() && jp.nextToken()!= JsonToken.END_OBJECT) {
    		if(JsonToken.FIELD_NAME.equals(jp.getCurrentToken())) {
    			String name = jp.getCurrentName();
    			jp.nextToken();
    			switch (name) {
	    			case "fightName":
	    				json.setFightName(jp.getValueAsString());
	    				break;
	    			case "success":
	    				json.setSuccess(jp.getValueAsBoolean());
	    				break;
	    			case "isCM":
	    				json.setIsCM(jp.getValueAsBoolean());
	    				break;
	    				
	    			case "players":
	    				int squadSize = 0; 
	    				/*jp token is currently pointing to Start_Array. "["
	    				  inside are objects of player. Find the correct player by checking
	    				  the fields: "name" and "account" 
	    				*/
	    				Boolean found = false;
	    				while(found == false && jp.nextToken()!= JsonToken.END_ARRAY) {
	    					if(jp.currentToken()!= JsonToken.START_OBJECT) {
	    						System.out.println("ERROR: Hey, the player search is NOT an object!!");
	    						return json;
	    					}
	    					//findPlayer p = jp.readValueAs(findPlayer.class);
	    					
	    					//Create a treeNode of the player to check desired info: 
	    					//TODO: Is this really more efficient than just creating player objects?
	    					TreeNode tree = mapper.readTree(jp);
	    					String tName = tree.get("name").toString();
	    					String tAccount = tree.get("account").toString().replace("\"", "");
	    					
	    					if (tName.equals(json.getPlayerName()) || tAccount.equals(json.getPlayerName())) {
	    						json.setPlayer(mapper.treeAsTokens(tree).readValueAs(JSONPlayer.class));
		    					System.out.println("Found player: " + tName + ", " + tAccount + " should equal: " + json.getPlayerName());
	    						found = true;
	    					}
	    					squadSize++;
	    					jp.skipChildren();
	    				}
	    				if (found == false) //Player doesn't exist
	    					return null;
	    				//skip the rest of the player objects. 
	    				while(jp.nextToken()!= JsonToken.END_ARRAY) {
	    					squadSize++;
	    					jp.skipChildren();
	    				}
	    				json.setSquadSize(squadSize);
	    				
	    				
	    				break;
	    			default:
	    				//if Start_array or Start_Object, skip. No need to check, if not it will do nothing.
	    				jp.skipChildren();  
    			}
    			//System.out.println("field name is: " + name + "= " + jp.getValueAsDouble());
    		}
    	}
        return json;
    }
}
