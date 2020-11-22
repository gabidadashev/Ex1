package ex1;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class GabiTest_WGraph_Algo {
	
public static weighted_graph_algorithms MakeMeGraph()
{
	weighted_graph gabitestgraph = new WGraph_DS();
	for(int i=1;i<=1000;i++)
	{
	gabitestgraph.addNode(i);
	}
	
	int count =100001; /// Make me 10000 Million Connection 
	while(count>0)
	{
		int node1= ThreadLocalRandom.current().nextInt(1, 1001);
		int node2= ThreadLocalRandom.current().nextInt(1, 1001);
		double w=ThreadLocalRandom.current().nextInt(0, 1001);
		gabitestgraph.connect(node1, node2, w);
		count--;
		
	}
	weighted_graph_algorithms GabiAlgo=new WGraph_Algo();
	GabiAlgo.init(gabitestgraph);
	return GabiAlgo;
}

	
	@Test
	void testConectedAndRemove() {
		weighted_graph_algorithms g=MakeMeGraph();
		weighted_graph gr=g.getGraph();
		gr.connect(5, 10, 200);
		gr.connect(3, 5, 200);
		gr.connect(1765, 757, 780);
		gr.connect(5, 10, 270);
		gr.removeEdge(5, 3);
		boolean a,b;
		a=gr.hasEdge(3, 5);
		b=gr.hasEdge(5, 10);
		assertTrue((a==false)&&(b==true));
		
	}
	@Test
	void ShotPath() {
		weighted_graph gabitestgraph = new WGraph_DS();
		for(int i=1;i<=5;i++)
		{
		gabitestgraph.addNode(i);
		}
		gabitestgraph.connect(1, 2, 54);
		gabitestgraph.connect(1, 3, 34);
		gabitestgraph.connect(1, 4, 14);
		gabitestgraph.connect(4, 5, 20);
		gabitestgraph.connect(4, 2, 50);
		gabitestgraph.connect(5, 2, 4);
		gabitestgraph.connect(1, 3, 12);
		weighted_graph_algorithms GabiAlgo=new WGraph_Algo();
		GabiAlgo.init(gabitestgraph);
		double d=GabiAlgo.shortestPathDist(2,4);
		assertTrue(d==24);
		
		
	}
	@Test
	void ShotPathMaslol() {
		weighted_graph gabitestgraph = new WGraph_DS();
		for(int i=1;i<=5;i++)
		{
		gabitestgraph.addNode(i);
		}
		gabitestgraph.connect(1, 2, 54);
		gabitestgraph.connect(1, 3, 34);
		gabitestgraph.connect(1, 4, 14);
		gabitestgraph.connect(4, 5, 20);
		gabitestgraph.connect(4, 2, 50);
		gabitestgraph.connect(5, 2, 4);
		gabitestgraph.connect(1, 3, 12);
		weighted_graph_algorithms GabiAlgo=new WGraph_Algo();
		GabiAlgo.init(gabitestgraph);
		List<node_info> a1=GabiAlgo.shortestPath(2, 4);
		assertTrue((a1.get(0).getKey()==2)&&(a1.get(1).getKey()==5)&&(a1.get(2).getKey()==4));

		
		
	}


}
