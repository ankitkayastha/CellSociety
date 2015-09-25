package model;

import java.util.ArrayList;
import java.util.List;

import data.Stats;

public class NeighborFactory {
	private int numRows;
	private int numCols;
	private int currentShape;
	private int currentSim;
	private int currentGridType;

	private int SQUARE = 4;
	private int TRIANGLE = 3;
	private int HEXAGON = 6;
	private int GOL = 0;
	private int FIRE = 1;
	private int SEG = 2;
	private int WATOR = 3;

	public NeighborFactory(Stats myStats) {
		currentShape = myStats.getGlobalChars().get("shape");
		currentSim = myStats.getGlobalChars().get("sim");
		numCols = myStats.getGlobalChars().get("cols");
		numRows = myStats.getGlobalChars().get("rows");
		//currentGridType = myStats.getGlobalChars().get("type");
	}

	public List<Cell> getNeighbors(Cell[] myArr, int index) {
		// whole lot of sims and shapes to determine everything

		int rowNum = getRow(index);
		int colNum = getCol(index);
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] rowDelta = getRowDelta(index);
		int[] colDelta = getColDelta(index);
		int[] arrDelta = getIndexDelta(index);
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum+rowDelta[i], colNum + colDelta[i])) {
				neighborsList.add(myArr[arrDelta[i]]);
			}
		}
		//System.out.printf("index: %d, row: %d, col: %d, neighbors: %d\n", index, rowNum, colNum, neighborsList.size());
		return neighborsList;
	}

	private int getRow(int index) {
		if (currentShape == SQUARE) {
			return index / numCols;
		} else if (currentShape == TRIANGLE) {
			return index / numCols;
		} else if (currentShape == HEXAGON) {
			return index / numCols;
		}
		return -1;
	}

	private int getCol(int index) {
		if (currentShape == SQUARE) {
			return index % numCols;
		} else if (currentShape == TRIANGLE) {
			return index % numCols;
		} else if (currentShape == HEXAGON) {
			return index % numCols;
		}
		return -1;
	}

	private int[] getRowDelta(int index) {
		if (currentShape == TRIANGLE) {
			return new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
		} else if (currentShape == HEXAGON) {
			return new int[] { -1, (index % 2) - 1, (index % 2) - 1, 1, (index % 2), (index % 2) };
		} else if (currentShape == SQUARE) {
			return new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
		}
		return null;
	}

	private int[] getColDelta(int index) {
		if (currentShape == TRIANGLE) {
			return new int[] { 0, -1, 1, -1, 1, 0, -1, 1 };
		} else if (currentShape == HEXAGON) {
			return new int[] { 0, -1, 1, 0, -1, 1 };
		} else if (currentShape == SQUARE) {
			return new int[] { 0, -1, 1, -1, 1, 0, -1, 1 };
		}
		return null;
	}

	private int[] getIndexDelta(int index) {
		if (currentShape == TRIANGLE) {
			return new int[] { index - numCols, index - numCols - 1, index - numCols + 1, index - 1, index + 1,
					index + numCols, index + numCols - 1, index + numCols + 1 };
		} else if (currentShape == HEXAGON) {
			return new int[] { index - numCols, ((index % 2) - 1) * numCols - 1, ((index % 2) - 1) * numCols + 1,
					index + numCols, (index % 2) * numCols - 1, (index % 2) * numCols + 1 };
		} else if (currentShape == SQUARE) {
			return new int[] { index - numCols, index - numCols - 1, index - numCols + 1, index - 1, index + 1,
					index + numCols, index + numCols - 1, index + numCols + 1 };
		}
		return null;
	}

	private boolean isOutOfBounds(int row, int col) {
		if (row < 0 || col < 0) {
			return true;
		}
		if (row >= numRows || col >= numCols) {
			return true;
		}
		return false;
	}
}
