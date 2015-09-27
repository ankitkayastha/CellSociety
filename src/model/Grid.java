package model;
import data.Stats;

public class Grid {

	private Cell[] myGrid;

	public Grid(Stats myStats) {
		myGrid = new Cell[myStats.getSize()];

		// populate the grid, iterate through the cells
		for (int i = 0; i < myStats.getSize(); i++) {
			CellFactory thisCellFactory = new CellFactory(myStats);
			Cell initCell = thisCellFactory.createCell(myStats.getCellData(i));
			myGrid[i] = initCell;
		}
	}
	
	public Cell[] getGrid() {
		return myGrid;
	}
	
	public Cell getCell(int i) {
		return myGrid[i];
	}
	
	public void changeCell(int index, Stats myStats) {
		myGrid[index].change(myStats);
	}
}
