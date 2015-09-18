import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.*;

public abstract class SpreadingFire extends Simulation {
	// simulation ends when no burning tree left
	// diagonal neighbors do not affect state of current cell
	// define the three states in terms of integers
	private final int EMPTY = 0; // empty ground or burnt tree
	private final int TREE = 1; // non burning tree
	private final int BURNING = 2; // burning tree
	private double probCatch; // probability of a cell catching fire based on
								// its neighbors
	private Random myRandom = new Random();
	private int numRows;
	private int numCols;
	private final String characteristicFire = "fire";

	public SpreadingFire(Map<String, Integer> globalChars) {
		super(globalChars);
		//probCatch = globalChars.get("prob");

	}

	@Override
	public void update(Grid myGrid, Reader myReader) {
		Cell[] oldGrid = new Cell[myReader.getSize()];
		Cell[] myGridGrid = myGrid.getGrid();

		for (int i = 0; i < myReader.getSize(); i++) {
			Cell currentCell = myGrid.getCell(i);
			Map<String, Integer> myMap = currentCell.getChars();
			Map<String, Integer> oldMap = new HashMap<String, Integer>();
			for (String s : myMap.keySet()) {
				// maybe make string/integer primitive
				oldMap.put(s, myMap.get(s));
			}
			Cell oldCell = new Cell(oldMap);
			oldGrid[i] = oldCell;

		}

		for (int i = 0; i < myReader.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];
			List<Cell> cellNeighbors = findNeighbors(oldGrid, i, myReader);

			for (Cell cell : cellNeighbors) {
				if (oldCell.getChars().get(characteristicFire) == TREE
						&& cell.getChars().get(characteristicFire) == BURNING) {
					myCell.getChars().put(characteristicFire, generateProbCatchState());
				}
			}
			if (oldCell.getChars().get(characteristicFire) == BURNING) {
				myCell.getChars().put(characteristicFire, EMPTY);
			}

		}
	}

	public int generateProbCatchState() {
		double prob = myRandom.nextDouble();
		if (prob < probCatch) {
			return BURNING;
		} else
			return TREE;
	}

	@Override
	public Color getCellColor(int index, Grid myGrid) {
		Cell myCell = myGrid.getCell(index);
		if (myCell.getChars().get(characteristicFire) == BURNING)
			return Color.RED;
		else if (myCell.getChars().get(characteristicFire) == EMPTY)
			return Color.YELLOW;
		else
			return Color.GREEN;
	}

	public abstract List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader);
}
