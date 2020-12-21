package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class implements the interface of directed_weighted_graph,
 * It is designed to create an directional weighted graph that can be stored on a hard disk.
 * Each graph has a collection of nodes, collections of edges, mode count and num of edges.
 */

public class DWGraph_DS implements directed_weighted_graph {

    private int MC;
    private int edgesNum;


    //The table of out edges, each node has a table of edges that out from it.
    private HashMap<Integer, HashMap<Integer, edge_data>>edgesOut;

    //The table of in edges, each node has a table of edges that enter to it.
    private HashMap<Integer, HashMap<Integer, Integer>>edgesIn;

    //The table of the nodes, the keys in the table are the key of each node.
    private HashMap<Integer, node_data> nodes;

    /**
     * Constructor to create a new DWGraph_DS:
     */

    public DWGraph_DS(){
        this.MC=0;
        this.edgesNum=0;
        nodes=new HashMap<Integer, node_data>();
        edgesOut=new HashMap<Integer, HashMap<Integer, edge_data>>();
        edgesIn=new HashMap<Integer, HashMap<Integer, Integer>>();

    }

    /**
     * A copy constructor.
     * @param  g- directed_weighted_graph
     */

    public DWGraph_DS(directed_weighted_graph g){
        this.edgesOut = new HashMap<Integer, HashMap<Integer, edge_data>>();
        this.edgesIn=new HashMap<Integer, HashMap<Integer, Integer>>();
        this.nodes = new HashMap<Integer, node_data>();

        //Copy the nodes and the edges of the graph.
        for (node_data node: g.getV()) {
            this.nodes.put(node.getKey(),new NodeData(node));
            this.edgesOut.put(node.getKey(),new HashMap<Integer, edge_data>());
            this.edgesIn.put(node.getKey(),new HashMap<Integer, Integer>());
            //Connecting each node to all its neighbors with the relevant weights.
            for (edge_data E:g.getE(node.getKey())){
                this.edgesOut.get(E.getSrc()).put(E.getDest(),new EdgeData(E));
                this.edgesIn.get(E.getSrc()).put(E.getDest(),E.getSrc());
            }
        }
        this.MC = g.getMC();
        this.edgesNum = g.edgeSize();
    }

    /**
     * This function return the node_data by getting the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */

    @Override
    public node_data getNode(int key) {
        return nodes.get(key);
    }

    /**
     *This function return the edge_data between node1 and node2(If there is an edge between them).
     * if there is no such edge - it returns null.
     * @param src
     * @param dest
     * @return edge_data.
     */

    @Override
    public edge_data getEdge(int src, int dest) {
        //Check if the src node of the edge is in the edges out table.
        if (edgesOut.containsKey(src)) {
            //If there is an edge, return it.
            return edgesOut.get(src).get(dest);
        }
        return null;
    }

    /**
     *This function add a new node to the graph.
     * if there is already a node with such a key, no action would be performed.
     * @param n- node_data
     */

    @Override
    public void addNode(node_data n){
        if (!nodes.containsKey(n.getKey())){
            nodes.put(n.getKey(),new NodeData(n));
            edgesOut.put(n.getKey(),new HashMap<Integer, edge_data>());
            edgesIn.put(n.getKey(),new HashMap<Integer, Integer>());
            MC++;
        }
    }

    /**
     * This function connect an edge between two nodes, with an weight >=0.
     * if the edge already exists - the method updates the weight of the edge.
     * @param src
     * @param dest
     * @param w- weight
     */

    @Override
    public void connect(int src, int dest, double w) {
        //Check that the two nodes are contained in the graph and that they are different from each other.
        if (0 <= w && src!=dest ){
            if (getNode(src)!=null && getNode(dest)!=null){
                edge_data e = getEdge(src,dest);
                if (e !=null ){
                    //If there is already a edge connected between them with weight w there is no need for any action.
                    if (e.getWeight()==w){
                        return;
                    }
                    //If there is already a edge connected between them with weight that different from w:
                    else {
                        edgesOut.get(src).put(dest,new EdgeData(src,dest,w));
                        MC++;
                    }
                }
                //If there is no edge between them we will increase the number of edges
                // in the graph by one and creating the edge.
                else {
                    edgesOut.get(src).put(dest,new EdgeData(src,dest,w));
                    edgesIn.get(dest).put(src,dest);
                    MC++;
                    edgesNum++;
                }
            }
        }
    }

    /**
     * This function return a Collection that
     * representing all the nodes in the graph.
     * @return Collection of node_data
     */

    @Override
    public Collection<node_data> getV() {
        return nodes.values();
    }


    /**
     * This function returns a Collection of edges that connected to node_id.
     * @param  node_id
     * @return Collection of edge_data
     */

    @Override
    public Collection<edge_data> getE(int node_id) {
         if (edgesOut.containsKey(node_id)){
             return edgesOut.get(node_id).values();
         }
         return null;
    }

    /**
     * This function delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * @param key
     * @return the data of the removed node (null if none).
     */

    @Override
    public node_data removeNode(int key) {
        //Check if the node is in the graph.
        if (getNode(key) != null) {
            int size;
            //A help array that holds the keys of the nodes that connected to the remove node.
            ArrayList<Integer> arrayHelp = new ArrayList<Integer>();
            arrayHelp.addAll(edgesOut.get(key).keySet());

            size = arrayHelp.size();
            if (edgesIn.get(key).size() != 0) {
                arrayHelp.addAll(edgesIn.get(key).keySet());
            }
            for (int i = 0; i < arrayHelp.size(); i++) {
                if (i < size) {
                    //Delete all the out edges of the graph that are connected to the node.
                    removeEdge(key, arrayHelp.get(i));
                }
                //Delete all the in edges of the graph that are connected to the node.
                else removeEdge(arrayHelp.get(i),key);
            }
            MC++;
            //Remove the node from the edges collections
            edgesOut.remove(key);
            edgesIn.remove(key);
            //Remove the node from the nodes collection and return it.
            return nodes.remove(key);
        }
        return null;
    }

    /**
     * This function delete an edge from the graph.
     * @param src
     * @param dest
     * @return edge_data
     */

    @Override
    public edge_data removeEdge(int src, int dest) {
        //Check that the two nodes are contained in the graph.
        if (getNode(src) != null && getNode(dest) != null){
            //Check if there is an edge between them.
            if (getEdge(src,dest) != null){
                edgesNum--;
                MC++;
                edgesIn.get(dest).remove(src);
                return edgesOut.get(src).remove(dest);
            }
        }
        return null;
    }

    /**
     * This function return the number of vertices (nodes) in the graph.
     * @return number of nodes.
     */

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    /**
     * This function return the number of edges in the graph.
     * @return number of edges.
     */

    @Override
    public int edgeSize() {
        return edgesNum;
    }

    /**
     * This function return the Mode Count.
     * Any change in the inner state of the graph is cause an increment in the ModeCount.
     * @return MC
     */

    @Override
    public int getMC() {
        return MC;
    }


    /**
     * This function returns true if two graphs are equal to each other.
     * Two directional weighted graphs are equal to each other if they have the same number of edges,
     * the same edges, the same number of nodes and
     * if they contain the same nodes.
     * @param o
     * @return true/false
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DWGraph_DS)) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        if (that.edgeSize() != this.edgeSize() || that.nodeSize() != this.nodeSize()){
            return false;
        }
        for (node_data n:this.getV()) {
            if (!(n.equals(that.getNode(n.getKey())))) {
                return false;
            }
            for (edge_data E:this.getE(n.getKey())) {
                //Check that the edges weights in the two graphs are the same
                if (!this.getEdge(E.getSrc(),E.getDest()).equals(that.getEdge(E.getSrc(),E.getDest()))){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Print function
     * @return String
     */


    @Override
    public String toString() {
        StringBuilder S = new StringBuilder();
        S.append("Num of nodes: " + this.nodeSize() + "\n");
        S.append("Number of edegs: " + this.edgeSize()+ "\n");
        S.append("Nodes: \n");
        Collection<node_data> nodes = this.getV();
        for (node_data n: nodes) {
            S.append(n.toString());
            S.append("\n");
        }
        S.append("\nEdges: \n");
        for (Integer K: edgesOut.keySet()) {
            S.append("key of the node:" + K+"\n");
            for (edge_data e:this.getE(K)) {
                S.append("the neighbor:" + e.getSrc());
                S.append(", The weight of the edge between them: " + e.getWeight()+"\n");
            }
            S.append("\n");
        }
        return S.toString();
    }
}
