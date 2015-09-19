import java.util.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
public class Segregation extends Simulation {
	



	public Segregation(Map<String, Integer> globalChars) {
		super(globalChars);
	}

	@Override
	public void update(Grid currentGrid, Reader myReader) {
		
	}

	@Override
	public Color getCellColor(int index, Grid myGrid) {
		return null;
	}

	@Override
	public Shape getCellShape(int index, int width, int height, int rows, int cols) {
		return null;
	}

	@Override
	public List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader) {
		return null;
	}
	
	public boolean isSatisfied() {
		boolean myBool = false;
		
		
		return myBool;
	}

}
