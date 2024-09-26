package json_Parsing.json_Class.deserializers;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class MapDeserializer extends StdDeserializer<Map<String, Object>> {

    private static final long serialVersionUID = 1L;

    protected MapDeserializer() {
        super(Map.class);
    }

    //The object will usually come as the first index in an array, but we need to double check that! This could handle more than one instance.
    //Another issue is that the Object mapper is created multiple times every log just to do another parse... Perhaps we could just go through the values?
    @Override
    public Map<String, Object> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException { 
    	
    	Object[] o = jp.readValueAs(Object[].class);
    	
    	Object obj = o[0];
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper
	      .convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }
}
