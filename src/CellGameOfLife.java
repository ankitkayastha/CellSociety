import java.util.*;

import javafx.scene.paint.Color;
public class CellGameOfLife extends Cell {
	private int DEAD = 0; //light blue
	private int ALIVE = 1; //blue
	private final String characteristic = "life";
	
	public CellGameOfLife(Map<String, Integer> map) {
		super(map);
		System.out.println(map.toString());
		System.out.println(map.keySet());
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
				neighborsList.add(currentGrid.getOldCell(cellRow + deltaX[i], cellCol + deltaY[i]));
		}
		
		return neighborsList;
	}	
	
	public void update(Grid myGrid, Reader myReader) {
		Cell[][] currentGrid = myGrid.getOldGrid();
		Cell[][] newGrid = myGrid.getNewGrid();
		for (int i = 0; i < myReader.getRows(); i++) {
			for (int j = 0; j < myReader.getCols();j++) {
				Cell myCell = myGrid.getOldCell(i, j);
				List<Cell> cellNeighbors = myCell.findNeighbors(myGrid, myReader.getRows(), myReader.getCols());
				int numLiveNeighbors = 0;
				for (Cell cell: cellNeighbors) {
					if (myCharacteristicMap.get(characteristic) == ALIVE)
						numLiveNeighbors++;
				}
				if (myCharacteristicMap.get(characteristic) == ALIVE) {
					if (numLiveNeighbors == 2 || numLiveNeighbors == 3) {
						myCell.setNextState(ALIVE);
						myCharacteristicMap.put(characteristic, ALIVE);
						myGrid.setNewCell(i, j, new CellGameOfLife(myCharacteristicMap));
					}
					else {
						myCell.setNextState(DEAD);
						myGrid.setNewCell(i, j, new CellGameOfLife(myCharacteristicMap));
						myCharacteristicMap.put(characteristic, DEAD);
					}
				}
				else {
					if (numLiveNeighbors == 3) {
						myCell.setNextState(ALIVE);
						myGrid.setNewCell(i, j, new CellGameOfLife(myCharacteristicMap));
						myCharacteristicMap.put(characteristic, ALIVE);
					}
					else {
						myCell.setNextState(DEAD);
						myGrid.setNewCell(i, j, new CellGameOfLife(myCharacteristicMap));
						myCharacteristicMap.put(characteristic, DEAD);
					}
				}
			}
		}
	}
}
