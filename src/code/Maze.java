package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import given.iDeque;
import given.iSimpleContainer;

/* * 
 * 
 * ASSIGNMENT 2-Part2
 * STUDENT AUTHOR:  Eren Deniz Sanlıer
 *
 * MODIFY 
 * 
 * */

public class Maze {

	// Characters that define the maze
	char O = 'O'; // allowable cells
	char I = 'I'; // walls
	char S = 'S'; // start point of the Maze
	char E = 'E'; // exit cell
	char visited = '*'; // visited cells

	char[][] currentMaze;
	int rows;
	int cols;

	/*
	 * ADD FIELDS IF NEEDED
	 * No need
	 */

	public Maze() {
		/*
		 * 
		 * Implement a constructor if needed
		 * No need
		 */
	}

	public static class Coordinate {
		int x;
		int y;

		public Coordinate(int r, int c) {
			x = r;
			y = c;
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (!Coordinate.class.isAssignableFrom(obj.getClass())) {
				return false;
			}
			final Coordinate other = (Coordinate) obj;
			if (this.x == other.x && this.y == other.y)
				return true;
			else
				return false;
		}

		/*
		 * ADD CODE IF NEEDED
		 * No need
		 */
	}

	/*
	 * This method loads a maze given in a file. It is given for you. It reads the
	 * given maze file and load it into a 2-D char array
	 * 
	 * Feel free to modify it if needed.
	 * 
	 */
	public void loadMaze(String fileName) throws IOException {

		// BARIS: ASK THEM TO MODIFY?

		BufferedReader br1 = new BufferedReader(new FileReader(fileName));
		String line1;

		line1 = br1.readLine();
		rows = 0;

		while (line1 != null) { // gets the size of the maze

			line1 = br1.readLine();
			rows++;
		}
		br1.close();

		BufferedReader br2 = new BufferedReader(new FileReader(fileName));
		currentMaze = new char[rows][]; // creates a char array with maze size

		String line2;
		int i = 0;

		while ((line2 = br2.readLine()) != null) // loads maze elements to 2-D char array
		{
			String newStr = line2.replaceAll(", ", "");
			currentMaze[i++] = newStr.toCharArray();
		}

		cols = currentMaze[0].length;

		br2.close();
	}

	// Prints the maze if you want to debug
	public String toString() {
		int i = 0, j = 0;
		StringBuilder sb = new StringBuilder(1000);
		sb.append("Maze: " + rows + " x " + cols);

		for (; i < rows; i++) {
			for (; j < cols - 1; j++) {
				sb.append(currentMaze[i][j] + ", ");
			}
			sb.append(currentMaze[i][j] + System.lineSeparator());
		}

		return sb.toString();
	}

	/*
	 * ADD METHODS IF NEEDED
	 * no need
	 */

	/*
	 * Implement the algorithm given in the document in pseudocode form to solve the
	 * maze: i.e. find a path from the start coordinate to the end coordinate.
	 * 
	 * The algorithm takes in a container which changes its behavior, a deque to be
	 * filled from the back (we could have used something from Java but going with
	 * custom made DSes)
	 * 
	 * You are required to fill in the input iDeque with the visited nodes in the
	 * given order in a first-in first-out manner.
	 * 
	 * The neighbors of a coordinate follow the 4-neighborhood rule, i.e., they can
	 * be UP, DOWN, LEFT, RIGHT. Although it does not matter which order you push
	 * the neighbors to the container, we are going to impose an order for the
	 * autograder: 1) Decrease the y coordinate 2) Increase the x coordinate 3)
	 * Increase the y coordinate 4) Decrease the x coordinate
	 * 
	 * It returns true is an exit is fond, false otherwise.
	 * 
	 */
	public boolean solveMaze(iSimpleContainer<Coordinate> sc, iDeque<Coordinate> visitedNodes, String mazeName)
			throws IOException {
		loadMaze(mazeName);
		Coordinate currentState = getStartState(currentMaze);
		sc.push(currentState);

		while(!sc.isEmpty()) {

			currentState = sc.pop();

			if(isExit(currentState.x, currentState.y)){
				return true;

			}else if(!isVisited(currentState)) {

				markVisited(currentState, visited);
				
				for(int i = 0; i < 4; i++) {
					Coordinate neighbor = null;
					switch(i) {
					case 0:
						neighbor = new Coordinate(currentState.x, currentState.y - 1);
						break;
					case 1:
						neighbor = new Coordinate(currentState.x + 1, currentState.y);
						break;
					case 2:
						neighbor = new Coordinate(currentState.x, currentState.y + 1);
						break;
					case 3:
						neighbor = new Coordinate(currentState.x - 1, currentState.y);
						break;
					}
					if (isInMaze(neighbor)) {
						sc.push(neighbor);
					}
					
				}


				
				visitedNodes.addBehind(currentState);
			}
		}


		return false;
	}

	/*
	 * 
	 * The below functions are given to you as suggestions. You can use them in the
	 * solveMaze function. However, we will not check these explicitly
	 * ty for help
	 */

	// Helper method which marks a coordinate as visited
	private void markVisited(Coordinate c, char val) {
		if(currentMaze[c.x][c.y] == O) currentMaze[c.x][c.y] = val;
	}

	// Helper method which checks if the coordinate has been visited before
	private boolean isVisited(Coordinate c) {

		if(currentMaze[c.x][c.y] == visited) return true;
		else return false;
	}

	// Helper method which checks if the coordinate is within the maze or not
	private boolean isInMaze(Coordinate c) {
		if(c.x >= rows || c.y >= cols || c.x < 0 || c.y < 0) return false;
		else if (currentMaze[c.x][c.y] == O || currentMaze[c.x][c.y] == E)
			return true;
		else return false;
	}

	// Helper method which checks if the coordinate is an exit or not
	private boolean isExit(int row, int col) {

		if(currentMaze[row][col] == E) return true;
		else return false;
	}

	// Returns the start state from the maze
	private Coordinate getStartState(char[][] maze) {

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if(maze[i][j] == S) return new Coordinate(i, j);
			}
		}
		return null;
	}
}
