package puzzle8;
import java.util.HashSet;
import java.util.Scanner;

public class Solver {
	public static void main(String[] args) {
		// initialize user data storing method with scanner
		int[][] userInput = new int[3][3];
		BoardNode userNode = new BoardNode(userInput);
		Scanner scanner = new Scanner(System.in); 
		HashSet<Integer> trackIntegers = new HashSet<>();

		// search waiting to be initialized
		Search search = null;
		
		// Simple UI which prompts the User for an algorithm and difficulty level
		boolean con = true;

	while(con==true){  //the loop keeps going till User says no more to using the solver
		System.out.println();
		System.out.println("Welcome to 8 puzzle"); //Below are the options asking User for which search and what difficulty to pick
		System.out.println("Enter tiles in order going from top left all the way to bottom right going across (with 0 being the empty tile)");
		System.out.println();

		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                while (true) { // Keep asking until a valid input is received
                    if (!scanner.hasNextInt()) { // Check if the input is an integer
                        System.out.println("Invalid input! Please enter a valid integer.");
                        scanner.next(); // Discard the invalid input
                        continue; // Ask for input again
                    }

                    int num = scanner.nextInt();

                    if (num == 9) { // Check if the number is 9
                        System.out.println("Error! The number 9 is not allowed. Please enter a different number.");
                    } else if (trackIntegers.contains(num)) { // Check for duplicates
                        System.out.println("Number already entered! Please enter a unique number.");
                    } else {
                        trackIntegers.add(num); // Add valid number to HashSet
                        userInput[i][j] = num; // Store in matrix
                        break; // Exit the loop since input is valid
                    }
                }
            }
        }
		
		System.out.println("Please chose an Algorithm below:");
		System.out.println();
		System.out.println();
		
		System.out.println("1. UniformCost");
	
		System.out.println("2. A*");
		System.out.println();
		int input = scanner.nextInt(); // variable that saves user's first input of which algorithm to use
		
		switch(input) { //switch is used to determine what search and difficulty to use
			
		case 1:							
			search = new UniformCost(userNode);
			
		case 2:  //final case for A* which provides options for both Heuristics
			System.out.println();
			System.out.println("This is the A* algorithm, please pick a heuristic: ");
			System.out.println();
			System.out.println("1. Misplaced Tiles");
			System.out.println("2. Manhattan");
			System.out.println();
			int input2 = scanner.nextInt();
			
			switch(input2){	// third switch that chooses which A* algorithm to use		
				case 1: 
					search = new Astar(userNode,1);
				case 2:
					search = new Astar(userNode,2);
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
