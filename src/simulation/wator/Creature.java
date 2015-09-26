package simulation.wator;

import java.util.*;

import data.Stats;
import model.Cell;
import model.NeighborFactory;

public abstract class Creature {
	
	protected final int KELP = 0;
	protected final int FISH = 1;
	protected final int SHARK = 2;
	protected int sharkLife;
	protected final String ANIMAL = "animal";
	protected final String BREED = "breed";
	protected final String HEALTH = "health";
	private Random myRandom;
	protected NeighborFactory myFactory;
	protected Map<String, Integer> globalCharsMap;
	
	
	public Creature(Stats stats) {
		myFactory = new NeighborFactory(stats);
		myRandom = new Random();
		globalCharsMap = stats.getGlobalChars();
		if (globalCharsMap.keySet().contains("sharkLife")) {
			sharkLife = globalCharsMap.get("sharkLife");
		}
		
	}
	
	public List<Cell> generateNeighbors(Cell[] grid, int index, Stats stats, int animal) {
		List<Cell> allNeighbors = myFactory.getNeighbors(grid, index);
		List<Cell> cellNeighbors = new ArrayList<Cell>();
		for (int i = 0; i < allNeighbors.size(); i++) {
			if (allNeighbors.get(i).getChars().get(ANIMAL) == animal) {
				cellNeighbors.add(allNeighbors.get(i));
			}
		}
		return cellNeighbors;
	}
	
	public void reproduce(int index, Cell[] grid, Stats stats, int animal, int eaten) {
		List<Cell> cellNeighbors = generateNeighbors(grid, index, stats, eaten);
		int randIndex = myRandom.nextInt(cellNeighbors.size());
		Cell neighborCell = cellNeighbors.get(randIndex);
		neighborCell.getChars().put(ANIMAL, animal);
		neighborCell.getChars().put(BREED, 0);
		neighborCell.getChars().put(HEALTH, sharkLife);

		grid[index].getChars().put(BREED, 0);
	}
	
	public void eat(int index, Cell[] grid, Stats stats, int animal, int eaten) {
		List<Cell> kelpNeighbors = generateNeighbors(grid, index, stats, eaten);
		int randIndex = myRandom.nextInt(kelpNeighbors.size());
		Cell kelpCell = kelpNeighbors.get(randIndex);
		kelpCell.getChars().put(ANIMAL, animal);
		kelpCell.getChars().put(BREED, grid[index].getChars().get(BREED));
		kelpCell.getChars().put(HEALTH, grid[index].getChars().get(HEALTH));

		grid[index].getChars().put(ANIMAL, KELP);
		grid[index].getChars().put(BREED, 0);
		grid[index].getChars().put(HEALTH, 0);
	}
	
	public boolean hasAnimal(int index, Cell[] grid, Stats myStats, int animal) {		
		List<Cell> cellNeighbors = myFactory.getNeighbors(grid, index);			
		for (int i = 0; i < cellNeighbors.size(); i++) {
			if (cellNeighbors.get(i).getChars().get(ANIMAL) == animal) {
				return true;
			}
		}
		return false;
	}
	
	public abstract void animalAction(Cell[] oldGrid, Cell[] myGridGrid, Stats stats);
}
