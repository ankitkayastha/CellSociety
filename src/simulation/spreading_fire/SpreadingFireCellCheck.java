package simulation.spreading_fire;

import java.util.List;
import java.util.Random;

import model.Cell;

public class SpreadingFireCellCheck {

	private final int TREE = 1; // non burning tree
	private final int BURNING = 2; // burning tree
	private Random myRandom = new Random();
	private final String characteristicFire = "fire";

	public boolean areNeighborsBurning(List<Cell> cellNeighbors) {
		int numBurningNeighbors = 0;
		for (Cell cell : cellNeighbors) {
			if (cell.getChars().get(characteristicFire) == BURNING)
				numBurningNeighbors++;
		}
		return numBurningNeighbors > 0;
	}

	public int generateProbCatchState(double probToCheck) {
		double prob = myRandom.nextDouble();
		if (prob < probToCheck) {
			return BURNING;
		} else
			return TREE;
	}
}
