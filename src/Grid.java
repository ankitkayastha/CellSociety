import java.util.*;

public class Grid {


	private Cell[][] myGrid;
	private Cell[][] oldGrid;
	
	private Reader myReader;

	public Grid(Reader myReader) {
		this.myReader = myReader;
		myGrid = new Cell[myReader.getRows()][myReader.getCols()];
		List<Map<String, Integer>> myData = myReader.getData();

		// populate the grid, iterate through the cells
		for (int i = 0; i < myReader.getRows(); i++) {
			for (int j = 0; j < myReader.getCols(); j++) {
				Cell initCell = new Cell(myReader.getCell(i, j));
				myGrid[i][j] = initCell;
			}
		}
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
	