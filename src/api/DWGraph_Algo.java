package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;


/**
 * This class implements the interface of dw_graph_algorithms.
 *The class includes a number of algorithms that run on directional weighted graphs.
 */


public class DWGraph_Algo implements dw_graph_algorithms {

    private directed_weighted_graph gr;

    //This field is intended to keep weights the lowest weight path between two nodes in the graph.
    private HashMap<Integer,NodeDistance> weights=new HashMap<>();

    //This field is intended to keep parent nodes in the lowest weight path between two nodes in the graph.
    private HashMap<Integer,Integer> parents=new HashMap<>();

    /**
     * Constructor to create a new DWGraph_Algo:
     */

    public DWGraph_Algo(){
        this.gr = new DWGraph_DS();
    }

    /**
     * A copy constructor(Shallow copy).
     * @param g- directed_weighted_graph.
     */

    public DWGraph_Algo(directed_weighted_graph g){
        this.gr = g;
    }

    /**
     * This function init the graph on which this set of algorithms operates on.
     * @param g- directed_weighted_graph.
     */

    @Override
    public void init(directed_weighted_graph g) {
        this.gr = g;
    }

    /**
     * This function return the underlying graph of which DWGraph_Algo works.
     * @return this directed_weighted_graph gr.
     */

    @Override
    public directed_weighted_graph getGraph() {
        return this.gr;
    }

    /**
     * This function compute a deep copy of this directed_weighted_graph.
     * @return this directed_weighted_graph gr.
     */

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph g = new DWGraph_DS(this.gr);
        return g;
    }

    /**
     * Dijkstra algorithm return true if this directed_weighted_graph is connected,
     * it is scanning this graph using the tag and info of each node in the graph,
     * start from the vertex identified with some key.
     * Using this algorithm it is possible to determine whether the graph is connected -If the graph has a vertex whose tag
     * is infinite or whose info is white, it means that the graph is not a connected.
     * In addition, path weight between nodes in the graph can be found using this algorithm.
     * @param key - unique key of node
     * @return true/false
     */

    public boolean Dijkstra(int key) {
        PriorityQueue<NodeDistance> PQ = new PriorityQueue<>();
        //Update the weight of each node to infinity and update the color(info) of all the nodes to white.
        //White node - means we have not visited it yet.
        for (node_data n : gr.getV()) {
            n.setInfo("White");
            weights.put(n.getKey(),new NodeDistance(n.getKey(),Double.MAX_VALUE));
        }
        //Update the weight of the node from which we will start scanning the graph to 0.
        weights.get(key).setDistance(0);
        PQ.add(weights.get(key));

        //A loop that goes through all the nodes in the graph.
        while (PQ.size() != 0){
            NodeDistance u = PQ.poll();
            //Black node - means we have updated the minimum weight of the node
            if (!(gr.getNode(u.getK()).getInfo().equals("Black"))) {
                for (edge_data ni : gr.getE(u.getK())) {
                    if (!((gr.getNode(ni.getDest()).getInfo()).equals("Black"))) {
                        double t = ni.getWeight() + weights.get(u.getK()).getDistance();
                        //Update the min weight of the neighbors of node u.
                        if (weights.get(ni.getDest()).getDistance() > t) {
                            weights.get(ni.getDest()).setDistance(t);
                            //Update the parent node of edge ni.
                           parents.put(ni.getDest(), u.getK());
                        }
                        PQ.add(weights.get(ni.getDest()));
                    }
                }
            }
            gr.getNode(u.getK()).setInfo("Black");
        }
        //check if there are any nodes that we did not reach (white nodes)
        for (node_data node : gr.getV()) {
            if (node.getInfo().equals("White")) {
                return false;
            }
        }
        return true;
    }

    /**
     *This function returns true if there is a valid path from EVREY node to each other node.
     * @return true/false.
     */

    @Override
    public boolean isConnected() {
        //An empty graph or a graph that contains a single node is connected
        if (gr.nodeSize() == 0 || gr.nodeSize() == 1) {
            return true;
        }
        //  When we know the graph is not empty,
        //  run the Dijkstra algorithm on each node in the graph to check if the graph is connected
        for (node_data node:gr.getV()) {
            System.out.println(node.getKey());
            if (!Dijkstra(node.getKey())){
                return false;
            }
        }
        return true;
    }

    /**
     *This function finds the path with the lowest weight between src to dest.
     * @param src- start node
     * @param  dest- end node
     * @return the weight of the path, returns -1 if no such path.
     */

    @Override
    public double shortestPathDist(int src, int dest) {

        if (gr.getNode(src) != null && gr.getNode(dest) != null){
            //The weight of the path of a node to itself is 0.
            if (src == dest){
                return 0;
            }
            Dijkstra(src);
            //If the weight of dest different from infinitely,
            // we will return the weight of the path from the node from which we scanned the graph(src) to dest
            if (weights.get(dest).getDistance() != Double.MAX_VALUE){
                return weights.get(dest).getDistance();
            }
        }
        //If there is no path between the two vertices we will return -1
        return -1;
    }

    /**
     *This function returns the path with the lowest weight between src to dest - as a list of nodes:
     * @param src- start node
     * @param dest- end node
     * @return List of node_data
     */

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        ArrayList<node_data> array = new ArrayList<node_data>();
        //The weight of the path between src to dest.
        double dis = shortestPathDist(src, dest);
        //There is no path between the two nodes.
        if (dis == -1) {
            return null;
        }
        // A path between a node node itself
        if (dis == 0 && src == dest){
            array.add(gr.getNode(src));
            return array;
        }
        node_data begin = gr.getNode(dest);
        array.add(gr.getNode(dest));
        //A loop that finds the nodes in the path according to the parent nodes.
        while (!(begin.equals(gr.getNode(src)))){
            int key = this.parents.get(begin.getKey());
            array.add(gr.getNode(key));
            begin = gr.getNode(key);
        }
        ArrayList<node_data> result=new ArrayList<>();
        for (int i = array.size()-1;  0<=i ; i--) {
            result.add(array.get(i));
        }
        return result;
    }

    /**
     * This function saves this directed_weighted_graph to the given
     * file name- in JSON format.
     * @param file - the file name.
     * @return true - if the file was successfully saved
     */

    @Override
    public boolean save(String file) {
        boolean ans = false;
        Gson gson = new GsonBuilder().registerTypeAdapter(DWGraph_DS.class,new graphJson()).create();
        String graphJson = gson.toJson(this.getGraph());
        System.out.println(graphJson);

        try {
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(graphJson);
            pw.close();
            ans = true;
        }
        //Error exception in cases where we could not save the file
        catch (IOException e){
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * This function load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph would remain "as is".
     * @param file - file name of JSON file
     * @return true - if the graph was successfully loaded.
     */

    @Override
    public boolean load(String file) {

        boolean ans = false;
        try
        {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(DWGraph_DS.class, new graphJson());
            Gson gson = builder.create();

            FileReader reader = new FileReader(file);
             this.gr= gson.fromJson(reader, DWGraph_DS.class);
            System.out.println(this.gr);
            ans = true;
        }
        catch (FileNotFoundException e) {
            //Error exception in cases where we could not load the file
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * Print function
     * @return String
     */

    public String toString(){
        return this.gr.toString();
    }

}
