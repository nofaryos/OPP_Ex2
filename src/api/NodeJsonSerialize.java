package api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * This class implements the interface of JsonSerializer.
 * This class converts a node_data object to Json String
 */

public class NodeJsonSerialize implements JsonSerializer<node_data> {

    /**
     * This function converts a edge_data object to Json String.
     * @param node
     * @param type
     * @param jsonSerializationContext
     * @return JsonElement
     */

    @Override
    public JsonElement serialize(node_data node, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject nodeJson = new JsonObject();
        nodeJson.addProperty("pos", node.getLocation().toString());
        nodeJson.addProperty("id", node.getKey());
        return nodeJson;
    }
}
