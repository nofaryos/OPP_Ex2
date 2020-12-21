ReadMe:
Ex2: NOFAR YOSEF and OR TIRAM.


This project includes two parts: 
 the first part designed to model data structures and algorithms on directional weighted graphs and the second part includes a game of Pokemons on the graph built in the first part.
Below is a description of the two parts:

The first part:
this part includes  9 classes:

1. NodeData class: This class is implements the interface of node data and it designed to create a vertex in the graph.
  Each node in the graph has a unique key, location- that represents the geo location <x,y,z>  of the node in 3D space, weight, tag and info.
the last two used to define properties of the node through which it will be possible to check whether the graph is connected and in addition to calculate path weights in the graph.

2. EdgeData class: This class is  implements the interface of edge data and it designed to create an edge in the graph.
Each edge in the graph has key of src node, key of dest node, weight, tag and info.
In this class there is an internal class:
 edgeLocation class that implements the interface of edge_location. 
 This class represents a position of an edge on the graph (a relative position on an edge - between two consecutive nodes).


3. DWGraph_DS class: This class implements the interface of directed_weighted_graph.
 each graph has a 3 collections in the form of hash map: the first for keeping the nodes of the graph, 
the second for keeping the out edges and the last for keeping the enter edges.
changes in the graph such as: add/remove a node/edge, checking if there is a edge between two nodes and more can be made in this class. 
Hash Map: In the hash map data structure, each member in the collection has a unique key, in this way i can access the member, add an member or delete an member with an O(1). 
Hence, I chose this data structure so that graph changes would be made quickly, even when it comes to a graph with lots of vertices. 
 

4. DWGraph_Algo class: this class implements the interface of dw_graph_algorithms,
 this class implements algorithms that can be run on the graph: checking whether the graph is connected, finding the shortest path between two vertices , deep copying of a graph, 
saving the graph to Json object and loading the graph from Json String .
In this class i used Dijkstra's algorithm to check whether the graph is connected and in addition, 
to calculate path weights in the graph- This algorithm scans the graph using the properties of each vertex, 
at each stage of the algorithm each vertex is marked by which it is possible to know whether the algorithm has already visited a particular node in the graph and in addition, 
to know the minimum weight of the path between that vertex and the start vertex.

5. geoLocation class:   This class implements the interface of geo_location.
  geoLocation represents a geo location <x,y,z>, aka Point3D of node in the graph.
  Each geoLocation has value of X coordinate, value of Y coordinate and value of Z coordinate.

6. NodeDistance class:   This class represents the distance of node in the graph in some path
 in the graph, it used in Dijkstra's algorithm to checking if the graph is connected and to finding the shortest path between two nodes.

7. EdgeJsonSerialize class: This class implements the interface of JsonSerializer, This class is using to converts an edge_data object to Json String.


8. NodeJsonSerialize class: This class implements the interface of JsonSerializer, This class is using to converts an node object to Json String.

9. graphJson class: This class implements the interfaces of JsonSerializer and JsonDeserializer, This class includes two functions:
The first function using to converts a Json String to directed_weighted_graph object.
 The second function using to converts a directed_weighted_graph object to Json String.

The second part:

This section describes a game of Pokemons and Agents that consists of 24 stages, each stage has a score that consists of the amount of moves the agent made during the game and 
its value that varies depending on the amount of Pokemons he ate.
In order to gain the maximum score at each stage, we used the following strategy:
At each stage of the game:
If the number of agents is greater than two:
 If the agent's speed is less than three the agent will send it to the nearest Pokemon - so the agent can eat the Pokemon closest to him, gain speed and only then walk longer distances to eat higher value Pokemons,
Otherwise: If the agent's speed is greater than three or there are more than two agents we choose at each stage to eat the highest value Pokemon by sending each of them the agent closest to him.

This part includes  7 classes:


1.  Ex2 class: This class is designed to run the game by the user, it includes the implementations of the game strategy described in the next node functions.

2.  Arena class: This class describes the game scene - the Pokemon, the agents and the graph on which the game operates.
Tree map: we chose to keep the Pokemons in a  tree map data structure because in this way the pokemons arranged according to their value from the largest to the smallest.

3.  CL_Agent class: This class describes an agent in the game,
Each agent has a speed, a vertex on which he is, a value that increases during the game depending on the amount of Pokemons he ate and a unique number.

4. CL_Pokemon class: This class describes a pokemon in the game,
Each pokemon has an edge on which he is, a value and type thet used to know in which direction of the edge the pokemon is found.

5.  My panel class: This class describes the graphics of the game.

6.  My frame class: This class describes the game window.

7. openScreen class: This class describes the game entry screen.







