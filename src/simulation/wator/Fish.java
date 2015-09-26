package simulation.wator;

import data.Stats;
import model.Cell;


public class Fish extends Creature {
	
	private int fishBreed;
	
	
	public Fish(Stats stats) {
		super(stats);
		if (globalCharsMap.keySet().contains("fishBreed")) {
			fishBreed = globalCharsMap.get("fishBreed");
		}
	}

	@Override
	public void animalAction(Cell[] oldGrid, Cell[] myGridGrid, Stats stats) {
		for (int i = 0; i < stats.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];

			if (oldCell.getChars().get(ANIMAL) == FISH) {
				if (oldCell.getChars().get(BREED) >= fishBreed) {
					if (hasAnimal(i, myGridGrid, stats, KELP)) {
						reproduce(i, myGridGrid, stats, FISH, KELP);
						System.out.println("Fish is reproducing");
					} 
				}
				else if (hasAnimal(i, myGridGrid, stats, KELP)) {
					myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
					eat(i, myGridGrid, stats, FISH, KELP);
				} else {
					myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
				}
			}
		}
	}
}
