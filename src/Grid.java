import java.util.*;
public class Grid {
	
	//private Cell[][] myGrid;
	
	public Grid(Reader myReader) {
		myGrid = new Cell[myReader.getRows()][myReader.getCols()];
		List<Map<String, Integer>> myData = myReader.getData();
		
		//populate the grid, interate through the cells
		for (int i = 0; i < myReader.getRows(); i++) {
			for (int j = 0; j < myReader.getCols(); j++) {
				Cell myCell = myGrid[i][j];
				//myCell.setCurrentState(state);
			}
		}
	}
	
	/*
	 * returns cell at a specific location
	 */
	public Cell cellAtLocation(int m, int n) {
		return myGrid[m][n];
	}
	
	