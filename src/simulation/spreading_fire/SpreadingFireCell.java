package simulation.spreading_fire;

import java.util.*;
import javafx.scene.paint.Color;
import model.Cell;

public class SpreadingFireCell extends Cell {
	private final int EMPTY = 0; // empty ground or burnt tree
	private final int TREE = 1; // non burning tree
	private final int BURNING = 2; // burning tree
	private Random myRandom = new Random();
	private final String characteristicFire = "fire";

	public SpreadingFireCell(Map<String, Integer> characteristicMap, Map<Integer, Color> colorMap) {
		super(characteristicMap, colorMap);
	}

	@Override
	public void fillColorMap() {
		getColorMap().put(EMPTY, Color.WHITE);
		getColorMap().put(TREE, Color.GREEN);
		getColorMap().put(BURNING, Color.RED);
	}

	@Override
	public Color getCellColor(int state) {
		return getColorMap().get(state);
	}
	
	private boolean areNeighborsBurning(List<Cell> cellNeighbors) {
		int numBurningNeighbors = 0;
		for (Cell cell: cellNeighbors) {
			if (cell.getChars().get(characteristicFire) == BURNING)
				numBurningNeighbors++;
		}
		return numBurningNeighbors > 0;
	}
	
	private int generateProbCatchState() {
		double prob = myRandom.nextDouble();
		if (prob < probCatch) {
			return BURNING;
		} else
			return TREE;
	}

}
