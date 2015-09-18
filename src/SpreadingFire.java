import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.*;
public abstract class SpreadingFire extends Simulation {


	//simulation ends when no burning tree left 
	//diagonal neighbors do not affect state of current cell
	//define the three states in terms of integers
	private final int EMPTY = 0; //empty ground or burnt tree
	private final int TREE = 1; //non burning tree
	private final int BURNING = 2; //burning tree
	private double probCatch = 0.15; //probability of a cell catching fire based on its neighbors
	private Random myRandom = new Random();
	private int numRows;
	private int numCols;
	private final String characteristicFire = "fire";
	
	@Override
	public void update(Grid currentGrid, Grid newGrid, Reader myReader, int index) {
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell myCurrentStateCell = currentGrid.getCell(i);
			Cell myNextStateCell = newGrid.getCell(i);
			if (myCurrentStateCell.getChars().get(characteristicFire) == EMPTY) {
				myNextStateCell.getChars().put(characteristicFire, EMPTY);
		}
		if (myCurrentStateCell.getChars().get(characteristicFire) == BURNING) {
			myNextStateCell.getChars().put(characteristicFire, EMPTY);
		}
		List<Cell> cellNeighbors = findNeighbors(currentGrid, index, myReader);
		for (Cell cell: findNeighbors(currentGrid, index, myReader)) {
			if (myCurrentStateCell.getChars().get(characteristicFire) == TREE && cell.getChars().get(characteristicFire) == BURNING) {
				myNextStateCell.getChars().put(characteristicFire, generateProbCatchState());
			}
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
	public abstract List<Cell> findNeighbors(Grid currentGrid, int index, Reader myReader);
	public abstract Shape getCellShape(int i);

	@Override
	public Color getCellColor(Cell myCell) {
		if (myCell.getChars().get(characteristicFire) == BURNING)
			return Color.RED;
		else if (myCell.getChars().get(characteristicFire) == EMPTY)
			return Color.YELLOW;
		else
			return Color.GREEN;
	}
}
