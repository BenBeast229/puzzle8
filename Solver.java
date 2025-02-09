package puzzle8;
import java.util.Scanner;

public class Solver {
	public static void main(String[] args) {
		
		// All the boards.
		int [][] easy = {{1,3,4},{8,6,2},{7,0,5}}; // first test case on easy difficulty
		int [][] medium = {{2,8,1},{0,4,3},{7,6,5}}; // second test case on medium difficulty
		int [][] hard = {{5,6,7},{4,0,8},{3,2,1}}; // third test case on hard difficulty

		// TODO - fix these test cases to take any inputs from the user

		// Initial Nodes for 3 Levels
		BoardNode easyNode = new BoardNode(easy);
		BoardNode mediumNode = new BoardNode(medium);
		BoardNode hardNode = new BoardNode(hard);
		
		// search waiting to be initialized
		Search search = null;
		
		// Simple UI which prompts the User for an algorithm and difficulty level
		boolean con = true;

	while(con==true){  //the loop keeps going till User says no more to using the solver
		System.out.println();
		System.out.println("Welcome to 8 puzzle"); //Below are the options asking User for which search and what difficulty to pick
		System.out.println("Please chose an Algorithm below:");
		System.out.println();
		System.out.println();
		
		System.out.println("1. UniformCost");
	
		System.out.println("2. A*");
		System.out.println();
		Scanner scanner = new Scanner(System.in); // TODO - FIX "Resource leak: 'scanner' is never closed" warning
		int input = scanner.nextInt(); // variable that saves user's first input of which algorithm to use
		
		System.out.println("Now chose a difficulty");
		System.out.println();
		System.out.println();
		System.out.println("1. Easy");
		
		System.out.println("2. Medium");
		
		System.out.println("3. Hard");
		System.out.println();
		
		int input2 = scanner.nextInt(); // variable that saves user's second input of difficulty
		
		switch(input) { //switch is used to determine what search and difficulty to use
			
		case 1:							
			switch(input2){ // second switch is used to determine the difficulty and set the node's to their respective parameters
				case 1: 
					search = new UniformCost(easyNode);
					break;
				case 2: 
					search = new UniformCost(mediumNode);
					break;
				case 3: 
					search = new UniformCost(hardNode);
					break;
			}

			break;
			
		case 2:  //final case for A* which provides options for both Heuristics
			System.out.println();
			System.out.println("This is the A* algorithm, please pick a heuristic: ");
			System.out.println();
			System.out.println("1. Misplaced Tiles");
			System.out.println("2. Manhattan");
			System.out.println();
			int input3 = scanner.nextInt();
			
			switch(input3){	// third switch that chooses which A* algorithm to use		
				case 1: 
					switch(input2) { // second switch that sets difficulty for Misplaced Tiles method
					
					case 1:
						search = new Astar(easyNode,1);
						break;
					
					case 2: 
						search = new Astar(mediumNode,1);
						break;
					case 3: 
						search = new Astar(hardNode,1);
						break;
				}
				
				break;

				case 2:
						switch(input2) { // second switch that sets difficulty for Manhattan search
					
					case 1:
						search = new Astar(easyNode,2);
						break;
					
					case 2: 
						search = new Astar(mediumNode,2);
						break;
					case 3: 
						search = new Astar(hardNode,2);
						break;
				}
			}
					
		}
		
		System.out.println("Okay! Ready to Go!");
		System.out.println("Hit any key!"); // prompt to begin search 
		System.out.println();
		System.out.println("The search will begin: ");
		search.search(); //the search starts
		System.out.println("Do you want to continue?");
		System.out.println();
		System.out.println("1. Yes");
		System.out.println("2. No");
		int input5 = scanner.nextInt();
		if(input5==2) {
			con = false; // ends the program 
		}	
	}	
	}
}