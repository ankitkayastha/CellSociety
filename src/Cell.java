import java.util.*;

import javafx.scene.paint.Color;
public abstract class Cell {
	public Map<String, Integer> myCharacteristicMap;
	// TODO change public
	
	public Cell(Map<String, Integer> map) {
		myCharacteristicMap = map;
	}
	
	public Map<String, Integer> getChars() {
		return myCharacteristicMap;
	}
	
	public abstract Cell[][] update(Cell[][] currentGrid, Reader myReader);
	
	public abstract Color getColor();
	
	public abstract List<Cell> findNeighbors(Cell[][] currentGrid, int numRows, int numCols);
	
	
	
	/*
	 * m is row number, n is col number
	 */
	public boolean isOutOfBounds(int m, int n, int numRows, int numCols) {
		return (m < 0 || m >= numRows || n < 0 || n >= numCols);
	}
}


