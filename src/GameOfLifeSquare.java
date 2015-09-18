import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.*;

public class GameOfLifeSquare extends GameOfLife {
	private final String cols = "cols";
	private final String rows = "rows";
	private final String characteristic = "life";
	
	@Override 
	public List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader) {
		Map<String, Integer> myMap = myReader.getGlobalChars();
		int numCols = myMap.get(cols); //returns number of columns
		int numRows = myMap.get(rows);
		int rowNum = index / numCols; //row number of cell
		int colNum = index % numCols; //col number of cell
		Cell myCell = myArr[index]; //returns cell given index
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaX = {-1, 0, 0, 1, 1, -1, -1, 1};
		int[] deltaY = {0, 1, -1, 0, 1, 1, -1, -1};
		int[] arrDelta = {-1, 1, numCols, -numCols, numCols + 1, numCols - 1, -numCols + 1, -numCols - 1};
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum + deltaX[i], colNum + deltaY[i], numRows, numCols))
					neighborsList.add(myArr[index + arrDelta[i]]);
		}
		return neighborsList;
	}	
	
	@Override
	public Shape getCellShape(int index) {
		return new Rectangle(5,5);
	}



}


