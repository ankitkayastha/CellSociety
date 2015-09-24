package model;

public class Grid {

	private Cell[] myGrid;

	public Grid(Stats myStats) {
		myGrid = new Cell[myStats.getSize()];

		// populate the grid, iterate through the cells
		for (int i = 0; i < myStats.getSize(); i++) {
			Cell initCell = new Cell(myStats.getCellData(i));
			myGrid[i] = initCell;
		}
	}
	
	public Cell[] getGrid() {
		return myGrid;
	}
	
	public Cell getCell(int i) {
		return myGrid[i];
	}
}
