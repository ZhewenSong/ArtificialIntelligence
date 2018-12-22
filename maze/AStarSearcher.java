import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();
		State state = new State(maze.getPlayerSquare(), null, 0, 0); 
		int xs = maze.getPlayerSquare().X;
		int ys = maze.getPlayerSquare().Y;
		int xg = maze.getGoalSquare().X;
		int yg = maze.getGoalSquare().Y;
		StateFValuePair curr = null;
		double fValue = Math.abs(xs-xg) + Math.abs(ys-yg); // initial fValue
		frontier.add(new StateFValuePair(state, fValue)); // adding start point to PQ
		
		while (!frontier.isEmpty()) {
			if (this.maxSizeOfFrontier < frontier.size())
				this.maxSizeOfFrontier = frontier.size();
			curr = frontier.poll(); 
			State scurr = curr.getState();
			if (scurr.getDepth() > this.maxDepthSearched)
				this.maxDepthSearched = scurr.getDepth();
			this.noOfNodesExpanded ++;
			if (scurr.isGoal(maze)) {
				scurr = scurr.getParent();
				while (scurr.getParent() != null) { // plotting solution
					maze.setOneSquare(scurr.getSquare(), '.');
					scurr = scurr.getParent();
					this.cost ++;
				}
				this.cost ++;
				return true;
			}
			
			explored[curr.getState().getX()][curr.getState().getY()] = true;
			ArrayList<State> successors = curr.getState().getSuccessors(explored, maze);
			for (State s : successors) {
				fValue = s.getGValue()
						 + Math.abs(s.getX()-xg) + Math.abs(s.getY()-yg);
				StateFValuePair newState = new StateFValuePair(s, fValue);	
				updateElement(frontier, newState);
				
			}
		}

		// return false;
		return true;
	}

	private void updateElement(PriorityQueue<StateFValuePair> frontier, StateFValuePair newState) {
		for (StateFValuePair s : frontier) { 
			if (s.getState().getX() == newState.getState().getX() &&
					s.getState().getY() == newState.getState().getY()	) {  // find if there is any same state in frontier
				if (s.getState().getGValue() > newState.getState().getGValue()) { // if new state has a lower gValue
					frontier.remove(s); // replace the old state with new state
					frontier.add(newState);					
					return;
				} else return;
			}
		}
		frontier.add(newState); // directly add state if there are not identical states in frontier
	}
}
