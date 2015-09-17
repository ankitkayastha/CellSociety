import java.util.*;

import javafx.scene.paint.Color;
public class CellGameOfLife extends Cell {
	private int DEAD = 0; //light blue
	private int ALIVE = 1; //blue
	private final String characteristic = "Life";
	
	public CellGameOfLife(Map<String, Integer> map) {
		super(map);
	}
	
	
	public Color getColor() {
		if (myCharacteristicMap.get(characteristic) == ALIVE)
			return Color.BLUE;
		else
			return Color.CYAN;
	}
	
	@Override
	public List<Cell> findNeighbors(Grid currentGrid, int numRows, int numCols) {
		int cellRow = myCharacteristicMap.get(row);
		int cellCol = myCharacteristicMap.get(col);
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaX = {-1, 0, 0, 1, -1, -1, 1, 1};
		int[] deltaY = {0, 1, -1, 0, 1, -1, -1, 1};
		for (int i = 0; i < deltaX.length; i++) {
			if (!isOutOfBounds(cellRow + deltaX[i], cellCol + deltaY[i], numRows, numCols))
				neighborsList.add(currentGrid.getCell(cellRow + deltaX[i], cellCol + deltaY[i]));
		}
		
		return neighborsList;
	}	
	
	public void update(Grid currentGrid, Grid newGrid, Reader myReader) {
		for (int i = 0; i < myReader.getRows(); i++) {
			for (int j = 0; j < myReader.getCols();j++) {
				Cell myCell = currentGrid.getCell(i, j);
				List<Cell> cellNeighbors = myCell.findNeighbors(currentGrid, myReader.getRows(), myReader.getCols());
				int numLiveNeighbors = 0;
				for (Cell cell: cellNeighbors) {
					if (myCharacteristicMap.get(characteristic) == ALIVE)
						numLiveNeighbors++;
				}
				if (myCharacteristicMap.get(characteristic) == ALIVE) {
					if (numLiveNeighbors == 2 || numLiveNeighbors == 3) {
						myCell.setNextState(ALIVE);
						myCharacteristicMap.put(characteristic, ALIVE);
						newGrid.getCell(i, j) = new Cell(myCharacteristicMap);
					}
					else {
						myCell.setNextState(DEAD);
						newGrid.getCell(i, j) = new Cell(myCharacteristicMap);
						myCharacteristicMap.put(characteristic, DEAD);
					}
				}
				else {
					if (numLiveNeighbors == 3) {
						myCell.setNextState(ALIVE);
						newGrid.getCell(i, j) = new Cell(myCharacteristicMap);
						myCharacteristicMap.put(characteristic, ALIVE);
					}
					else {
						myCell.setNextState(DEAD);
						newGrid.getCell(i, j) = new Cell(myCharacteristicMap);
						myCharacteristicMap.put(characteristic, DEAD);
					}
				}
			}
		}
	}
}
