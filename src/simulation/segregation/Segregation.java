package simulation.segregation;


import data.Stats;
import model.Cell;
import model.Grid;
import simulation.Simulation;

public class Segregation extends Simulation {
	private final int EMPTY = 0; 
	private double threshold;
	private String thresh = "threshold";
	private final String agent = "agent";
	private SegregationCellCheck cellCheck = new SegregationCellCheck();
	
	public Segregation(Stats stats) {
		super(stats);
		if (stats.getGlobalChars().keySet().contains(thresh)){
			threshold = stats.getGlobalChars().get(thresh);
		}
	}
	
	@Override
	public void update(Grid myGrid, Stats myStats) {
		Cell[] oldGrid = super.copyGrid(myGrid, myStats);
		Cell[] myGridGrid = myGrid.getGrid();
		
		//moves all dissatisfied cells
		for (int i = 0; i < myStats.getSize(); i++) {
			if ((oldGrid[i].getChars().get(agent)!=EMPTY) & !cellCheck.isSatisfied(oldGrid, i, myStats, threshold)) {
				if (cellCheck.hasEmpty(myGridGrid)) {
					cellCheck.move(myGridGrid, i);
				}
			}
		}
	}
	
}
