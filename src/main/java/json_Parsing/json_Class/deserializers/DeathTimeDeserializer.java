package json_Parsing.json_Class.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DeathTimeDeserializer  extends StdDeserializer<Long> {

    private static final long serialVersionUID = 1L;

    protected DeathTimeDeserializer() {
        super(Long.class);
    }
    @Override
    public Long deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException { 
    	while (jp != null && jp.nextToken()!= JsonToken.END_OBJECT) {
    		if(JsonToken.FIELD_NAME.equals(jp.getCurrentToken())) {
    			if (jp.currentName().equals("deathTime")) {
    				jp.nextToken();
    				return jp.getLongValue();
    			}
    		}
    	}
    	
    	return 0l;
    }
}
