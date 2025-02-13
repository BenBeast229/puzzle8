import heapq
import copy
import time

class EightPuzzleState: # Class to represent a state of the 8-puzzle problem. It initializes the 8 puzzle with all it's parents and children along with the depth and cost of the state.
    def __init__(self, board, parent=None, move=None, depth=0, cost=0):
        self.board = board
        self.parent = parent
        self.move = move
        self.depth = depth
        self.cost = cost
        self.zero_pos = self.find_zero() # Finds the position of the empty space (0)
    
    # Finds the position of the empty space (0) in the board
    def find_zero(self):
        for i in range(3):
            for j in range(3):
                if self.board[i][j] == 0:
                    return (i, j)
        return None
    
    # Defines the less-than operator for comparing states based on cost
    def __lt__(self, other):
        return self.cost < other.cost
    
    # Defines the equality operator for comparing states based on the board configuration
    def __eq__(self, other):
        return self.board == other.board
    
    # Defines the hash function for states based on the board configuration
    def __hash__(self):
        return hash(str(self.board))

# Counts the number of misplaced tiles compared to the goal state using misplace tiled search and uses that to find the path to the goal state. 
def misplaced_tile_heuristic(state, goal):
    count = 0
    for i in range(3):
        for j in range(3):
            if state.board[i][j] != goal[i][j] and state.board[i][j] != 0: # Check if the tile is misplaced
                count += 1
    return count

# Uses the Manhattan distance heuristic to calculate the distance of each tile from it's goal position and uses that to find the most optimal path.
def manhattan_distance_heuristic(state, goal):
    distance = 0
    for i in range(3):
        for j in range(3):
            value = state.board[i][j]
            if value != 0: # Skip the empty space (0)
                x = (value - 1) // 3
                y = (value - 1) % 3
                distance += abs(x - i) + abs(y - j) # Calculate the Manhattan distance using the difference in coordinates
    return distance

# this is just a place holder for the uniform cost search algorithm. Since it doesn't use any heuristic, it always returns 0 and uses the general search algorithm to find the path to the goal state.
def uniform_cost_search(state, goal):
    return 0

# Function to generate all possible valid moves from the current state
def get_neighbors(state):
    moves = {'Up': (-1, 0), 'Down': (1, 0), 'Left': (0, -1), 'Right': (0, 1)} # Defines all possible moves the tiles can take
    neighbors = [] # List to store all the neighbors
    x, y = state.zero_pos
    
    for move, (dx, dy) in moves.items():
        nx, ny = x + dx, y + dy # Calculates the new position after the move
        if 0 <= nx < 3 and 0 <= ny < 3:
            # Create a shallow copy of the board
            new_board = [row[:] for row in state.board]
            # Swap the zero position with the new position
            new_board[x][y], new_board[nx][ny] = new_board[nx][ny], new_board[x][y]
            # Create a new state and add it to the neighbors list
            neighbors.append(EightPuzzleState(new_board, state, move, state.depth + 1))
    
    return neighbors

# General search function that uses the specified algorithm to find the path to the goal state. It uses provided pseudocode as the general search traversal and calls the specified algorithm to find the path to the goal state.
def general_search(search_problem, algorithm):
    priority_queue = [] # Priority queue to store the states based on their cost
    heapq.heappush(priority_queue, (0, search_problem))
    visited = set()
    start_time = time.time()
    max_cost = 0
    last_depth = -1
    nodes_traversed = 0  # Initialize node counter
    
    #Base case if the initial state is the goal state
    if search_problem.board == [[1, 2, 3], [4, 5, 6], [7, 8, 0]]:
        end_time = time.time()
        print("Initial state is the goal state:")
        for row in search_problem.board:
            print(row)
        print(f"Depth: {search_problem.depth}, Cost: {search_problem.cost}, Max Cost: {max_cost}")
        print(f"Solution found in {end_time - start_time:.4f} seconds")
        print(f"Nodes traversed: {nodes_traversed}")
        return solution_found(search_problem, end_time - start_time)
    
    while priority_queue: # Loop until the priority queue is empty
        _, current_state = heapq.heappop(priority_queue) # Pop the state with the lowest cost
        nodes_traversed += 1  # Increment node counter
        
        # Recursive base case to end recursive calls
        if current_state.board == [[1, 2, 3], [4, 5, 6], [7, 8, 0]]:
            end_time = time.time()
            print(f"Nodes traversed: {nodes_traversed}")
            return solution_found(current_state, end_time - start_time)
        
        board_tuple = tuple(tuple(row) for row in current_state.board) # Convert the board to a tuple for hashing
        visited.add(board_tuple) # Add the current state to the visited set
        
        for neighbor in get_neighbors(current_state): # Generates all possible neighbors
            if tuple(tuple(row) for row in neighbor.board) in visited: # Skip the neighbor if it has already been visited
                continue
            neighbor.cost = neighbor.depth + (algorithm(neighbor, [[1, 2, 3], [4, 5, 6], [7, 8, 0]]) if algorithm else 0)
            max_cost = max(max_cost, neighbor.cost)
            heapq.heappush(priority_queue, (neighbor.cost, neighbor))
            
            if neighbor.depth > last_depth: # Print the state if the depth has increased with all moves, cost, depth, and updated puzzle state
                last_depth = neighbor.depth
                print(f"Move: {neighbor.move}")
                for row in neighbor.board:
                    print(row)
                print(f"Depth: {neighbor.depth}, Cost: {neighbor.cost}, Max Cost: {max_cost}\n")
    
    print(f"Nodes traversed: {nodes_traversed}")
    return None

# helper function that takes the path from the solution and creates a local new puzzle with the updated moves and prints the final solution.
def solution_found(state, time_taken):
    direction = []
    while state.parent:
        direction.append(state.move)
        state = state.parent
    print(f"Solution found in {time_taken:.4f} seconds")
    return direction[::-1]

# helper function to initialize general_search recursive algorithm with the selected algorithm and the initial puzzle state.
def solve_puzzle(eight_puzzle_grid, search_algorithm):
    initial_puzzle_state = EightPuzzleState(eight_puzzle_grid)
    
    algorithm = None
    if search_algorithm == 'A* Misplaced Tile':
        algorithm = misplaced_tile_heuristic
    elif search_algorithm == 'A* Manhattan Distance':
        algorithm = manhattan_distance_heuristic
    elif search_algorithm == 'Uniform Cost Search':
        algorithm = uniform_cost_search
    
    return general_search(initial_puzzle_state, algorithm)

# 8 puzzle interface that takes the input from the user and initializes the puzzle with the provided input and then asks the user to select the algorithm to solve the puzzle.
def main():
    print("Welcome to 8-puzzle solver!")
    print("Enter the 8-puzzle board row by row (use 0 for the empty space):")
    eight_puzzle_grid = [] # List to store the 8-puzzle grid
    entered_numbers = set() # Set to store the entered numbers to check for duplicates
    
    for i in range(3): # Loop to get the input for each row
        row = [] # List to store the numbers in the row
        for j in input().split(): 
            try: # Check if the input is a valid integer. Uses a try and catch block to handle invalid inputs including non-integer inputs and number 9.
                input_value = int(j)
                if input_value < 0 or input_value > 8:
                    print("Invalid input. Please enter numbers between 0 and 8.")
                    return
                if input_value in entered_numbers:
                    print(f"Invalid input. The number {input_value} is already in the puzzle.")
                    return
                entered_numbers.add(input_value)
                row.append(input_value)
            except ValueError:
                print("Invalid input. Please enter valid integers 0-8.")
                return
        
        if len(row) != 3: # Check if the row has exactly 3 numbers
            print("Invalid input. Please enter exactly 3 numbers per row.")
            return
        eight_puzzle_grid.append(row)
    
    print("Choose algorithm:")
    print("1. A* Misplaced Tile")
    print("2. A* Manhattan Distance")
    print("3. Uniform Cost Search")
    choice = input("Enter choice (1, 2, or 3): ")
    
    if choice == "1":
        algorithm = "A* Misplaced Tile"
    elif choice == "2":
        algorithm = "A* Manhattan Distance"
    elif choice == "3":
        algorithm = "Uniform Cost Search"
    else:
        print("Invalid choice. Please enter 1, 2, or 3.")
        return
    
    print(f"Solving using {algorithm} algorithm:")
    solution = solve_puzzle(eight_puzzle_grid, algorithm) # Calls the solve_puzzle function and begins the search
    
if __name__ == "__main__":
    main()