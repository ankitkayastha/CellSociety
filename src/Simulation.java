import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Simulation {
	protected Map<String, Integer> glob;
	
	public Simulation(Map<String, Integer> globalChars) {
		glob = globalChars;
		System.out.println(glob.toString());
	}
	
	
	public abstract void update(Grid currentGrid, Reader myReader);
	
	public boolean isOutOfBounds(int m, int n, int numRows, int numCols) {
		return (m < 0 || m >= numRows || n < 0 || n >= numCols);
	}
	
	public abstract Color getCellColor(int index, Grid myGrid);
	
	public abstract Shape getCellShape(int index, int width, int height, int rows, int cols); 

}
