import java.util.*;

import javafx.scene.paint.Color;

public class GameOfLife extends Simulation {
	public GameOfLife(Map<String, Integer> globalChars) {
		super(globalChars);
	}

	private final String characteristic = "life";
	protected final int DEAD = 0;
	protected final int ALIVE = 1;

	@Override
	public void update(Grid myGrid, Reader myReader) {
		Cell[] oldGrid = super.copyGrid(myGrid, myReader);
		Cell[] myGridGrid = myGrid.getGrid();

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
	public Color getCellColor(int index, Grid myGrid) {
		Cell myCell = myGrid.getCell(index);
		if (myCell.getChars().get(characteristic) == ALIVE)
			return Color.LAWNGREEN;
		else
			return Color.BLACK;
	}

	@Override
	public List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader) {
		int numCols = myReader.getGlobalChars().get("cols"); 
		int numRows = myReader.getGlobalChars().get("rows");
		int rowNum = index / numCols; // row number of cell
		int colNum = index % numCols; // col number of cell
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaRow = { -1, 0, 0, 1, 1, -1, -1, 1 };
		int[] deltaCol = { 0, 1, -1, 0, 1, 1, -1, -1 };
		int[] arrDelta = { -numCols, 1, -1, numCols, numCols + 1, -numCols + 1, -numCols - 1, numCols - 1 };
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum + deltaRow[i], colNum + deltaCol[i], numRows, numCols)) {
				neighborsList.add(myArr[index + arrDelta[i]]);
			}
		}
		return neighborsList;
	}
}
