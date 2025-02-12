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
Info info = new Info();
if(this.i==1) {
info.makePQueue(new f1Comparator());
}
else {
info.makePQueue(new f2Comparator());
}
 //making a priority queue with one of the heuristics determine the Comparator
BoardNode node = initialNode;
info.pQueue.add(node);

while(!(info.pQueue.isEmpty())) {
node = info.pQueue.poll();
info.incTime();

if (!info.visited.containsKey(node.hashCode()) || node.getMaxCost() < info.visited.get(node.hashCode()).getMaxCost()) {
info.visited.put(node.hashCode(), node);
}

if(node.isGaol()) {
PathActions p = new PathActions(initialNode,node,info); // class that creates a path from goal to start Node if goal is reached.
p.printPath(); // the path is then printed
return true;
}

Successor s = new Successor(); // Successor class created to provide next possible moves from current node
List<BoardNode> list = s.successor(node); // list of potential children

for (BoardNode temp : list) {
// Check if node has been visited or if there's a better path
if (!info.visited.containsKey(temp.hashCode()) || temp.getMaxCost() < info.visited.get(temp.hashCode()).getMaxCost()) {
info.visited.put(temp.hashCode(), temp);

// Ensure the priority queue always holds the best path
if (!info.visited.containsKey(temp.hashCode()) || temp.getMaxCost() < info.visited.get(temp.hashCode()).getMaxCost()) {
info.visited.put(temp.hashCode(), temp);
if (info.pQueue.contains(temp)) {
info.pQueue.remove(temp);
}
info.pQueue.add(temp);
}

info.pQueue.add(temp);
info.pQueueSize();
}
}

}

return false;
}
}