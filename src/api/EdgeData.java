package api;

import java.util.Objects;

/**
 * This class implements the interface of edge_data.
 * EdgeData is a directional edge(src,dest) in a (directional) weighted graph.
 * Each edge has a src node, dest node, info, tag and weight
 * that represents it in a particular graph
 */

public class EdgeData implements edge_data {

    private int src;
    private int dest;
    private double weight;
    private int tag;
    private String info;

    /**
     * Constructor to create a new EdgeData:
     * @param s - The key of the src node
     * @param d - The key of the dest node
     * @param w - The weight of the edge
     */

    public EdgeData(int s, int d, double w){
        this.src = s;
        this.dest = d;
        this.weight = w;
        this.tag = 0;
        this.info = " ";
    }

    /**
     * A copy constructor.
     * @param e - edge_data.
     */

    public EdgeData(edge_data e){
        this.src = e.getSrc();
        this.dest = e.getDest();
        this.weight = e.getWeight();
        this.tag = e.getTag();
        this.info = e.getInfo();
    }

    /**
     * This function return the key of the src node of this edge.
     * @return src
     */

    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * This function return the key of the dest node of this edge.
     * @return dest
     */

    @Override
    public int getDest() {
        return this.dest;
    }

    /**
     * This function return the weight of this edge.
     * @return weight
     */

    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * This function return the info of this edge.
     * @return info
     */

    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * This function changing the info associated with this edge.
     * @param s
     */

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * This function return the tag of this edge.
     * @return tag
     */

    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * This function changing the tag associated with this edge.
     * @param t
     */

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    private class edgeLocation implements edge_location{

        /**
         * This class implements the interface of edge_location.
         * This class represents a position of an edge on the graph (a relative position
         * on an edge - between two consecutive nodes).
         */

        private edge_data edge;

        @Override
        public edge_data getEdge() {
            return this.edge;
        }

        @Override
        public double getRatio() {
            return 0;
        }
    }

    /**
     * This function returns true if two edges are equal to each other.
     * Two edges are equal to each other if they have the same src node,
     * the same dest node and the same weight.
     * @param o object
     * @return true/false
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdgeData)) return false;
        EdgeData edgeData = (EdgeData) o;
        return Double.compare(edgeData.weight, weight) == 0 &&
                Objects.equals(src, edgeData.src) &&
                Objects.equals(dest, edgeData.dest);
    }

    /**
     * Print function
     * @return String
     */

    @Override
    public String toString() {
        return "EdgeData{" +
                "src=" + src +
                ", dest=" + dest +
                ", weight=" + weight +
                ", tag=" + tag +
                ", info='" + info + '\'' +
                '}';
    }
}
