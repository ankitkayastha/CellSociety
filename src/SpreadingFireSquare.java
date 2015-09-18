import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.*;
public class SpreadingFireSquare extends SpreadingFire {
	private final String rows = "rows";
	private final String cols = "cols";
	
	@Override
	public Shape getCellShape(int index) {
		return new Rectangle(5, 5);
	}
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see SpreadingFire#findNeighbors(Grid, int, Reader)
	 * Finds neighbors, which for spreading fire are up, down, left, right
	 */
	public List<Cell> findNeighbors(Grid currentGrid, int index, Reader myReader) {
		Map<String, Integer> myMap = myReader.getGlobalChars();
		int numCols = myMap.get(cols); //returns number of columns
		int numRows = myMap.get(rows);
		int rowNum = index / numCols; //row number of cell
		int colNum = index % numCols; //col number of cell
		Cell myCell = currentGrid.getCell(index); //returns cell given index
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaX = {-1, 0, 0, 1};
		int[] deltaY = {0, 1, -1, 0};
		int[] arrDelta = {-1, 1, numCols, -numCols}; //numCols + 1, numCols - 1, -numCols + 1, -numCols - 1};
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum + deltaX[i], colNum + deltaY[i], numRows, numCols))
					neighborsList.add(currentGrid.getCell(index + arrDelta[i]));
		}
		return neighborsList;
	}
}
