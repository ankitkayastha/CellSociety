package simulation.wator;

import java.util.Random;

import data.Stats;
import model.Cell;
import model.Grid;
import simulation.Simulation;

public class WaTor extends Simulation {
	protected final int KELP = 0;
	protected final int FISH = 1;
	protected final int SHARK = 2;
	private Creature myFish;
	private Creature myShark;

	Random myRandom = new Random(1234);

	public WaTor(Stats stats) {
		super(stats);
		myFish = new Fish(stats);
		myShark = new Shark(stats);
	}

	@Override
	public void update(Grid myGrid, Stats stats) {
		Cell[] oldGrid = super.copyGrid(myGrid, stats);
		Cell[] myGridGrid = myGrid.getGrid();

		myFish.animalAction(oldGrid, myGridGrid, stats);
		myShark.animalAction(oldGrid, myGridGrid, stats);
	}

	@Override
	public void setParam(Double thisDouble) {}
}