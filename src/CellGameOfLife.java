import java.util.*;

import javafx.scene.paint.Color;
public class CellGameOfLife extends Cell {
	private int DEAD = 0; //light blue
	private int ALIVE = 1; //blue
	private final String characteristic = "life";
	
	public CellGameOfLife(Map<String, Integer> map) {
		super(map);
		//System.out.println(map.toString());
		//System.out.println(map.keySet());
	}
	
	
	public Color getColor() {
		if (myCharacteristicMap.get(characteristic) == ALIVE)
			return Color.BLUE;
		else
			return Color.CYAN;
	}
	
	@Override
	public List<Cell> findNeighbors(Cell[][] oldGrid, int numRows, int numCols) {
		int cellRow = myCharacteristicMap.get("row");
		int cellCol = myCharacteristicMap.get("col");
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaX = {-1, 0, 0, 1, -1, -1, 1, 1};
		int[] deltaY = {0, 1, -1, 0, 1, -1, -1, 1};
		for (int i = 0; i < deltaX.length; i++) {
			if (!isOutOfBounds(cellRow + deltaX[i], cellCol + deltaY[i], numRows, numCols))
				neighborsList.add(oldGrid[cellRow + deltaX[i]][ cellCol + deltaY[i]]);
		}
		//System.out.println("neigh: "+neighborsList.size());
		return neighborsList;
	}	
	
	public Cell[][] update(Cell[][] oldGrid, Reader myReader) {
		System.out.println("Update");
		for (int i = 0; i < myReader.getRows(); i++) {
			for (int j = 0; j < myReader.getCols();j++) {
				Cell myCell = oldGrid[i][j];
				List<Cell> cellNeighbors = myCell.findNeighbors(oldGrid, myReader.getRows(), myReader.getCols());
				int numLiveNeighbors = 0;
				for (Cell cell: cellNeighbors) {
					if (cell.myCharacteristicMap.get(characteristic) == ALIVE)
						numLiveNeighbors++;
				}
				if (myCharacteristicMap.get(characteristic) == ALIVE) {
					if (numLiveNeighbors == 2 || numLiveNeighbors == 3) {
						myCharacteristicMap.put(characteristic, ALIVE);
					}
					else {
						myCharacteristicMap.put(characteristic, DEAD);
					}
				}
				else {
					if (numLiveNeighbors == 3) {
						myCharacteristicMap.put(characteristic, ALIVE);
					}
					else {
						myCharacteristicMap.put(characteristic, DEAD);
					}
				}
			}
		}
		return oldGrid;
	}
}
