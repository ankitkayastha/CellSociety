
public class CellGameOfLife extends Cell {
	private int DEAD = 0;
	private int ALIVE = 1;
	
	
	public CellGameOfLife(int currentState, int nextState) {
		super(currentState, nextState);
	}
	
	public void getNextState(Grid myGrid, Cell myCell) {
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
