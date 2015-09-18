import java.util.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
public abstract class GameOfLife extends Simulation {
	private final String characteristic = "life";
	protected final int DEAD = 0;
	protected final int ALIVE = 1;
	
	@Override
	public Color getCellColor(Cell myCell) {
		if (myCell.getChars().get(characteristic) == ALIVE)
			return Color.BLUE;
		else
			return Color.CYAN;
	}
	
	@Override
	public void update(Grid currentGrid, Grid newGrid, Reader myReader, int index) {
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell myCurrentStateCell = currentGrid.getCell(index);
			Cell myNextStateCell = newGrid.getCell(index);
			List<Cell> cellNeighbors = findNeighbors(currentGrid, index, myReader);
			int numLiveNeighbors = 0;
			for (Cell cell: cellNeighbors) {
				if (cell.getChars().get(characteristic) == ALIVE)
					numLiveNeighbors++;
			}
				if (myCurrentStateCell.getChars().get(characteristic) == ALIVE) {
					if (numLiveNeighbors == 2 || numLiveNeighbors == 3) {
						myNextStateCell.getChars().put(characteristic, ALIVE);
					}
					else {
						myNextStateCell.getChars().put(characteristic, DEAD);
					}
				}
				else {
					if (numLiveNeighbors == 3) {
						myNextStateCell.getChars().put(characteristic, ALIVE);
					}
					else {
						myNextStateCell.getChars().put(characteristic, DEAD);
					}
				}
			}
		
		currentGrid = copyGrid(newGrid, myReader);
	}

	public abstract Shape getCellShape(int index);
	public abstract List<Cell> findNeighbors(Grid myGrid, int index, Reader myReader);
}
