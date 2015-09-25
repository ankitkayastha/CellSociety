package simulation.spreading_fire;
import java.util.List;

import data.Stats;
import model.Cell;
import model.Grid;
import model.NeighborFactory;
import simulation.Simulation;

public class SpreadingFire extends Simulation {
	private final int EMPTY = 0; // empty ground or burnt tree
	private final int TREE = 1; // non burning tree
	private final int BURNING = 2; // burning tree
	private double probCatch; // probability of a cell catching fire based on
	private final String characteristicFire = "fire";
	private SpreadingFireCellCheck myCheck = new SpreadingFireCellCheck();
	
	
	public SpreadingFire(Stats stats) {
		super(stats);
		if (stats.getGlobalChars().keySet().contains("prob")) {
			probCatch = stats.getGlobalChars().get("prob")/100.0;
		}
	}

	@Override
	public void update(Grid myGrid, Stats myStats) {
		Cell[] oldGrid = super.copyGrid(myGrid, myStats);
		Cell[] myGridGrid = myGrid.getGrid();

		for (int i = 0; i < myStats.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];
			NeighborFactory myNeighborFactory = new NeighborFactory(myStats);
			
			List<Cell> cellNeighbors = myNeighborFactory.getNeighbors(oldGrid, i);
			if (oldCell.getChars().get(characteristicFire) == TREE) {
				if (myCheck.areNeighborsBurning(cellNeighbors)) {
						myCell.getChars().put(characteristicFire, myCheck.generateProbCatchState(probCatch));
				}
			}			
			if (oldCell.getChars().get(characteristicFire) == BURNING) {
				myCell.getChars().put(characteristicFire, EMPTY);
			}
		}
	}
	

}
