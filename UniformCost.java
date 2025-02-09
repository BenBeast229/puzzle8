package puzzle8;

import java.util.Comparator;
import java.util.List;

public class UniformCost implements Search {
	private BoardNode initialNode;
	
	public UniformCost(BoardNode node) {
		this.initialNode = node;
	}
	
	private class gComparator implements Comparator<BoardNode>{
		
		public int compare(BoardNode a, BoardNode b) {
			return a.getMaxCost() - b.getMaxCost();
		}
	}
	
	public boolean search() {
		//UnifromCost search which creates a priority queue which sorts according to g(n)
		
		// TODO - Using the general search algorithm pseudo-code, create a UniformCost Search algorithm that is the least costly on the nodes and returns requested state value
		return false;
	}
}
