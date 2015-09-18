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
	public void update(Grid myGrid, Reader myReader) {
		Cell[] oldGrid = new Cell[myReader.getSize()];
		Cell[] myGridGrid = myGrid.getGrid();
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell currentCell = myGrid.getCell(i);
			Map<String, Integer> myMap = currentCell.getChars();
			Map<String, Integer> oldMap = new HashMap<String, Integer>();
			for (String s : myMap.keySet()) {
				// maybe make string/integer primitive
				oldMap.put(s, myMap.get(s));
			}
			Cell oldCell = new Cell(oldMap);
			oldGrid[i] = oldCell;

		}

		for (int i = 0; i < myReader.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];
			List<Cell> cellNeighbors = findNeighbors(oldGrid, i, myReader);
			int numLiveNeighbors = 0;
			for (Cell cell : cellNeighbors) {
				if (cell.getChars().get(characteristic) == ALIVE) {
					numLiveNeighbors++;
				}
			}
			if (oldCell.getChars().get(characteristic) == ALIVE) {
				if (numLiveNeighbors == 2 || numLiveNeighbors == 3) {
					myCell.getChars().put(characteristic, ALIVE);
				} else {
					myCell.getChars().put(characteristic, DEAD);
				}
			} else {
				if (numLiveNeighbors == 3) {
					myCell.getChars().put(characteristic, ALIVE);
				} else {
					myCell.getChars().put(characteristic, DEAD);
				}
			}
		}
	}

	public abstract Shape getCellShape(int index);

	public abstract List<Cell> findNeighbors(Grid myGrid, int index, Reader myReader);
}
