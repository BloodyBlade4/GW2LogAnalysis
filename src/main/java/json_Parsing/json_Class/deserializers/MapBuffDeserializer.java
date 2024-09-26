package json_Parsing.json_Class.deserializers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

//id, then generation/uptime (depending on what's available)
public class MapBuffDeserializer extends StdDeserializer< Map<Integer, Map<String, Double>>> {

    private static final long serialVersionUID = 1L;

    protected MapBuffDeserializer() {
        super(Map.class);
    }

    /* All buff lists are arranged inside the buff array[] as: 
     * {
     * 		"id": ####,
     * 		"buffData": [
     * 			{
     * 				!!!Only certain info here is desired! 
     * 			}
     * 		]
     * 
     * Refer to the BuffDataClass for more details on buffData.
     * 
     */
    //Another issue is that the Object mapper is created multiple times every log just to do another parse... Perhaps we could just go through the values?
    @Override
    public Map<Integer, Map<String, Double>> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException { //
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
