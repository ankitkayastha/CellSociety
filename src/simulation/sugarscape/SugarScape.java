package simulation.sugarscape;

import data.Stats;
import model.Cell;
import model.Grid;
import simulation.Simulation;

public class SugarScape extends Simulation {
	
	private Patch myPatch;
	private Agent myAgent;
	public SugarScape(Stats stats) {
		super(stats);
		myPatch = new Patch();
		myAgent = new Agent(stats);
	}

	@Override
	public void update(Grid currentGrid, Stats myStats) {
		Cell[] oldGrid = super.copyGrid(currentGrid, myStats);
		Cell[] myGridGrid = currentGrid.getGrid();
		
		myPatch.doPatch(oldGrid, myGridGrid); //update patches
		myAgent.doAgent(oldGrid, myGridGrid, myStats);
	}

}
