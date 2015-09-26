package simulation.wator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import data.Stats;
import model.Cell;
import model.Grid;
import model.NeighborFactory;
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
		
		/*int numFish = 0;
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
		}*/
	}

}


/*public class WaTor extends Simulation {
	protected final int KELP = 0;
	protected final int FISH = 1;
	protected final int SHARK = 2;
	private int sharkBreed;
	private int fishBreed;
	private int sharkLife;
	private final String ANIMAL = "animal";
	private final String BREED = "breed";
	private final String HEALTH = "health";

	Random myRandom = new Random(1234);

	public WaTor(Stats myStats) {
		super(myStats);
		if (myStats.getGlobalChars().keySet().contains("sharkBreed")) {
			sharkBreed = myStats.getGlobalChars().get("sharkBreed");
		}
		if (myStats.getGlobalChars().keySet().contains("fishBreed")) {
			fishBreed = myStats.getGlobalChars().get("fishBreed");
		}
		if (myStats.getGlobalChars().keySet().contains("sharkLife")) {
			sharkLife = myStats.getGlobalChars().get("sharkLife");
		}
	}

	@Override
	public void update(Grid myGrid, Stats myStats) {
		Cell[] oldGrid = super.copyGrid(myGrid, myStats);
		Cell[] myGridGrid = myGrid.getGrid();

		doFish(oldGrid, myGridGrid, myStats);
		doShark(oldGrid, myGridGrid, myStats);
		
		int numFish = 0;
		int numShark  = 0;
		int numEmpty = 0;
		for (int i=0; i<myGridGrid.length; i++) {
			if (myGridGrid[i].getChars().get(ANIMAL)==FISH) {
				numFish++;
			}
			if (myGridGrid[i].getChars().get(ANIMAL)==SHARK) {
				numShark++;
			}
			if (myGridGrid[i].getChars().get(ANIMAL)==KELP) {
				numEmpty++;
			}
		}
		System.out.printf("Fish: %6.2f%%, Shark: %6.2f%%, Kelp: %6.2f%%\n", numFish*100.0/myGridGrid.length, numShark*100.0/myGridGrid.length, numEmpty*100.0/myGridGrid.length);
	}

	private void doFish(Cell[] oldGrid, Cell[] myGridGrid, Stats myStats) {
		for (int i = 0; i < myStats.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];

			if (oldCell.getChars().get(ANIMAL) == FISH) {
				if (oldCell.getChars().get(BREED) >= fishBreed) {
					if (has(i, myGridGrid, myStats, KELP)) {
						create(i, myGridGrid, myStats, FISH, KELP);
					} 
				}
				else if (has(i, myGridGrid, myStats, KELP)) {
					myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
					eat(i, myGridGrid, myStats, FISH, KELP);
				} else {
					myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
				}
			}
		}
	}

	private void doShark(Cell[] oldGrid, Cell[] myGridGrid, Stats myStats) {
		for (int i = 0; i < myStats.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];

			if (oldCell.getChars().get(ANIMAL) == SHARK) {
				if (oldCell.getChars().get(HEALTH) <= 0) {
					myCell.getChars().put(ANIMAL, KELP);
					myCell.getChars().put(BREED, 0);
					myCell.getChars().put(HEALTH, 0);
				} else { 
					if (oldCell.getChars().get(BREED) >= sharkBreed) {
						myCell.getChars().put(HEALTH, myCell.getChars().get(HEALTH) - 1);
						if (has(i, myGridGrid, myStats, KELP)) {
							create(i, myGridGrid, myStats, SHARK, KELP);
						}
						else if (has(i, myGridGrid, myStats, FISH)) {
							//create(i, myGridGrid, myReader, SHARK, FISH);
							myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
							myCell.getChars().put(HEALTH, myCell.getChars().get(HEALTH) + 1);
							eat(i, myGridGrid, myStats, SHARK, FISH);
						}
					} 
					else if (has(i, myGridGrid, myStats, FISH)) {
						myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
						myCell.getChars().put(HEALTH, myCell.getChars().get(HEALTH) + 1);
						eat(i, myGridGrid, myStats, SHARK, FISH);
					} else if (has(i, myGridGrid, myStats, KELP)) {
						myCell.getChars().put(HEALTH, myCell.getChars().get(HEALTH) - 1);
						myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
						eat(i, myGridGrid, myStats, SHARK, KELP);
					}
				}
			}
		}
	}

	private List<Cell> generateNeighbors(Cell[] grid, int index, Stats myStats, int animal) {
		NeighborFactory myNeighborFactory = new NeighborFactory(myStats);
		
		List<Cell> neighbors = myNeighborFactory.getNeighbors(grid, index);			
		List<Cell> emptyNeighbors = new ArrayList<Cell>();
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get(ANIMAL) == animal) {
				emptyNeighbors.add(neighbors.get(i));
			}
		}
		return emptyNeighbors;
	}

	private void create(int index, Cell[] grid, Stats myStats, int animal, int eaten) {
		List<Cell> emptyNeighbors = generateNeighbors(grid, index, myStats, eaten);
		int randIndex = myRandom.nextInt(emptyNeighbors.size());
		Cell createCell = emptyNeighbors.get(randIndex);
		createCell.getChars().put(ANIMAL, animal);
		createCell.getChars().put(BREED, 0);
		createCell.getChars().put(HEALTH, sharkLife);

		grid[index].getChars().put(BREED, 0);
	}

	private void eat(int index, Cell[] grid, Stats myStats, int animal, int eaten) {
		List<Cell> kelpNeighbors = generateNeighbors(grid, index, myStats, eaten);
		int randIndex = myRandom.nextInt(kelpNeighbors.size());
		Cell kelpCell = kelpNeighbors.get(randIndex);
		kelpCell.getChars().put(ANIMAL, animal);
		kelpCell.getChars().put(BREED, grid[index].getChars().get(BREED));
		kelpCell.getChars().put(HEALTH, grid[index].getChars().get(HEALTH));

		grid[index].getChars().put(ANIMAL, KELP);
		grid[index].getChars().put(BREED, 0);
		grid[index].getChars().put(HEALTH, 0);
	}

	private boolean has(int index, Cell[] grid, Stats myStats, int animal) {
		NeighborFactory myNeighborFactory = new NeighborFactory(myStats);
		
		List<Cell> neighbors = myNeighborFactory.getNeighbors(grid, index);	
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get(ANIMAL) == animal) {
				return true;
			}
		}
		return false;
	}
}*/