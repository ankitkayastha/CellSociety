import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.*;

public class GameOfLifeSquare extends GameOfLife {
	public GameOfLifeSquare(Map<String, Integer> globalChars) {
		super(globalChars);
		// TODO Auto-generated constructor stub
	}

	private final String cols = "cols";
	private final String rows = "rows";
	private final String characteristic = "life";

	@Override
	public List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader) {
		Map<String, Integer> myMap = myReader.getGlobalChars();
		int numCols = myMap.get(cols); // returns number of columns
		int numRows = myMap.get(rows);
		int rowNum = index / numCols; // row number of cell
		int colNum = index % numCols; // col number of cell
		//System.out.printf("Row: %d, Col: %d\n", rowNum, colNum);
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaRow = { -1, 0, 0, 1, 1, -1, -1, 1 };
		int[] deltaCol = { 0, 1, -1, 0, 1, 1, -1, -1 };
		int[] arrDelta = { -numCols, 1, -1, numCols, numCols + 1, -numCols + 1, -numCols - 1, numCols - 1 };
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum + deltaRow[i], colNum + deltaCol[i], numRows, numCols)) {
				neighborsList.add(myArr[index + arrDelta[i]]);
			}
			else {
				//System.out.printf("InsideRow: %d, InsideCol: %d\n", rowNum + deltaRow[i], colNum + deltaCol[i]);
			}
		}
		return neighborsList;
	}

	@Override
	public Shape getCellShape(int index, int width, int height, int rows, int cols) {
		double ySize = (double) height / rows;
		double xSize = (double) width / cols;
		int colNum = index % cols;
		int rowNum = index / cols;
		Rectangle thisRect = new Rectangle(colNum*xSize, rowNum*ySize+40, xSize, ySize);
		thisRect.setStrokeWidth(4);
		thisRect.setStroke(Color.LIGHTGRAY);
		return thisRect;
	}

}
