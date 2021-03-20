import java.util.Random;

public class OpponentAI {
	private static Position[] directions = { new Position(1, 0), new Position(0, 1), new Position(-1, 0), new Position(0, -1) };
	
	private PlayerGrid AIgrid;
	
	private Position firstHit = null;
	private Position direction = null;
	private int lastTriedDirection = -1;
	private int stepNum = 0;
	private boolean invertDirection = false;
	
	private Random r = new Random();
	
	public OpponentAI(PlayerGrid grid) {
		this.AIgrid = grid;
	}
	
	public void guess() {
		if(firstHit == null) {
			Position guess = new Position(r.nextInt(AIgrid.getGridSize()), r.nextInt(AIgrid.getGridSize()));
			int result = AIgrid.guess(guess);
			if(result == 1) {
				firstHit = guess;
				lastTriedDirection = -1;
			}
		} else {
			if(direction == null) {
				lastTriedDirection++;
				stepNum = 1;
				if(lastTriedDirection < 4 && !AIgrid.validGuess(firstHit.add(directions[lastTriedDirection]))) {
					lastTriedDirection++;
				}
				if(lastTriedDirection >= 4) {
					Position guess = new Position(r.nextInt(AIgrid.getGridSize()), r.nextInt(AIgrid.getGridSize()));
					int result = AIgrid.guess(guess);
					if(result == 1) {
						firstHit = guess;
					}
					return;
				}
				int result = AIgrid.guess(firstHit.add(directions[lastTriedDirection]));
				if(result == 1) {
					direction = directions[lastTriedDirection];
				} else if(result == 2) {
					System.out.println("AI sunk your ship!");
					lastTriedDirection = -1;
					firstHit = null;
					direction = null;
				}
			} else {
				if(!invertDirection) {
					stepNum++;
					int result = AIgrid.guess(firstHit.add(direction.multiply(stepNum)));
					if(result == 0) {
						invertDirection = true;
						stepNum = 0;
					} else if(result == 2) {
						lastTriedDirection = -1;
						firstHit = null;
						direction = null;
						System.out.println("AI sunk your ship!");
					}
				} else {
					stepNum--;
					int result = AIgrid.guess(firstHit.add(direction.multiply(-stepNum)));
					if(result != 1) {
						lastTriedDirection = -1;
						firstHit = null;
						direction = null;
						invertDirection = false;
					}
				}
			}
		}
	}
}
