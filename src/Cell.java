import java.util.*;

import javafx.scene.paint.Color;
public abstract class Cell {
	protected int myCurrentState;
	protected int myNextState;
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
	
	public int getCurrentState() {
		return myCurrentState;
	}
	public void setCurrentState(int state) {
		myCurrentState = state;
	}
	public int getNextState() {
		return myNextState;
	}
	public void setNextState(int state) {
		myNextState = state;
	}
	public int getXLocation() {
		return xLocation;
	}
	public int getYLocation() {
		return yLocation;
	}
	public abstract void update(Grid currentGrid, Reader myReader);
	//public abstract List<Cell> findNeighbors(Grid myGrid, int m, int n, int rows, int cols);
	
	
	public abstract Color getColor();
	
	public abstract List<Cell> findNeighbors(Grid currentGrid, int numRows, int numCols);
	
	
	
	/*
	 * m is row number, n is col number
	 */
	public boolean isOutOfBounds(int m, int n, int numRows, int numCols) {
		return (m < 0 || m >= numRows || n < 0 || n >= numCols);
	}
}


