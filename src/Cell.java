import java.util.*;
public abstract class Cell {
	private int myCurrentState;
	private int myNextState;
	private int xLocation;
	private int yLocation;
	public Cell(int currentState, int nextState) {
		myCurrentState = currentState;
		myNextState = nextState;
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
	public abstract void getNextState(Grid myGrid, Cell myCell);
	//public abstract List<Cell> findNeighbors(Grid myGrid, int m, int n, int rows, int cols);
}
