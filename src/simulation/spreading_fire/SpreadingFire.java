package simulation.spreading_fire;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import model.Cell;
import model.Grid;
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
	public void update(Grid myGrid, Reader myReader) {
		Cell[] oldGrid = super.copyGrid(myGrid, myReader);
		Cell[] myGridGrid = myGrid.getGrid();

		for (int i = 0; i < myReader.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];
			List<Cell> cellNeighbors = findNeighbors(oldGrid, i, myReader);

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

	@Override
	public Color getCellColor(int index, Grid myGrid) {
		Cell myCell = myGrid.getCell(index);
		if (!myCell.getChars().keySet().contains(characteristicFire)) {
			return Color.WHITE;
		}
		if (myCell.getChars().get(characteristicFire) == BURNING)
			return Color.RED;
		else if (myCell.getChars().get(characteristicFire) == EMPTY)
			return Color.YELLOW;
		else
			return Color.GREEN;
	}

	@Override
	public List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader) {
		int numCols = myReader.getGlobalChars().get("cols"); 
		int numRows = myReader.getGlobalChars().get("rows");
		int rowNum = index / numCols; // row number of cell
		int colNum = index % numCols; // col number of cell
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaRow = { -1, 0, 0, 1};
		int[] deltaCol = { 0, 1, -1, 0};
		int[] arrDelta = { -numCols, 1, -1, numCols};
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum + deltaRow[i], colNum + deltaCol[i], numRows, numCols)) {
				neighborsList.add(myArr[index + arrDelta[i]]);
			}
		}
		return neighborsList;
	}
}
