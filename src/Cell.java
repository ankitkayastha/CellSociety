import java.util.*;

import javafx.scene.paint.Color;
public abstract class Cell {
	private int xLocation;
	private int yLocation;
	protected Color cellColor;
	protected final String row = "row";
	protected final String col = "col";
	public Map<String, Integer> myCharacteristicMap;
	// TODO change public
	
	public Cell(Map<String, Integer> map) {
		myCharacteristicMap = map;
	}
	
	public Map<String, Integer> getChars() {
		return myCharacteristicMap;
	}
	

	public int getXLocation() {
		return xLocation;
	}
	public int getYLocation() {
		return yLocation;
	}
	public abstract Cell[][] update(Cell[][] currentGrid, Reader myReader);
	//public abstract List<Cell> findNeighbors(Grid myGrid, int m, int n, int rows, int cols);
	
	
	public abstract Color getColor();
	
	public abstract List<Cell> findNeighbors(Cell[][] currentGrid, int numRows, int numCols);
	
	
	
	/*
	 * m is row number, n is col number
	 */
	public boolean isOutOfBounds(int m, int n, int numRows, int numCols) {
		return (m < 0 || m >= numRows || n < 0 || n >= numCols);
	}
}


