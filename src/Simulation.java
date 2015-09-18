import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Simulation {
	
	
	public abstract void update(Grid currentGrid, Reader myReader);
	
	public boolean isOutOfBounds(int m, int n, int numRows, int numCols) {
		return (m < 0 || m >= numRows || n < 0 || n >= numCols);
	}
	
	public abstract Color getCellColor(Cell myCell);
	
}
