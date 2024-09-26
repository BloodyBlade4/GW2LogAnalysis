package json_Parsing.json_Class.deserializers;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class MapHealingDeserializer extends StdDeserializer<Map<String, Object>> {

    private static final long serialVersionUID = 1L;

    protected MapHealingDeserializer() {
        super(Map.class);
    }

    //The object with be an item inside of "extHealingStats" 
    @Override
    public Map<String, Object> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException { 
    	while(jp.nextToken() != JsonToken.END_OBJECT) {
    		if(JsonToken.FIELD_NAME.equals(jp.getCurrentToken())) {
    			if (jp.currentName().equals("outgoingHealing")) {
    				System.out.println("Current name is: " + jp.currentName());
    				jp.nextToken(); //go to the start of the array
    				jp.nextToken(); //go to the start of the object.
    				Object o = jp.readValueAs(Object.class);
    				System.out.println("object is " + o.toString());
    				ObjectMapper objectMapper = new ObjectMapper();
    				return objectMapper
    			      .convertValue(o, new TypeReference<Map<String, Object>>() {});
    			}
    			else {
    				jp.nextToken();
    				jp.skipChildren();
    			}
    				
    		}
    	}
    	return null;
    }
}
