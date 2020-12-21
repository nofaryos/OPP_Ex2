package api;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

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
    void getNode() {
        directed_weighted_graph g = graph_creator(0);
        //Check that if you want to get a node in the graph without nodes you get null back
        assertNull(g.getNode(5));
        directed_weighted_graph r = graph_creator(40);
        //Check that if you want to get a node that is not in the graph you get null back
        assertNull(g.getNode(50));
        //Checking for a node that exists in the graph
        assertNotNull(r.getNode(30));
    }

    @Test
    void getEdge() {
        directed_weighted_graph g=graph_creator(20);
        g.connect(4,0,0);
        g.connect(4,1,1);
        g.connect(4,2,2);
        g.connect(4,3,3);
        g.connect(4,4,4);
        g.connect(4,5,5);
        g.connect(6,4,7);
        assertNull(g.getEdge(5,4));
        g.connect(5,4,10);
        edge_data e = g.getEdge(4,5);
        assertEquals(5,e.getWeight());
        g.connect(4,5,9);
        edge_data a=g.getEdge(4,5);
        assertEquals(9,a.getWeight());
        g.removeEdge(4,5);
        assertNull(g.getEdge(4,5));
        assertNotNull(g.getEdge(5,4));
        assertNull(g.getEdge(4,4));
        assertNull(g.getEdge(30,40));
        assertNull(g.getEdge(0,3));
    }

    @Test
    void addNode() {
        directed_weighted_graph g = graph_creator(10);
        node_data r = g.getNode(5);
        int s=g.nodeSize();
        g.addNode(r);
        //Check that no new vertex is created in the heap when trying
        // to add a vertex with a key that already exists in the graph
        assertSame(r,g.getNode(5));
        assertSame(s,g.nodeSize());
        g.addNode(new NodeData());
        //Check that we were able to add a vertex to the graph
        assertNotNull(g.getNode(10));
        //Check that we given null when there is no such vertex in the graph
        assertNull(g.getNode(20));
    }

    @Test
    void connect() {
        directed_weighted_graph g = graph_creator(30);
        for (int i=0; i < 30; i=i+2){
            g.connect(0,i,i+2);
        }
        //A test that there is no connection of a edge between a vertex and itself
        assertNull(g.getEdge(0,0));
        assertEquals(6,g.getEdge(0,4).getWeight());
        assertNull(g.getEdge(4,0));
        g.connect(4,0,2);
        assertEquals(2,g.getEdge(4,0).getWeight());
        g.connect(0,2,-5);
        assertEquals(4,g.getEdge(0,2).getWeight());

        assertNull(g.getEdge(50,60));
        assertNull(g.getEdge(5,40));

    }

    @Test
    void getV() {
        directed_weighted_graph g=graph_creator(0);
        List<node_data> result=new ArrayList<>();
        node_data a=new NodeData();
        node_data b=new NodeData();
        node_data c=new NodeData();
        node_data d=new NodeData();
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        result.add(a);
        result.add(b);
        result.add(c);
        result.add(d);
        assertTrue(listComparison(g.getV(),result));
    }

    @Test
    void getE() {
        directed_weighted_graph g=graph_creator(10);
        assertEquals(0,g.getE(0).size());
    }

    @Test
    void removeNode() {
        directed_weighted_graph g = graph_creator(30);
        for (int i = 0; i <g.nodeSize() ; i++) {
            g.connect(0,i,i);
            g.connect(i,0,i);
        }
        g.removeNode(0);
        assertNull(g.getEdge(2,0));
        assertNull(g.getEdge(0,2));
        assertNull(g.getEdge(1,0));
        assertNull(g.getEdge(0,1));


        assertEquals(0,g.edgeSize());
        assertEquals(29,g.nodeSize());

    }

    @Test
    void removeEdge() {
        directed_weighted_graph g=graph_creator(20);
        Collection<node_data > nodes = g.getV();
        for (int i = 0; i <g.nodeSize() ; i++) {
            g.connect(0,i,i);
        }
        assertNull(g.removeEdge(0,0));
        assertNull(g.removeEdge(30,40));
        assertNull(g.removeEdge(0,40));
        g.connect(1,0,2);
        g.removeEdge(0,1);
        assertNotNull(g.removeEdge(1,0));
        //Check that we were able to remove a edge from the graph
        g.removeEdge(0,4);
        assertNull(g.getEdge(0,4));
    }

    @Test
    void nodeSize() {
        directed_weighted_graph g=graph_creator(50);
        assertEquals(50,g.nodeSize());
        g.removeNode(4);
        assertEquals(49,g.nodeSize());
        g.removeNode(4);
        assertEquals(49,g.nodeSize());
        g.removeNode(100);
        assertEquals(49,g.nodeSize());
        g.removeNode(3);
        g.removeNode(67);
        assertEquals(48,g.nodeSize());
    }

    @Test
    void getMC_edgeSize() {
        directed_weighted_graph g=graph_creator(20);
        assertEquals(0,g.edgeSize());
        assertEquals(20,g.getMC());
        for (int i = 0; i < 20 ; i++) {
            g.connect(0,i,2);
        }
        assertEquals(19,g.edgeSize());
        assertEquals(39,g.getMC());
        g.removeEdge(0,0);
        assertEquals(19,g.edgeSize());
        assertEquals(39,g.getMC());
        g.removeEdge(0,1);
        assertEquals(18,g.edgeSize());
        assertEquals(40,g.getMC());
        g.removeNode(0);
        assertEquals(0,g.edgeSize());
        assertEquals(59,g.getMC());
    }

    @Test
    public void equalTest()
    {
        directed_weighted_graph g1= graph_creator(0);
        assertEquals(g1,g1);
        directed_weighted_graph g2= graph_creator(0);
        assertEquals(g1,g2);
        g1.connect(0,2,3);
        assertEquals(g1,g2);
        g2.connect(0,2,3);
        assertEquals(g1,g2);

    }

    @Test
    public  void BuildRunTime()
    {
        //Check that it possible to build a graph with a million vertices and ten million sides in a reasonable run time
        assertTimeoutPreemptively(Duration.ofMillis(10000), () -> {

            directed_weighted_graph g=graph_creator(1000000);
            for (int i = 0; i <g.nodeSize() ; i++)
                for (int k= 0; k <11 ; k++)
                    g.connect(i,k,1);

        });

    }

    public boolean listComparison(Collection<node_data> a,Collection<node_data> b){
        boolean ans=false;
        for ( node_data na: a) {
            for (node_data nb: b) {
                if (na.equals(nb)){
                    ans=true;
                    break;
                }
            }
            if (!ans){
                return ans;
            }
        }
        return ans;
    }
}