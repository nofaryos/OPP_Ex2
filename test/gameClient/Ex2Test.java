package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Ex2Test {

    @Test
    void pokemon(){

        Point3D point = new Point3D(1,2,3);
        edge_data e = new EdgeData(4,5,6);
        CL_Pokemon p= new CL_Pokemon(point,1,7,8,e);
        assertEquals(1,p.getType());
        assertEquals(7,p.getValue());
        assertEquals(e,p.get_edge());
        assertEquals(point,p.getLocation());
    }

    @Test

    void agent(){
        directed_weighted_graph g = new DWGraph_DS();
        node_data node_1 = new NodeData();
        node_data node_2 = new NodeData();
        node_data node_3 = new NodeData();
        node_data node_4 = new NodeData();
        g.addNode(node_1);
        g.addNode(node_2);
        g.addNode(node_3);
        g.addNode(node_4);
        CL_Agent a = new CL_Agent(g,0);
        assertEquals(0,a.getSrcNode());
        assertNull(a.get_curr_edge());
        assertEquals(0,a.getValue());
        assertEquals(0 ,a.getSpeed());
    }

    @Test
    void arena(){
        Arena arena = new Arena();
        directed_weighted_graph g = new DWGraph_DS();

        node_data node_1 = new NodeData();
        node_data node_2 = new NodeData();
        g.addNode(node_1);
        g.addNode(node_2);
        CL_Agent a_1 = new CL_Agent(g,node_1.getKey());
        CL_Agent a_2 = new CL_Agent(g,node_2.getKey());
        List<CL_Agent> agents = new ArrayList<>();
        agents.add(a_1);
        agents.add(a_2);
        arena.setAgents(agents);
        assertEquals(agents,arena.getAgents());

    }
}