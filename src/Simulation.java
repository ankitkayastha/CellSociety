import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Simulation {
	
	
	public abstract void update(Grid currentGrid, Grid newGrid, Reader myReader, int index);
	
	public boolean isOutOfBounds(int m, int n, int numRows, int numCols) {
		return (m < 0 || m >= numRows || n < 0 || n >= numCols);
	}
	
	public abstract Color getCellColor(Cell myCell);
	
	/*
	 * copies from new grid to current grid
	 */
	public Grid copyGrid(Grid newGrid, Reader myReader) {
		Grid currentGrid = null;
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell myCell = newGrid.getCell(i);
			currentGrid.setCell(myCell, i);
		}
		
		
		return currentGrid;
	}
}
