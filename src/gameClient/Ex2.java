package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class Ex2 implements Runnable{
    private static MyPanel panel;
    private static MyFrame frame;
    private static Arena _ar;
    private static openScreen screen;


    public static void main(String[] a) {
        Thread client = new Thread(){
            public void run(){
                try {
                    screen =  new openScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } ;
        client.start();
    }

    @Override
    public void run() {
        game_service game = Game_Server_Ex2.getServer(screen.getLevel());
        game.login(screen.getId());
        String info = game.toString();
        JSONObject line = null;
        String nameOfGraph=" ";
        try {
            line = new JSONObject(info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject ttt = null;
        try {
            ttt = line.getJSONObject("GameServer");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            nameOfGraph = ttt.getString("graph");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dw_graph_algorithms ga = new DWGraph_Algo();
        //load the graph
        ga.load(nameOfGraph);
        directed_weighted_graph gg=ga.getGraph();
        //init the game
        init(game,gg);

        game.startGame();

        frame.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());

        int ind=0;

        //Sleep in the first third of the game
        long dt = 110;

        double timeOfGame = game.timeToEnd();

        while(game.isRunning()) {
            moveAgants(game, ga);
            try {
                if(ind%1==0) {
                    frame.repaint();
                }
                //Sleep in the second third of the game
                if (game.timeToEnd() < timeOfGame* (2.0/3.0)){
                    dt = 106;
                }

                //Sleep in the last third of the game
                if (game.timeToEnd() < timeOfGame* (1.0/3.0)){
                    dt = 83;
                }
                Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }
    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen.
     * @param game
     * @param ga
     * @param
     */
    private static void moveAgants(game_service game, dw_graph_algorithms ga) {
        String lg = game.move();
        List<CL_Agent> agents = Arena.getAgents(lg, ga.getGraph());
        _ar.setAgents(agents);
        String fs = game.getPokemons();
        TreeSet<CL_Pokemon> pokemons = Arena.json2Pokemons(fs);
        for (CL_Pokemon p : pokemons) {
            Arena.updateEdge(p, ga.getGraph());
        }
        //We will go through the Pokemons according to their value,
        // we will start with the Pokemon with the highest value
        _ar.setPokemons(pokemons);
        for (CL_Pokemon p : pokemons) {
            CL_Agent agent = nextNode1(ga, p);
            if (agent != null && (agent.getSpeed() > 3.0 || agents.size() > 2)) {
                int dest = nextNode2(ga, agent, p);
                game.chooseNextEdge(agent.getID(), dest);
            }
            //Set the next node for agents for whom we have not yet set the next node.
            for (int i = 0; i < agents.size(); i++) {
                CL_Agent ag = agents.get(i);
                int id = ag.getID();
                int dest = ag.getNextNode();
                int src = ag.getSrcNode();
                double v = ag.getValue();
                if (dest == -1) {
                    dest = nextNode(ga, src);
                    game.chooseNextEdge(ag.getID(), dest);
                }
            }
        }
    }


    /**
     * This function return the closet agent to pokemon p.
     * @param ga
     * @param p
     * @return CL_Agent
     */

    private static CL_Agent nextNode1(dw_graph_algorithms ga, CL_Pokemon p) {
        List<CL_Agent> agents = _ar.getAgents();
        double dis = Double.MAX_VALUE;
        //The pokemon we will return.
        CL_Agent chooseAgent = null;
        for (int i = 0; i < agents.size(); i++) {
            CL_Agent a = agents.get(i);
            //if the src of the agent different from the dest of the pokemon
            if (a.getSrcNode() != p.get_edge().getDest() ) {
                //Find the shortest path between each agent and Pokemon
                double disTemp = ga.shortestPathDist(a.getSrcNode(), p.get_edge().getDest());
                if (disTemp < dis) {
                    dis = disTemp;
                    chooseAgent = a;
                }
            }
            else {
                double disTemp = ga.shortestPathDist(a.getSrcNode(), p.get_edge().getSrc());
                if (disTemp < dis) {
                    dis = disTemp;
                    chooseAgent = a;
                }
            }
        }
        return chooseAgent;
    }

    /**
     * This function selects a dest for the agent in order to bring it closer to the Pokemon.
     * @param ga
     * @param p
     * @param agent
     * @return dest
     */

    private static int nextNode2(dw_graph_algorithms ga, CL_Agent agent, CL_Pokemon p) {
        int dest = -1;
        Collection<edge_data> ee = ga.getGraph().getE(agent.getSrcNode());
        double dis = Double.MAX_VALUE;
        //Go over all the edges attached to the vertex on which the agent is located.
        for (edge_data e:ee) {
            //if the src of the agent different from the dest of the pokemon
            if (agent.getSrcNode()!= p.get_edge().getDest()) {
                //Select the dest depending on the distance to dest of the pokemon edge
                // and according to the Pokemon value.
                double disTemp = ga.shortestPathDist(e.getDest(), p.get_edge().getDest())/p.getValue() ;
                if (disTemp < dis ) {
                    dis = disTemp;
                    dest = e.getDest();
                }
            }
            else {
                double disTemp = ga.shortestPathDist(e.getDest(), p.get_edge().getSrc()) /p.getValue();
                if (disTemp < dis ) {
                    dis = disTemp;
                    dest = e.getDest();
                }

            }
        }
        return dest ;
    }

    /**
     * This function sends the agent to the Pokemon closest to him.
     * @param ga
     * @param src
     * @return dest
     */

    private static int nextNode(dw_graph_algorithms ga, int src) {
        int dest = -1;
        Collection<edge_data> ee = ga.getGraph().getE(src);
        TreeSet<CL_Pokemon> pokemon=_ar.getPokemons();
        double dis = Double.MAX_VALUE;
        double value = Double.MIN_VALUE;
        //Go over all the edges attached to the vertex on which the agent is located.
        for (edge_data e:ee) {
            //Go over all the pokemons.
            for (CL_Pokemon p: pokemon) {
                //if the src of the agent different from the dest of the pokemon
                if (src!= p.get_edge().getDest()) {
                    //Find the shortest path between each agent and Pokemon
                    double disTemp = ga.shortestPathDist(e.getDest(), p.get_edge().getDest());
                    if (disTemp < dis ) {
                        dis = disTemp;
                        dest = e.getDest();
                        value = p.getValue();
                    }
                    else {
                        //f we find a path that is equal to the shortest path but has a greater Pokemon value,
                        // we would like to move toward that Pokemon.
                        if (dis == disTemp &&   value <  p.getValue()) {
                            dis = disTemp;
                            dest = e.getDest();
                            value = p.getValue();
                        }
                    }
                }
                else {
                    //Find the shortest path between each agent and Pokemon
                    double disTemp = ga.shortestPathDist(e.getDest(), p.get_edge().getSrc());
                    if (disTemp < dis ) {
                        dis = disTemp;
                        dest = e.getDest();
                        value = p.getValue();
                    }
                    else {
                        if (dis == disTemp  &&  value <  p.getValue()) {
                            dis = disTemp;
                            dest = e.getDest();
                            value = p.getValue();
                        }
                    }
                }

            }

        }
        return dest ;
    }

    /**
     * This function init the agents in the beginning of the game.
     * @param game
     * @param gg
     */

    private void init(game_service game, directed_weighted_graph gg) {
        String pokemons = game.getPokemons();
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(pokemons));
        frame = new MyFrame("test Ex2");
        frame.setSize(1000, 700);
        panel = new MyPanel();
        frame.add(panel);
        panel.update(_ar,game);
        frame.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int numOfAgents = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            TreeSet<CL_Pokemon> TreePokemon = Arena.json2Pokemons(game.getPokemons());
            _ar.setPokemons(TreePokemon);
            for (CL_Pokemon pokemon : TreePokemon) {
                Arena.updateEdge(pokemon, gg);
            }
            //Position the agents according to the position of the Pokemons at the beginning of the game.
            for (CL_Pokemon p : TreePokemon) {
                if (0 < numOfAgents) {
                    int destPokemon = p.get_edge().getDest();
                    if (p.getType() < 0) {
                        destPokemon = p.get_edge().getSrc();
                    }
                    game.addAgent(destPokemon);
                    numOfAgents--;
                } else {
                    break;
                }
            }
            //If the number of agents is greater than the number of Pokemon we will place the other agents at random.
            while ( 0 < numOfAgents){
                int src;
                Iterator<node_data> itr = gg.getV().iterator();
                int r = (int)(Math.random()*gg.nodeSize());
                int i=0;
                while(i < r) {itr.next();i++;}
                src = itr.next().getKey();
                game.addAgent(src);
                numOfAgents--;
            }
        }

        catch (JSONException e) {e.printStackTrace();}
    }
}