import java.util.PriorityQueue;
public class tests {
	public static void main(String[] args) {
		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

		frontier.add(new StateFValuePair(new State(new Square(2,3), null, 0, 2), 1.0));
		frontier.add(new StateFValuePair(new State(new Square(3,3), null, 0, 1), 1.0));
		frontier.add(new StateFValuePair(new State(new Square(4,3), null, 0, 9), 1.0));
		StateFValuePair newState = new StateFValuePair(new State(new Square(2,3), null, 0, 2), 0.0);

		for (StateFValuePair s : frontier) {

			if (s.getState().getSquare().X == (newState.getState().getSquare().X) &&
					s.getState().getSquare().Y == (newState.getState().getSquare().Y)	) 
				if (s.getFValue() > newState.getFValue()) {
					frontier.remove(s);
					frontier.add(newState);
					System.out.println(newState.getFValue());
					break;
				} 
		}
		//System.out.println(frontier.poll().getState().getDepth());
		//System.out.println(frontier.poll().getState().getDepth());
		//System.out.println(frontier.poll().getState().getDepth());
	}
}
