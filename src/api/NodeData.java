package api;

/**
 * This class implements the interface of node_data.
 * NodeData is a vertex in a directional weighted graph.
 * Each vertex has an identity number(unique key), color(info), distance(tag), weight, and location
 * that represents it in a particular graph
 */

public class NodeData implements node_data{

    private int key;
    private int tag;
    private String info;
    private double weight;
    private static int forKey = 0;
    private geo_location location;

    /**
     * Constructor to create a new NodeData:
     */

    public NodeData(){
        this.key = forKey;
        forKey++;
        this.tag = 0;
        this.info = " ";
        this.weight = 0;
        this.location = new geoLocation();
    }

    /**
     * A copy constructor.
     * @param n - node_data.
     */

    public NodeData(node_data n){
        this.key = n.getKey();
        this.tag = n.getTag();
        this.info = n.getInfo();
        if (n.getLocation() != null) {
            setLocation(n.getLocation());
        }
    }

    /**
     * Constructor to create a new NodeData from Json object:
     * @param key - unique id.
     * @param gl - location of the node
     */

    protected NodeData(int key, geoLocation gl){
        this.key=key;
        if (gl!=null) {
            setLocation(gl);
        }
        this.tag = 0;
        this.info = " ";
        this.weight = 0;
    }

    /**
     * This function return the unique key (id) associated with this node.
     * @return  key
     */

    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * This function return the location of this node in the graph.
     * @return  geo_location
     */

    @Override
    public geo_location getLocation() {
        if (this.location != null) {
            return this.location;
        }
        return null;
    }

    /**
     * This function changing the location of this node.
     * @param p - new geo_location
     */

    @Override
    public void setLocation(geo_location p) {
        this.location = new geoLocation(p);

    }

    /**
     * This function return the weight of this node.
     * @return weight
     */

    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * This function changing the weight of this node.
     * @param w - weight
     */

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    /**
     * This function return the info associated with this node.
     * @return info
     */

    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * This function changing the info associated with this node.
     * @param s
     */

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * This function return the tag associated with this node.
     * @return tag
     */

    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * This function changing the tag associated with this node.
     * @param t
     */

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    /**
     * This function returns true if two nodes are equal to each other.
     * Two nodes are equal to each other if they have the same key,
     * the same info, the same weight and the same tag.
     * @param o object
     * @return true/false
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeData)) return false;
        NodeData nodeData = (NodeData) o;
        if (this.key != nodeData.getKey()){
            return false;
        }
        if (this.tag != nodeData.getTag()){
            return false;
        }
        if (!this.info.equals(nodeData.getInfo())){
            return false;
        }
         if (this.weight != nodeData.getWeight()){
            return false;
        }
        return true;
    }

    /**
     * This function resets the keys of the nodes in the graph so that they start from zero again.
     */

    protected static void setForKey(){
        forKey=0;
    }


    /**
     * Print function
     * @return String
     */

    @Override
    public String toString() {
        return "NodeData{" +
                "key=" + key +
                ", tag=" + tag +
                ", info='" + info + '\'' +
                ", weight=" + weight +
                ", location=" + location +
                '}';
    }
}
