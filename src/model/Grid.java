package model;

import view.GuiClass;

public class Grid {

	private Cell[] myGrid;

	public Grid(Reader myReader, GuiClass myGui) {
		myGrid = new Cell[myReader.getSize()];

		// populate the grid, iterate through the cells
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell initCell = new Cell(myReader.getCell(i));
			myGrid[i] = initCell;
		}
	}
	
	public Cell[] getGrid() {
		return myGrid;
	}
	
	public void setCell(Cell myCell, int index) {
		myGrid[index] = myCell;
	}
	public Cell getCell(int i) {
		return myGrid[i];
	}
}
