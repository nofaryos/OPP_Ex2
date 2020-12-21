package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataTest {

    @Test
    void getSrc() {
        EdgeData e=new EdgeData(5,6,8);
        assertEquals(5,e.getSrc());
    }

    @Test
    void getInfo() {
        EdgeData e=new EdgeData(8,8,10);
        assertEquals(" ",e.getInfo());
    }

    @Test
    void setInfo() {
        EdgeData e=new EdgeData(1,2,3);
        assertEquals(" ",e.getInfo());
        e.setInfo("info");
        assertEquals("info",e.getInfo());
    }

    @Test
    void getTag() {
        EdgeData e=new EdgeData(0,1,3);
        assertEquals(0,e.getTag());
    }

    @Test
    void setTag() {
        EdgeData e=new EdgeData(5,6,7);
        assertEquals(0,e.getTag());
        e.setTag(1);
        assertEquals(1,e.getTag());
    }

    @Test
    void getDest() {
        EdgeData e=new EdgeData(7,1,8);
        assertEquals(1,e.getDest());
    }

    @Test
    void getWeight() {
        EdgeData e=new EdgeData(6,9,13);
        assertEquals(13,e.getWeight());
    }
}