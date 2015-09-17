import java.util.*;
public class Grid {
	
	private Cell[][] myGrid;
	
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
	
	public List<Cell> findNeighbors(int m, int n, int numRows, int numCols) {
		List<Cell> neighborsList = new ArrayList<Cell>();
	//	Square mySquare = new Square(0, 1);
		//neighborsList.add(mySquare);
		int[] deltaX = {-1, 0, 0, 1};
		int[] deltaY = {0, 1, -1, 0};
		for (int i = 0; i < deltaX.length; i++) {
			if (!isOutOfBounds(m + deltaX[i], n + deltaY[i], numRows, numCols))
			 neighborsList.add(myGrid[m + deltaX[i]][n + deltaY[i]]);
		}
		
		return neighborsList;
	}
	
	/*
	 * m is row number, n is col number
	 */
	public boolean isOutOfBounds(int m, int n, int numRows, int numCols) {
		return (m < 0 || m >= numRows || n < 0 || n >= numCols);
	}
}
