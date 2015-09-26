package simulation.game_of_life;

import java.util.List;

import data.Stats;
import model.Cell;
import model.Grid;
import model.NeighborFactory;
import simulation.Simulation;

public class GameOfLife extends Simulation {
	public GameOfLife(Stats stats) {
		super(stats);
	}

	private final String characteristic = "life";
	protected final int DEAD = 0;
	protected final int ALIVE = 1;

	@Override
	public void update(Grid myGrid, Stats myStats) {
		Cell[] oldGrid = super.copyGrid(myGrid, myStats);
		Cell[] myGridGrid = myGrid.getGrid();

		for (int i = 0; i < myStats.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];
			NeighborFactory myNeighborFactory = new NeighborFactory(myStats);
			
			List<Cell> cellNeighbors = myNeighborFactory.getNeighbors(oldGrid, i);

			int numLiveNeighbors = 0;
			for (Cell cell : cellNeighbors) {
				//System.out.printf("cell: %s\n", cell.getChars().toString());
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
}
