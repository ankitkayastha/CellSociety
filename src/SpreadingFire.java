import java.util.*;

public class SpreadingFire extends Simulation {
	//simulation ends when no burning tree left 
	//diagonal neighbors do not affect state of current cell
	//define the three states in terms of integers
	private final int EMPTY = 0; //empty ground or burnt tree
	private final int TREE = 1; //non burning tree
	private final int BURNING = 2; //burning tree
	private double probCatch = 0.15; //probability of a cell catching fire based on its neighbors
	private Random myRandom = new Random();
	
	public void getNextState(Cell myCell) {
		if (myCell.getCurrentState() == EMPTY) {
			myCell.setNextState(EMPTY);
		}
		if (myCell.getCurrentState() == BURNING) {
			myCell.setNextState(EMPTY);
		}
		for (Cell cell: getNeighbors()) {
			if (myCell.getCurrentState() == TREE && cell.getCurrentState() == BURNING) {
				myCell.setNextState(generateProbCatchState());
			}
		}
	}
	
	public int generateProbCatchState() {
		double prob = myRandom.nextDouble();
		if (prob < probCatch) {
			return BURNING;
		}
		else
			return TREE;
	}
	
	/*
	 * determines whether simulation has finished
	 */
	public boolean isFinished(Grid<Cell, Cell> myGrid) {
		int numBurningTrees = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (myGrid[ROW][COL].getCurrentState() != BURNING)
					numBurningTrees++;
			}
			if (numBurningTrees == 0) {
				return true;
			}
		}
	}
}
