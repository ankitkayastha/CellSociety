package model;

import java.util.ArrayList;
import java.util.List;

import data.Stats;

public class NeighborFactory {
	private Stats myStats;
	
	private int numRows;
	private int numCols;
	private int currentShape;
	private int currentSim;
	private int currentType;

	private int SQUARE = 4;
	private int TRIANGLE = 3;
	private int HEXAGON = 6;
	private int GOL = 0;
	private int FIRE = 1;
	private int SEG = 2;
	private int WATOR = 3;
	private int SUGAR = 4;

	private int NOWRAP = 0;
	private int WRAP = 1;

	public NeighborFactory(Stats myStats) {
		this.myStats = myStats;
		currentShape = myStats.getGlobalChars().get("shape");
		currentSim = myStats.getGlobalChars().get("sim");
		numCols = myStats.getGlobalChars().get("cols");
		numRows = myStats.getGlobalChars().get("rows");
		currentType = myStats.getGlobalChars().get("type");
	}

	public List<Cell> getNeighbors(Cell[] myArr, int index) {
		currentType = myStats.getGlobalChars().get("type");
		int rowNum = getRow(index);
		int colNum = getCol(index);
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] rowDelta = getRowDelta(index);
		int[] colDelta = getColDelta(index);
		int[] arrDelta = getIndexDelta(index);
		for (int i = 0; i < arrDelta.length; i++) {
			if (currentType == NOWRAP) {
				if (!isOutOfBounds(rowNum + rowDelta[i], colNum + colDelta[i])) {
					neighborsList.add(myArr[arrDelta[i]]);
				}
			} else if (currentType == WRAP) {
				int inBoundsDelta = makeInBounds(arrDelta[i], rowNum + rowDelta[i], colNum + colDelta[i]);
				neighborsList.add(myArr[inBoundsDelta]);
			}
		}
		return neighborsList;
	}

	private int getRow(int index) {
		return index / numCols;
	}

	private int getCol(int index) {
		return index % numCols;
	}

	private int[] getRowDelta(int index) {
		if ((currentShape == TRIANGLE || currentShape == SQUARE) && (currentSim == FIRE || currentSim == WATOR || currentSim == SUGAR)) {
			return new int[] { -1, 0, 0, 1 };
		} else if ((currentShape == TRIANGLE || currentShape == SQUARE) && (currentSim == GOL || currentSim == SEG)) {
			return new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
		}  else if (currentShape == HEXAGON) {
			return new int[] { -1, (index % 2) - 1, (index % 2) - 1, 1, (index % 2), (index % 2) };
		}
		return null;
	}

	private int[] getColDelta(int index) {
		if ((currentShape == TRIANGLE || currentShape == SQUARE) && (currentSim == FIRE || currentSim == WATOR || currentSim == SUGAR)) {
			return new int[] { 0, -1, 1, 0 };
		} else if ((currentShape == TRIANGLE || currentShape == SQUARE) && (currentSim == GOL || currentSim == SEG)) {
			return new int[] { 0, -1, 1, -1, 1, 0, -1, 1 };
		} else if (currentShape == HEXAGON) {
			return new int[] { 0, -1, 1, 0, -1, 1 };
		}
		return null;
	}

	private int[] getIndexDelta(int index) {
		if ((currentShape == TRIANGLE || currentShape == SQUARE) && (currentSim == FIRE || currentSim == WATOR || currentSim == SUGAR)) {
			return new int[] { index - numCols, index - 1, index + 1, index + numCols };
		} else if ((currentShape == TRIANGLE || currentShape == SQUARE) && (currentSim == GOL || currentSim == SEG)) {
			return new int[] { index - numCols, index - numCols - 1, index - numCols + 1, index - 1, index + 1,
					index + numCols, index + numCols - 1, index + numCols + 1 };
		} else if (currentShape == HEXAGON) {
			return new int[] { index - numCols, index + ((index % 2) - 1) * numCols - 1, index + ((index % 2) - 1) * numCols + 1,
					index + numCols, index + (index % 2) * numCols - 1, index + (index % 2) * numCols + 1 };
		}
		return null;
	}

	private boolean isOutOfBounds(int row, int col) {
		return row < 0 || col < 0 || row >= numRows || col >= numCols;
	}

	private int makeInBounds(int index, int row, int col) {
		if (col < 0) {
			index = index + numCols;
		} else if (col >= numCols) {
			index = index - numCols;
		}
		if (row < 0) {
			index = index + numCols * numRows;
		} else if (row >= numRows) {
			index = index - numCols * numRows;
		}
		return index;
	}
}
