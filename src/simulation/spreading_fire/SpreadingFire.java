package simulation.spreading_fire;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import model.Cell;
import model.Grid;
import model.NeighborFactory;
import model.Reader;
import simulation.Simulation;

public class SpreadingFire extends Simulation {
	private final int EMPTY = 0; // empty ground or burnt tree
	private final int TREE = 1; // non burning tree
	private final int BURNING = 2; // burning tree
	private double probCatch; // probability of a cell catching fire based on
	private Random myRandom = new Random();
	private final String characteristicFire = "fire";

	public SpreadingFire(Map<String, Integer> globalChars) {
		super(globalChars);
		if (globalChars.keySet().contains("prob")) {
			probCatch = globalChars.get("prob")/100.0;
		}
	}

	@Override
	public void update(Grid myGrid, Stats myStats) {
		Cell[] oldGrid = super.copyGrid(myGrid, myStats);
		Cell[] myGridGrid = myGrid.getGrid();

		for (int i = 0; i < myStats.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];
			NeighborFactory myNeighborFactory = new NeighborFactory(myStats, super.thisSim, super.thisShape);
			
			List<Cell> cellNeighbors = myNeighborFactory.getNeighbors(oldGrid, i);
			if (oldCell.getChars().get(characteristicFire) == TREE) {
				if (areNeighborsBurning(cellNeighbors)) {
						myCell.getChars().put(characteristicFire, generateProbCatchState());
				}
			}			
			if (oldCell.getChars().get(characteristicFire) == BURNING) {
				myCell.getChars().put(characteristicFire, EMPTY);
			}
		}
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
