package api;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * This class implements the interface of JsonSerializer and JsonDeserializer.
 */

public class graphJson implements JsonDeserializer<directed_weighted_graph> , JsonSerializer<directed_weighted_graph> {

    /**
     * This function converts a Json String to directed_weighted_graph object.
     * @param json
     * @param type
     * @param jsonDeserializationContext
     * @return directed_weighted_graph
     */

    @Override
    public directed_weighted_graph deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject graphJson = json.getAsJsonObject();
        JsonArray edges = graphJson.get("Edges").getAsJsonArray();
        JsonArray nodes = graphJson.get("Nodes").getAsJsonArray();
        directed_weighted_graph g = new DWGraph_DS();

        for (JsonElement nodeJson: nodes) {
            JsonObject n = nodeJson.getAsJsonObject();
            String pos = n.get("pos").getAsString();
            String[] posSplit = pos.split(",");
            geoLocation gl = new geoLocation(Double.parseDouble(posSplit[0]),
                    Double.parseDouble(posSplit[1]),Double.parseDouble(posSplit[2]));
            int key = n.get("id").getAsInt();
            node_data node = new NodeData(key,gl);
            g.addNode(node);
        }
        for (JsonElement edgeJson: edges) {
            JsonObject e = edgeJson.getAsJsonObject();
            int src = e.get("src").getAsInt();
            int dest = e.get("dest").getAsInt();
            double w = e.get("w").getAsDouble();
            g.connect(src , dest ,w);
        }
        return g;
    }

    /**
     * This function converts a directed_weighted_graph object to Json String.
     * @param graph
     * @param type
     * @param jsonSerializationContext
     * @return JsonElement
     */

    @Override
    public JsonElement serialize(directed_weighted_graph graph, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject graphJson =  new JsonObject();
        JsonArray nodeJson = new JsonArray(graph.nodeSize());
        JsonArray edgeJson = new JsonArray(graph.edgeSize());
        NodeJsonSerialize node = new NodeJsonSerialize();
        EdgeJsonSerialize edge = new EdgeJsonSerialize();

        for (node_data n: graph.getV()) {
            nodeJson.add(node.serialize(n , type,  jsonSerializationContext));
            for (edge_data e: graph.getE(n.getKey())) {
                edgeJson.add(edge.serialize(e, type, jsonSerializationContext));
            }
        }
        graphJson.add("Edges",edgeJson);
        graphJson.add("Nodes",nodeJson);
        return graphJson;
    }
}
