package ex1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
	
	weighted_graph graph;
	
	private HashMap<Integer, Double> dist; 
	private HashMap<Integer, Integer> parent;	
	
	/**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
	@Override
	public void init(weighted_graph g) {
		this.graph = g;
		this.dist = new HashMap<>();
		this.parent = new HashMap<>();
	}

    /**
     * Return the underlying graph of which this class works.
     * @return
     */
	@Override
	public weighted_graph getGraph() {
		return this.graph;
	}

    /**
     * Compute a deep copy of this weighted graph.
     * @return
     */
	@Override
	public weighted_graph copy() {
		// new graph
		weighted_graph new_graph = new WGraph_DS();
		
		//add nodes to graph
		for (node_info node : this.graph.getV()) {
			
			new_graph.addNode(node.getKey());
			
			new_graph.getNode(node.getKey()).setInfo(node.getInfo());;
			new_graph.getNode(node.getKey()).setTag(node.getTag());
			
		}
		
		
		
		for (node_info node : this.graph.getV()) {
			
			for (node_info ni : this.graph.getV(node.getKey())) {
				new_graph.connect(node.getKey(), ni.getKey(), this.graph.getEdge(node.getKey(), ni.getKey()));
			}
		}
		
		return new_graph;
	}

	
	public void djikstra(int source) {
		
		PriorityQueue<node_info> queue = new PriorityQueue<>();
		
		
		for (node_info node : this.graph.getV()) { // for each vertex in g

			if (node.getKey() == source) {
				dist.put(node.getKey(), 0.0);
				node.setTag(0.0);
			}
			
			else {
				
				dist.put(node.getKey(), Double.MAX_VALUE);
				parent.put(node.getKey(), null);
				node.setTag(Double.MAX_VALUE);
			}
			
			queue.add(node);
			
		}
				
		while (!queue.isEmpty()) {
			
			node_info u = queue.poll(); //priorityQ -> min. (also remove)
			
			for (node_info neighbor : this.graph.getV(u.getKey())) {
				
				double alt = this.dist.get(u.getKey()) + this.graph.getEdge(u.getKey(), neighbor.getKey());
				
				if (alt < this.dist.get(neighbor.getKey())) { 
					
					dist.put(neighbor.getKey(), alt);
					parent.put(neighbor.getKey(), u.getKey());
					
					neighbor.setTag(alt);
					
					queue.remove(neighbor); //remove from old position
					queue.add(neighbor); //insert into new position using comperable interface.
					
				}
				
			}
		}
		
	}
	
	
    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     * @return
     */
	@Override
	public boolean isConnected() {
		
		//האם הגרף קשיר
		if (this.graph.nodeSize() == 1 || this.graph.nodeSize() == 0)
			return true;
		
		List<node_info> list = new ArrayList<>(this.graph.getV());
		djikstra(list.get(0).getKey());
		
		for (Double	dist : this.dist.values()) {
			if (dist == Double.MAX_VALUE)
				return false;
		}
		
		return true;
	}

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
	@Override
	public double shortestPathDist(int src, int dest) {
		djikstra(src);
		return this.dist.get(dest);
	}

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
	@Override
	public List<node_info> shortestPath(int src, int dest) {
		djikstra(src);
		
		List<node_info> res = new ArrayList<>();
		
		int runner = dest;
			
		res.add(this.graph.getNode(runner));
		
		while (true) {
			
			if (runner == src) break;
			
			res.add(this.graph.getNode(parent.get(runner)));
			runner = parent.get(runner);

		}
		
		List<node_info> back_result = new ArrayList<node_info>();
		
		for (int i = res.size() - 1; i >= 0; i--) {
			back_result.add(res.get(i));
		}
		
		return back_result;
	}

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
	@Override
	public boolean save(String file) {
		// TODO Auto-generated method stub
		
		
		try {
			
			
			PrintWriter pw=new PrintWriter(new File(file));
for (node_info node : this.graph.getV()) {
				
				for (node_info ni : this.graph.getV(node.getKey())) {
				
				String str=String.valueOf(node.getKey());
				String str1=String.valueOf(ni.getKey());
				String w=String.valueOf(this.graph.getEdge(node.getKey(), ni.getKey()));
				pw.write(str+">"+w+">"+str1);
				pw.write("\n");

				
			}

}
			pw.close();
			return true;
		
		}
		
		
		catch (FileNotFoundException e) {
			return false;
			
		}
		
		
	}

	/**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
	@Override
	public boolean load(String file) {
		// TODO Auto-generated method stub
		
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(file) );
			String line="";
			String SPLIT_BY=">";
			int node1,node2;
			double w;
			while((line=br.readLine()) !=null)
					{
				String[] userinfo=line.split(SPLIT_BY);
				node1=Integer.valueOf(userinfo[0]);
				w=Double.valueOf(userinfo[1]);
				node2=Integer.parseInt(userinfo[2]);
				this.graph.connect(node1, node2, w);
			
					}			
			return true;
		
		}
		
		catch (IOException  e) {
			e.printStackTrace();
			return false;
		}
		
		
		
	}

	
}
