import java.util.*;

public class Grid {

	private Cell[][] myGrid;
	private Cell[][] oldGrid;

	private Reader myReader;
	private GuiClass myGui;

	public Grid(Reader myReader, GuiClass myGui) {
		this.myGui = myGui;
		this.myReader = myReader;
		myGrid = new Cell[myReader.getRows()][myReader.getCols()];
		oldGrid = new Cell[myReader.getRows()][myReader.getCols()];
		//List<Map<String, Integer>> myData = myReader.getData();

		// populate the grid, iterate through the cells
		for (int i = 0; i < myReader.getRows(); i++) {
			for (int j = 0; j < myReader.getCols(); j++) {
				Cell initCell = new CellGameOfLife(myReader.getCell(i, j));
				System.out.println("myinit: "+i+" "+j+ " "+initCell.getChars().get("life"));
				myGrid[i][j] = initCell;
				Cell initOldCell = new CellGameOfLife(myReader.getCell(i, j));
				System.out.println("olinit: "+i+" "+j+ " "+initOldCell.getChars().get("life"));
				oldGrid[i][j] = initOldCell;
			}
		}
	}

	public void step() {
		// myGrid -> oldGrid
		for (int row = 0; row < myReader.getRows(); row++) {
			for (int col = 0; col < myReader.getCols(); col++) {
				Map<String, Integer> tempChars = myGrid[row][col].getChars();
				Cell tempCell = new CellGameOfLife(tempChars);
				oldGrid[row][col] = tempCell;
			}
		}
		
		// update myGrid using oldGrid
		for (int row = 0; row < myReader.getRows(); row++) {
			for (int col = 0; col < myReader.getCols(); col++) {
				//System.out.println(row + " "+col);
				Map<String, Integer> cellChars = oldGrid[row][col].getChars();
				Cell tempCell = new CellGameOfLife(cellChars);
				System.out.printf("Old: r%d c%d s%d\n", row, col, cellChars.get("life"));
				System.out.printf("Ol : r%d c%d s%d\n", cellChars.get("row"), cellChars.get("col"), cellChars.get("life"));

				myGrid = tempCell.update(oldGrid, myReader);
				//myGrid = oldGrid;
				
				Map<String, Integer> cellChars2 = myGrid[row][col].getChars();
				System.out.printf("New: r%d c%d s%d\n", row, col, cellChars2.get("life"));
				System.out.printf("Ne : r%d c%d s%d\n", cellChars2.get("row"), cellChars2.get("col"), cellChars2.get("life"));
				System.out.println();
				System.out.println();
			}
		}
		myGui.display(this, myReader);
	}
	
	public Cell getCell(int row, int col) {
		return myGrid[row][col];
	}
}
