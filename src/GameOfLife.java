import java.util.*;

import javafx.scene.paint.Color;
public abstract class GameOfLife extends Simulation {
	private final String characteristic = "life";
	private final int DEAD = 0;
	private final int ALIVE = 1;
	
	
	
	public Color getCellColor(Cell myCell) {
		if (myCell.getChars().get(characteristic) == ALIVE)
			return Color.BLUE;
		else
			return Color.CYAN;
	}
	
	public abstract void update(Grid currentGrid, Grid newGrid, Reader myReader, int index);
	
	public abstract List<Cell> findNeighbors(Grid myGrid, int index, Reader myReader);
}
