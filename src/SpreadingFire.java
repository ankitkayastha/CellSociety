import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.*;

public class SpreadingFire extends Simulation {
	// simulation ends when no burning tree left
	// diagonal neighbors do not affect state of current cell
	// define the three states in terms of integers
	private final int EMPTY = 0; // empty ground or burnt tree
	private final int TREE = 1; // non burning tree
	private final int BURNING = 2; // burning tree
	private double probCatch; // probability of a cell catching fire based on
								// its neighbors
	private Random myRandom = new Random();
	private final String characteristicFire = "fire";

	public SpreadingFire(Map<String, Integer> globalChars) {
		super(globalChars);
		System.out.println("fire globals: " +globalChars.toString());
		if (globalChars.keySet().contains("prob")) {
			probCatch = globalChars.get("prob")/100.0;
		}
		System.out.println("prob: " + probCatch);
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
		System.out.println(prob);
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

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see SpreadingFire#findNeighbors(Grid, int, Reader) Finds neighbors,
	 * which for spreading fire are up, down, left, right
	 */
	public List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader) {
		Map<String, Integer> myMap = myReader.getGlobalChars();
		int numCols = myMap.get("cols"); // returns number of columns
		int numRows = myMap.get("rows");
		int rowNum = index / numCols; // row number of cell
		int colNum = index % numCols; // col number of cell
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaRow = { -1, 0, 0, 1};
		int[] deltaCol = { 0, 1, -1, 0};
		int[] arrDelta = { -numCols, 1, -1, numCols};
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum + deltaRow[i], colNum + deltaCol[i], numRows, numCols)) {
				neighborsList.add(myArr[index + arrDelta[i]]);
			} else {
				// System.out.printf("InsideRow: %d, InsideCol: %d\n", rowNum +
				// deltaRow[i], colNum + deltaCol[i]);
			}
		}
		return neighborsList;
	}

	@Override
	public Shape getCellShape(int index, int width, int height, int rows, int cols) {
		double ySize = (double) height / rows;
		double xSize = (double) width / cols;
		int colNum = index % cols;
		int rowNum = index / cols;
		Rectangle thisRect = new Rectangle(colNum * xSize, rowNum * ySize + 40, xSize, ySize);
		thisRect.setStrokeWidth(4);
		thisRect.setStroke(Color.LIGHTGRAY);
		return thisRect;
	}
}
