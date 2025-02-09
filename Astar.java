package puzzle8;

import java.util.Comparator;
import java.util.List;

public class Astar implements Search {
	//Astar class that creates the Astar search
	private BoardNode initialNode;
	private int i;
	
	public Astar(BoardNode node, int i) {
		this.initialNode = node; 
		this.i = i; // this int value helps determine which heuristic will be used
	}
	
	private class f1Comparator implements Comparator<BoardNode>{  //comparator for tiles misplaced heuristic that will be used in Priority Queue
		
		Heuristics h = new Heuristics();
		
		public int compare(BoardNode a, BoardNode b) {
			return (a.getMaxCost() + h.numCorPos(a)) - (b.getMaxCost()+h.numCorPos(b));
		}
	}
	

	private class f2Comparator implements Comparator<BoardNode>{ //comparator for manhattan heuristic and totalCost 
		
		Heuristics h = new Heuristics();
		
		public int compare(BoardNode a, BoardNode b) {
			return (a.getMaxCost() + h.manhattan(a)) - (b.getMaxCost()+h.manhattan(b));
		}
	}
	
public boolean search() {
		//Astar search which creates a priority queue which sorts according to h(n)
		// TODO - Create an algorithm using the pseudo-code templete to use Astar search method
		// Manhattan implementation in other file
				return false;
			}

}
