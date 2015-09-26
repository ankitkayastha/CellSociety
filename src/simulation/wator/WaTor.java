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
	private final String ANIMAL = "animal";
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
		
		int numFish = 0;
		int numShark  = 0;
		int numEmpty = 0;
		for (int i=0; i<myGridGrid.length; i++) {
			if (myGridGrid[i].getChars().get(ANIMAL)==FISH) {
				numFish++;
			}
			else if (myGridGrid[i].getChars().get(ANIMAL)==SHARK) {
				numShark++;
			}
			else 
				numEmpty++;
		}
	}

}
