package gameClient;
import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;



public class MyPanel extends JPanel  {
    private int ind = 0;
    private Arena _ar;
    private gameClient.util.Range2Range _w2f;
    private game_service game;



    public MyPanel(){
        super();
        _ar = new Arena();
        ind = 0;
        this.setBackground(Color.white);
    }

    public void update(Arena ar , game_service game1) {
        this._ar = ar;
        this.game = game1;
        updateFrame();
    }

    //A function that updates the graph size to the appropriate panel size
    private void updateFrame() {
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(0, 0, 515, 367);
        this.add(lblNewLabel);
        Range rx = new Range(20,this.getWidth()-20);
        Range ry = new Range(this.getHeight()-10,150);
        Range2D frame = new Range2D(rx,ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g,frame);
    }
    // Function that draws the graphics according to the panel
    public void paint(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);
        BufferedImage img = null;
        BufferedImage resizedImage = resize(img,30,30);
        try {
            resizedImage = ImageIO.read(new File("data/Screen2.jpg"));//Add a background image
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(resizedImage,0,0,this.getWidth(),this.getHeight(),this);//Adjust the image size
        updateFrame();
        try {
            drawGraph(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawPokemons(g);
        drawAgants(g);
        drawInfo(g);
    }

    private void drawInfo(Graphics g) {
        java.util.List<String> str = _ar.get_info();
        String dt = "none";
        for(int i=0;i<str.size();i++) {
            g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
        }
    }
    //A function that draws the graph
    private void drawGraph(Graphics g) throws IOException {
        directed_weighted_graph gg = _ar.getGraph();
        Iterator<node_data> iter = gg.getV().iterator();
        g.setColor(Color.BLACK);
        Font font = new Font("", Font.ITALIC, 20 );
        g.setFont(font);
        //A timer that shows the time left for the game
        g.drawString("Time Left :" + game.timeToEnd()/1000, 100 , 100);
        BufferedImage img = null;
        img = ImageIO.read(new File("data/pokemon.png"));//Game graph title
        BufferedImage resizedImage = resize(img,400,100);
        g.drawImage(resizedImage, 300, 100, this);
        while(iter.hasNext()) {
            node_data n = iter.next();
            g.setColor(Color.BLACK);
            drawNode(n,6,g);//Create a node
            Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
            while(itr.hasNext()) {
                edge_data e = itr.next();
                g.setColor(Color.BLACK);
                drawEdge(e, g);//Create a rib on the graph
            }
            g.setColor(Color.BLACK);
        }
    }
    //A function that draws the Pokemon
    private void drawPokemons(Graphics g)  {
        TreeSet<CL_Pokemon> fs = _ar.getPokemons();
        if(fs!=null) {
            Iterator<CL_Pokemon> itr = fs.iterator();
            while(itr.hasNext()) {
                CL_Pokemon f = itr.next();
                Point3D c = f.getLocation();
                int r = 10;
                g.setColor(Color.green);
                if(f.getType() < 0) {
                    g.setColor(Color.BLACK);
                }
                if(c!=null) {
                    geo_location fp = this._w2f.world2frame(c);
                    BufferedImage img = null;

                    try {
                        g.drawString("val" + f.getValue() , (int) fp.x() - 25 ,(int) fp.y() - 25);//Adding the Pokemon value
                        img = ImageIO.read(new File("data/Pokeball.png"));//Add an image to Pokemon

                    }
                    catch (IOException e) {
                    }
                    //Adjust the image size
                    BufferedImage resizedImage = resize(img,30,30);
                    g.drawImage(resizedImage, (int) fp.x() - 20, (int) fp.y() - 20, this);
                    //	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);

                }
            }
        }
    }
    //A function that updates the size of the resulting image at the appropriate size
    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }
    //A function that draws agents
    private void drawAgants(Graphics g) {
        List<CL_Agent> rs = _ar.getAgents();
        int i = 0;
        while(rs != null && i < rs.size()) {
            geo_location c = rs.get(i).getLocation();
            int r=8;
            i++;
            if(c != null) {
                geo_location fp = this._w2f.world2frame(c);
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File("data/Pikachu.png"));//Add an image to an agent

                } catch (IOException e) {

                }
                g.drawString("Agent "+rs.get(i-1).getID()+" grade: "+rs.get(i-1).getValue(),this.getWidth()-200,i*20);//add the grade of the agent
                BufferedImage resizedImage = resize(img,70,70);
                g.drawImage(resizedImage, (int) fp.x() - 30, (int) fp.y() - 30, this);                }

        }
    }
    //A function that draws a node
    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
        g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
    }
    //A function that draws a side
    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
        //	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
    }


}