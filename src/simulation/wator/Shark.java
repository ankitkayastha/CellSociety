package simulation.wator;

import data.Stats;
import model.Cell;


public class Shark extends Creature {
	
	private int sharkBreed;
	
	public Shark(Stats stats) {
		super(stats);
		if (globalCharsMap.keySet().contains("sharkBreed")) {
			sharkBreed = globalCharsMap.get("sharkBreed");
		}
		if (globalCharsMap.keySet().contains("sharkLife")) {
			sharkLife = globalCharsMap.get("sharkLife");
		}
		
	}

	@Override
	public void animalAction(Cell[] oldGrid, Cell[] myGridGrid, Stats stats) {
		for (int i = 0; i < stats.getSize(); i++) {
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
						if (hasAnimal(i, myGridGrid, stats, KELP)) {
							reproduce(i, myGridGrid, stats, SHARK, KELP);
						}
						else if (hasAnimal(i, myGridGrid, stats, FISH)) {
							//create(i, myGridGrid, myReader, SHARK, FISH);
							myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
							myCell.getChars().put(HEALTH, myCell.getChars().get(HEALTH) + 1);
							eat(i, myGridGrid, stats, SHARK, FISH);
						}
					} 
					else if (hasAnimal(i, myGridGrid, stats, FISH)) {
						myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
						myCell.getChars().put(HEALTH, myCell.getChars().get(HEALTH) + 1);
						eat(i, myGridGrid, stats, SHARK, FISH);
					} else if (hasAnimal(i, myGridGrid, stats, KELP)) {
						myCell.getChars().put(HEALTH, myCell.getChars().get(HEALTH) - 1);
						myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
						eat(i, myGridGrid, stats, SHARK, KELP);
					}
				}
			}
		}
	}
}
