package puzzle8;

import java.util.ArrayList;
import java.util.List;

public class Successor {
public List<BoardNode> successor(BoardNode node) {
//successor function that takes a state and returns a list of possible states that can be reached

List<BoardNode> list = new ArrayList<BoardNode>();

int row = node.getRowBlank();
int col = node.getColBlank();

// Move UP (row should not be the first row)
if (row > 0) {  
BoardNode upNode = node.createChild(row - 1, col);
upNode.setDir(DIRECTIONS.UP);
list.add(upNode);
}

// Move DOWN (row should not be the last row)
if (row < 2) {  
BoardNode downNode = node.createChild(row + 1, col);
downNode.setDir(DIRECTIONS.DOWN);
list.add(downNode);
}

// Move RIGHT (col should not be the last column)
if (col < 2) {  
BoardNode rightNode = node.createChild(row, col + 1);
rightNode.setDir(DIRECTIONS.RIGHT);
list.add(rightNode);
}

// Move LEFT (col should not be the first column)
if (col > 0) {  
BoardNode leftNode = node.createChild(row, col - 1);
leftNode.setDir(DIRECTIONS.LEFT);
list.add(leftNode);
}
return list;  // a list of children is returned.
}
}