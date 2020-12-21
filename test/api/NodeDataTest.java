package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {

    @Test
    void getKey() {
        NodeData n=new NodeData();
        assertEquals(0,n.getKey());
    }

    @Test
    void getWeight() {
        NodeData n=new NodeData();
        assertEquals(0,n.getWeight());
    }

    @Test
    void setWeight() {
        NodeData n=new NodeData();
        assertEquals(0,n.getWeight());
        n.setWeight(7.5);
        assertEquals(7.5,n.getWeight());
    }

    @Test
    void getTag() {
        NodeData n=new NodeData();
        assertEquals(0,n.getTag());
    }

    @Test
    void setTag() {
        NodeData n=new NodeData();
        assertEquals(0,n.getTag());
        n.setTag(10);
        assertEquals(10,n.getTag());
    }

    @Test
    void getInfo() {
        NodeData n=new NodeData();
        assertEquals(" ",n.getInfo());
    }

    @Test
    void setInfo() {
        NodeData n=new NodeData();
        assertEquals(" ",n.getInfo());
        n.setInfo("info");
        assertEquals("info",n.getInfo());
    }

    @Test
    void getLocation() {
        NodeData n=new NodeData();
        geo_location gl = new geoLocation();
        assertEquals(gl,n.getLocation());
    }

    @Test
    void setLocation() {
        NodeData n=new NodeData();
        geo_location g=new geoLocation(5,5,5);
        n.setLocation(g);
        assertEquals(g,n.getLocation());
    }
}