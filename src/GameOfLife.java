
public class GameOfLife extends Simulation {
	private int DEAD = 0;
	private int ALIVE = 1;
	
	public void getNextState(Cell myCell) {
		if (myCell.getCurrentState() == ALIVE) {
			if (numLiveNeighbors() == 2 || numLiveNeighbors() == 3)) {
				myCell.setNextState(ALIVE);
			}
			else {
				myCell.setNextState(DEAD);
			}
		}
		else if (myCell.getCurrentState() == DEAD)
			if (numLiveNeighbors() == 3) {
				myCell.setNextState(ALIVE);
			}
			else {
				myCell.setNextState(DEAD);
			}
	}
}

