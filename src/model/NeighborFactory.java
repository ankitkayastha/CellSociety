package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulation.Simulation;

public class NeighborFactory {
	private List<Cell> neighbors;
	private Map<Integer, Integer[]> deltaRowMap;
	private Map<Integer, Integer[]> deltaColMap;
	private Map<Integer, Integer[]> arrDeltaMap;
	private int numRows;
	private int numCols;
	private int currentShape;
	private int currentSim;
	
		
	public NeighborFactory(Stats myStats, int sim, int shape) {
		currentShape = shape;
		currentSim = sim;
	}
	
	public List<Cell> getNeighbors(Cell[] myArr, int index) {
		// whole lot of sims and shapes to determine everything
		if (sim==) {
			
		} else if (sim==) {
			
		}
		
		if (shape==) {
			
		}
		
		
		
		
		
		
		
		int rowNum = index / numCols; // row number of cell
		int colNum = index % numCols; // col number of cell
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaRow = getDeltaRow(currentSim, currentShape);
		int[] deltaCol = getDeltaCol(currentSim, currentShape);
		int[] arrDelta = getArrDelta(currentSim, currentShape);
		//int[] arrDelta = { -numCols, 1, -1, numCols, numCols + 1, -numCols + 1, -numCols - 1, numCols - 1 };
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(index+arrDelta[i])) {
				neighborsList.add(myArr[index + arrDelta[i]]);
			}
		}
		return neighborsList;
	}
	
	private int[] getDeltaRow(int sim, int shape) {
		if (sim != xxxxx && shape==) {
			
		}
	}
	
	private int[] getDeltaCol(int sim, int shape) {
		if (sim != xxxxx && shape==) {
			
		}
	}
	
	private int[] getArrDelta(int sim, int shape) {
		if (sim != xxxxx && shape==) {
			
		}
	}
	
	private boolean isOutOfBounds(int index) {
		if (sim != xxxx && shape==) {
			
		}
	}
}
