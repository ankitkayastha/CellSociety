import java.util.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GameOfLife extends Simulation {
	public GameOfLife(Map<String, Integer> globalChars) {
		super(globalChars);
	}

	private final String characteristic = "life";
	protected final int DEAD = 0;
	protected final int ALIVE = 1;

	@Override
	public Color getCellColor(int index, Grid myGrid) {
		Cell myCell = myGrid.getCell(index);
		if (myCell.getChars().get(characteristic) == ALIVE)
			return Color.LAWNGREEN;
		else
			return Color.BLACK;
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

			int numLiveNeighbors = 0;
			for (Cell cell : cellNeighbors) {
				if (cell.getChars().get(characteristic) == ALIVE) {
					numLiveNeighbors++;
				}
			}
			if (oldCell.getChars().get(characteristic) == ALIVE) {
				if (numLiveNeighbors == 2 || numLiveNeighbors == 3) {
					myCell.getChars().put(characteristic, ALIVE);
				} else {
					myCell.getChars().put(characteristic, DEAD);
				}
			} else {
				if (numLiveNeighbors == 3) {
					myCell.getChars().put(characteristic, ALIVE);
				} else {
					myCell.getChars().put(characteristic, DEAD);
				}
			}
		}
	}

	@Override
	public List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader) {
		Map<String, Integer> myMap = myReader.getGlobalChars();
		int numCols = myMap.get("cols"); // returns number of columns
		int numRows = myMap.get("rows");
		int rowNum = index / numCols; // row number of cell
		int colNum = index % numCols; // col number of cell
		//System.out.printf("Row: %d, Col: %d\n", rowNum, colNum);
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaRow = { -1, 0, 0, 1, 1, -1, -1, 1 };
		int[] deltaCol = { 0, 1, -1, 0, 1, 1, -1, -1 };
		int[] arrDelta = { -numCols, 1, -1, numCols, numCols + 1, -numCols + 1, -numCols - 1, numCols - 1 };
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum + deltaRow[i], colNum + deltaCol[i], numRows, numCols)) {
				neighborsList.add(myArr[index + arrDelta[i]]);
			}
			else {
				//System.out.printf("InsideRow: %d, InsideCol: %d\n", rowNum + deltaRow[i], colNum + deltaCol[i]);
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
		Rectangle thisRect = new Rectangle(colNum*xSize, rowNum*ySize+40, xSize, ySize);
		thisRect.setStrokeWidth(4);
		thisRect.setStroke(Color.LIGHTGRAY);
		return thisRect;
	}}
