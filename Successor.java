package puzzle8;

import java.util.ArrayList;
import java.util.List;

public class Successor {

	public Successor() {
		
	}
	
	public List<BoardNode> successor(BoardNode node) {
		//successor function that takes a state and returns a list of possible states that can be reached
		
		List<BoardNode> list = new ArrayList<BoardNode>();
		
		int row = node.getRowBlank();
		int col = node.getColBlank();
		
		// TODO - make an algorithm that traverses the zero tile on it's options and moves it accordingly
		// Then, return the list at the end
		return list;  // a list of children is returned.
		 
	}
}
