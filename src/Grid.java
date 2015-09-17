import java.util.*;

public class Grid {

	private Cell[][] myGrid;
	private Cell[][] oldGrid;
	
	private Reader myReader;
	private GuiClass myGui;

	public Grid(Reader myReader) {
		this.myReader = myReader;
		myGrid = new Cell[myReader.getRows()][myReader.getCols()];
		List<Map<String, Integer>> myData = myReader.getData();

		// populate the grid, iterate through the cells
		for (int i = 0; i < myReader.getRows(); i++) {
			for (int j = 0; j < myReader.getCols(); j++) {
				Cell initCell = new CellGameOfLife(myReader.getCell(i, j));
				myGrid[i][j] = initCell;
			}
		}
	}
	
	public void step() {
		oldGrid = myGrid;
		for (int row=0; row< myReader.getRows(); row++) {
			for (int col=0; col<myReader.getCols(); col++) {
				Map<String, Integer> cellChars= oldGrid[row][col].getChars();
				Cell tempCell = new CellGameOfLife(cellChars);
				tempCell.update(oldGrid, myGrid);
				myGrid[row][col] = tempCell;
			}
		}
		myGui.display(myGrid);
	}
}
