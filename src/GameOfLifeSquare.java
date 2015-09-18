import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.*;

public class GameOfLifeSquare extends GameOfLife {
	private final String cols = "cols";
	private final String rows = "rows";
	private final String characteristic = "life";
	private final int DEAD = 0;
	private final int ALIVE = 1;
	@Override
	
	public List<Cell> findNeighbors(Grid myGrid, int index, Reader myReader) {
		Map<String, Integer> myMap = myReader.getGlobalChars();
		int numCols = myMap.get(cols); //returns number of columns
		int numRows = myMap.get(rows);
		int rowNum = index / numCols; //row number of cell
		int colNum = index % numCols; //col number of cell
		Cell myCell = myGrid.getCell(index); //returns cell given index
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaX = {-1, 0, 0, 1, 1, -1, -1, 1};
		int[] deltaY = {0, 1, -1, 0, 1, 1, -1, -1};
		int[] arrDelta = {-1, 1, numCols, -numCols, numCols + 1, numCols - 1, -numCols + 1, -numCols - 1};
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum + deltaX[i], colNum + deltaY[i], numRows, numCols))
					neighborsList.add(myGrid.getCell(index + arrDelta[i]));
		}
		return neighborsList;
	}	
	
	

	public void update(Grid currentGrid, Grid newGrid, Reader myReader, int index) {
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell myCurrentStateCell = currentGrid.getCell(index);
			Cell myNextStateCell = newGrid.getCell(index);
			List<Cell> cellNeighbors = findNeighbors(currentGrid, index, myReader);
			int numLiveNeighbors = 0;
			for (Cell cell: cellNeighbors) {
				if (cell.getChars().get(characteristic) == ALIVE)
					numLiveNeighbors++;
			}
				if (myCurrentStateCell.getChars().get(characteristic) == ALIVE) {
					if (numLiveNeighbors == 2 || numLiveNeighbors == 3) {
						myNextStateCell.getChars().put(characteristic, ALIVE);
					}
					else {
						myNextStateCell.getChars().put(characteristic, DEAD);
					}
				}
				else {
					if (numLiveNeighbors == 3) {
						myNextStateCell.getChars().put(characteristic, ALIVE);
					}
					else {
						myNextStateCell.getChars().put(characteristic, DEAD);
					}
				}
			}
		
		currentGrid = copyGrid(newGrid, myReader);
	}

	/*
	 * copies from new grid to current grid
	 */
	public Grid copyGrid(Grid newGrid, Reader myReader) {
		Grid currentGrid;
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell myCell = newGrid.getCell(i);
			currentGrid.setCell(i);
		}
		
		
		return currentGrid;
	}

	@Override
	public Shape getCellShape(int index) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Color getCellColor(int index) {
		// TODO Auto-generated method stub
		return null;
	}
}


