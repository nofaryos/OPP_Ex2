package api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * This class implements the interface of JsonSerializer.
 * This class converts an edge_data object to Json String
 */

public class EdgeJsonSerialize implements JsonSerializer<edge_data> {

    /**
     * This function converts a edge_data object to Json String.
     * @param edge
     * @param type
     * @param jsonSerializationContext
     * @return JsonElement
     */

    @Override
    public JsonElement serialize(edge_data edge, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject edgeJson = new JsonObject();
        edgeJson.addProperty("src", edge.getSrc());
        edgeJson.addProperty("w", edge.getWeight());
        edgeJson.addProperty("dest",edge.getDest());
        return edgeJson;
    }
}
