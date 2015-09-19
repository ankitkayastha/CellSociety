import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class Simulation {
	protected Map<String, Integer> glob;
	
	public Simulation(Map<String, Integer> globalChars) {
		glob = globalChars;
	}
	
	
	public abstract void update(Grid currentGrid, Reader myReader);
	
	public boolean isOutOfBounds(int m, int n, int numRows, int numCols) {
		return (m < 0 || m >= numRows || n < 0 || n >= numCols);
	}
	
	public abstract Color getCellColor(int index, Grid myGrid);
	
	public Shape getCellShape(int index, int width, int height, int rows, int cols) {
		double ySize = (double) height / rows;
		double xSize = (double) width / cols;
		int colNum = index % cols;
		int rowNum = index / cols;
		Rectangle thisRect = new Rectangle(colNum * xSize, rowNum * ySize + 40, xSize, ySize);
		thisRect.setStrokeWidth(4);
		thisRect.setStroke(Color.LIGHTGRAY);
		return thisRect;
	}

	public abstract List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader);

}
