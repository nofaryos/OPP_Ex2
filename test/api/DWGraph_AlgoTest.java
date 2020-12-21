package api;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    //Function for creating a graph with v_size vertices
    public directed_weighted_graph graph_creator(int v_size) {
        NodeData.setForKey();

        directed_weighted_graph g = new DWGraph_DS();
        for (int i = 0; i < v_size ; i++) {
            node_data a=new NodeData();
            g.addNode(a);
        }
        return g;
    }

    @Test
    void init() {
        directed_weighted_graph g=graph_creator(30);
        for (int i=0; i < g.nodeSize(); i++){
            g.connect(i,1,i);
        }
        dw_graph_algorithms ga=new DWGraph_Algo();
        ga.init(g);
        assertEquals(g.nodeSize(),ga.getGraph().nodeSize());
        assertSame(g,ga.getGraph());
        assertEquals(g,ga.getGraph());
    }

    @Test
    void copy_RunTime(){
        directed_weighted_graph g=graph_creator(1000000);
        for (int i = 0; i <100000 ; i++)
            for (int k= 0; k <11 ; k++)
                g.connect(i,k,1);
        DWGraph_Algo graph = new DWGraph_Algo();
        graph.init(g);

        //Check that it possible to copy a graph with a million vertices and a million sides in a reasonable run
        assertTimeoutPreemptively(Duration.ofMillis(100000), () -> {
            directed_weighted_graph graph_copy = graph.copy();
            System.out.println(graph_copy.edgeSize());

        });
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
        directed_weighted_graph g = graph_creator(15);
        g.connect(4,10,2);
        g.connect(6,7,2);
        g.connect(11,3,3);
        g.connect(1,14,3);
        DWGraph_Algo graph = new DWGraph_Algo();
        graph.init(g);
        directed_weighted_graph graph_copy = graph.copy();

        assertEquals(graph_copy,graph.getGraph());
        assertEquals(graph_copy,g);
        assertEquals(g.getMC(),graph_copy.getMC());
        assertEquals(g.edgeSize(), graph_copy.edgeSize());
        assertEquals(g.nodeSize(), graph_copy.nodeSize());
    }

    @Test
    void isConnected1() {
        directed_weighted_graph g=graph_creator(5);
        dw_graph_algorithms ga= new DWGraph_Algo(g);
        assertFalse(ga.isConnected());
        for (int i=0; i< ga.getGraph().nodeSize();i++){
            ga.getGraph().connect(0,i,0);
            ga.getGraph().connect(i,0,0);

        }
        assertTrue(ga.isConnected());
        ga.getGraph().removeEdge(0,2);
        assertFalse(ga.isConnected());
        ga.getGraph().removeNode(0);
        assertFalse(ga.isConnected());
        //Check that after removing a vertex to which edges were attached the graph is no longer connected.
        //assertFalse(ga.isConnected());
        for (int i = 1 ; i < 40 ; i++){
            ga.getGraph().removeNode(i);
        }
        assertTrue(ga.isConnected());
    }

    @Test
    void shortestPathDist() {
        directed_weighted_graph g=graph_creator(6);
        dw_graph_algorithms ga= new DWGraph_Algo(g);
        g.connect(0,1,0.5);
        g.connect(1,2,0.5);
        g.connect(0,2,1.5);
        g.connect(0,3,4);
        g.connect(3,4,6.5);
        assertEquals(1,ga.shortestPathDist(0,2));
        assertEquals(0,ga.shortestPathDist(0,0));
        assertEquals(-1,ga.shortestPathDist(1,3));
        assertEquals(-1,ga.shortestPathDist(0,5));
        assertEquals(-1,ga.shortestPathDist(1,7));
        assertEquals(-1,ga.shortestPathDist(8,9));
    }


    @Test
    void shortestPath1() {
        directed_weighted_graph g=graph_creator(4);
        dw_graph_algorithms ga= new DWGraph_Algo(g);
        g.connect(0,1,0.5);
        g.connect(1,2,0.5);
        g.connect(0,2,1.5);
        List<node_data> pathResult= ga.shortestPath(0,2);
        List<node_data> pathCheck= new LinkedList<node_data>();
        pathCheck.add(g.getNode(0));
        pathCheck.add(g.getNode(1));
        pathCheck.add(g.getNode(2));
        assertEquals(pathCheck.size(),pathResult.size());
        for (int i = 0; i < pathCheck.size() ; i++) {
            assertEquals(pathCheck.get(i),pathResult.get(i));
        }
        //Check that we gets null when we trying to get a path that is not in the graph
        assertNull(ga.shortestPath(8,9));
        assertNull(ga.shortestPath(0,10));
    }

    @Test
    void shortestPath2() {
        directed_weighted_graph g = graph_creator(6);
        dw_graph_algorithms ga = new DWGraph_Algo(g);
        for (int i = 0; i < g.nodeSize()-1; i++) {
            g.connect(i,i+1,0);
        }
        assertEquals(1,ga.shortestPath(0,0).size());
        List<node_data> R= ga.shortestPath(0,5);
        List<node_data> C= new LinkedList<node_data>();
        C.add(g.getNode(0));
        C.add(g.getNode(1));
        C.add(g.getNode(2));
        C.add(g.getNode(3));
        C.add(g.getNode(4));
        C.add(g.getNode(5));
        assertEquals(C,R);
        assertEquals(0,ga.shortestPathDist(0,5));
        assertEquals(-1,ga.shortestPathDist(5,0));
        g.removeNode(5);
        assertNull(ga.shortestPath(0,5));



    }
    @Test
    void save() throws IOException {
        directed_weighted_graph g = graph_creator(5);
        g.connect(0,3,7);
        g.connect(2,3,9);
        dw_graph_algorithms graph1 = new DWGraph_Algo(g);
        assertTrue(graph1.save("graph_1.json"));
    }

    @Test
    void load1() {

        directed_weighted_graph g = graph_creator(5);
        g.connect(0,3,7);
        g.connect(2,3,9);
        dw_graph_algorithms graph1 = new DWGraph_Algo(g);
        assertTrue(graph1.save("graph_2.json"));
        assertTrue(graph1.load("graph_2.json"));
        assertTrue(graph1.save("graph_2.json"));
    }

    @Test
    void save_load() throws IOException {
        directed_weighted_graph g = graph_creator(50);
        dw_graph_algorithms graph1 = new DWGraph_Algo(g);
        g.connect(10, 20, 0);
        g.connect(1, 3, 5);
        g.connect(4, 5, 6);
        g.connect(7, 8, 9);
        assertTrue(graph1.save("graph_3"));

        dw_graph_algorithms graph2 = new DWGraph_Algo(g);
        assertTrue(graph2.load("graph_3"));
        assertEquals(graph1.getGraph(), graph2.getGraph());
        g.removeNode(0);
        assertNotEquals(graph1.getGraph(), graph2.getGraph());
    }
}