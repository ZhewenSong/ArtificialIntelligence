import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Depth-First Search (DFS)
 * 
 * You should fill the search() method of this class.
 */
public class DepthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public DepthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main depth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		LinkedList<State> stack = new LinkedList<State>();
		
		stack.push(new State(maze.getPlayerSquare(), null, 0, 0)); // adding starting point to stack
		State curr = null;
		while (!stack.isEmpty()) {
			curr = stack.pop();
			this.maxDepthSearched = Math.max(this.maxDepthSearched, curr.getDepth()); 
			this.noOfNodesExpanded ++;
			if (curr.isGoal(maze)) { 
				curr = curr.getParent(); 
				while (curr.getParent() != null) { // plotting solution
					maze.setOneSquare(curr.getSquare(), '.');
					curr = curr.getParent();
					this.cost ++;
				}
				this.cost ++;
				return true;
			}
			
			ArrayList<State> successors = curr.getSuccessors(explored, maze); 
			for (State s : successors) {
				if (!isCycle(s)) stack.push(s); // if not in cycle, push to stack
			}
				
			this.maxSizeOfFrontier = Math.max(this.maxSizeOfFrontier, stack.size());

			
		}

		return false;
	}
	
	private boolean isCycle(State s) { // checking cycles
		State curr = s;
		s = s.getParent();
		while (s != null) {
			if (s.getX() == curr.getX() && s.getY() == curr.getY()) return true;
			s = s.getParent();
			
		}
		return false;
	}
	
	
}
