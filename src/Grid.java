import java.util.*;

public class Grid {

	private Cell[][] myGrid;

	private Reader myReader;
	private GuiClass myGui;

	public Grid(Reader myReader, GuiClass myGui) {
		this.myGui = myGui;
		this.myReader = myReader;
		myGrid = new Cell[myReader.getRows()][myReader.getCols()];
		//List<Map<String, Integer>> myData = myReader.getData();

		// populate the grid, iterate through the cells
		for (int i = 0; i < myReader.getRows(); i++) {
			for (int j = 0; j < myReader.getCols(); j++) {
				Cell initCell = new CellGameOfLife(myReader.getCell(i, j));
				System.out.println(initCell.getChars().get("life"));
				myGrid[i][j] = initCell;
			}
		}
	}

	public void step() {
		Cell[][] newGrid = new Cell[myReader.getRows()][myReader.getCols()];
		for (int row = 0; row < myReader.getRows(); row++) {
			for (int col = 0; col < myReader.getCols(); col++) {
				//System.out.println(row + " "+col);
				Map<String, Integer> cellChars = myGrid[row][col].getChars();
				Cell tempCell = new CellGameOfLife(cellChars);
				System.out.printf("r%d c%d s%d\n", row, col, cellChars.get("life"));
				tempCell.update(this, myReader);
				//myGrid[row][col] = tempCell;
			}
		}
		myGui.display(this, myReader);
	}

	
	public Cell getCurrentCell(int row, int col) {
		return myGrid[row][col];
	}
	
	
	
	public Cell[][] getCurrentGrid() {
		return myGrid;
	}
	
	public void setNewCell(int x, int y, Cell myCell) {
		myGrid[x][y] = myCell;
	}
}
