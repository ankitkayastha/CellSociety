import java.util.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
public abstract class GameOfLife extends Simulation {
	private final String characteristic = "life";
	protected final int DEAD = 0;
	protected final int ALIVE = 1;
	
	@Override
	public Color getCellColor(int index, Grid myGrid) {
		Cell myCell = myGrid.currentGrid()
		if (myCell.getChars().get(characteristic) == ALIVE)
			return Color.BLUE;
		else
			return Color.CYAN;
	}
	
	@Override
	public void update(Grid myGrid, Reader myReader) {
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell myCurrentStateCell = currentGrid.getCell(i);
			Cell myNextStateCell = newGrid.getCell(i);
			List<Cell> cellNeighbors = findNeighbors(currentGrid, i, myReader);
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

	public abstract List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader);
}
