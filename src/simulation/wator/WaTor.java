package simulation.wator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import model.Cell;
import model.Grid;
import model.Reader;
import simulation.Simulation;

public class WaTor extends Simulation {
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

	public WaTor(Map<String, Integer> globalChars) {
		super(globalChars);
		if (globalChars.keySet().contains("sharkBreed")) {
			sharkBreed = globalChars.get("sharkBreed");
		}
		if (globalChars.keySet().contains("fishBreed")) {
			fishBreed = globalChars.get("fishBreed");
		}
		if (globalChars.keySet().contains("sharkLife")) {
			sharkLife = globalChars.get("sharkLife");
		}
	}

	@Override
	public void update(Grid myGrid, Reader myReader) {
		Cell[] oldGrid = super.copyGrid(myGrid, myReader);
		Cell[] myGridGrid = myGrid.getGrid();
		
		doFish(oldGrid, myGridGrid, myReader);
		doShark(oldGrid, myGridGrid, myReader);
	}
	
	private void doFish(Cell[] oldGrid, Cell[] myGridGrid, Reader myReader) {
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];

			if (oldCell.getChars().get(ANIMAL) == FISH) {
				if (oldCell.getChars().get(BREED) >= fishBreed) {
					if (has(i, myGridGrid, myReader, KELP)) {
						create(i, myGridGrid, myReader, FISH);
					}
				}
				if (has(i, myGridGrid, myReader, KELP)) {
					myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
					eat(i, myGridGrid, myReader, FISH, KELP);
				} else {
					myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
				}
			}
		}
	}
	
	private void doShark(Cell[] oldGrid, Cell[] myGridGrid, Reader myReader) {
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];

			if (oldCell.getChars().get(ANIMAL) == SHARK) {
				if (oldCell.getChars().get(HEALTH) <= 0) {
					myCell.getChars().put(ANIMAL, KELP);
					myCell.getChars().put(BREED, 0);
					myCell.getChars().put(HEALTH, 0);
				} else if (oldCell.getChars().get(BREED) >= sharkBreed) {
					myCell.getChars().put(HEALTH, myCell.getChars().get(HEALTH) - 1);
					myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
					if (has(i, myGridGrid, myReader, KELP)) {
						create(i, myGridGrid, myReader, SHARK);
					}
				} else if (has(i, myGridGrid, myReader, FISH)) {
					myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
					eat(i, myGridGrid, myReader, SHARK, FISH);
				} else if (has(i, myGridGrid, myReader, KELP)) {
					myCell.getChars().put(HEALTH, myCell.getChars().get(HEALTH) - 1);
					myCell.getChars().put(BREED, myCell.getChars().get(BREED) + 1);
					eat(i, myGridGrid, myReader, SHARK, KELP);
				}
			}
		}
	}
	
	private List<Cell> generateNeighbors(Cell[] grid, int index, Reader myReader, int animal) {
		List<Cell> neighbors = findNeighbors(grid, index, myReader);
		List<Cell> emptyNeighbors = new ArrayList<Cell>();
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get(ANIMAL) == animal) {
				emptyNeighbors.add(neighbors.get(i));
			}
		}
		return emptyNeighbors;
	}

	private void create(int index, Cell[] grid, Reader myReader, int animal) {
		List<Cell> emptyNeighbors = generateNeighbors(grid, index, myReader, KELP);
		int randIndex = myRandom.nextInt(emptyNeighbors.size());
		Cell createCell = emptyNeighbors.get(randIndex);
		createCell.getChars().put(ANIMAL, animal);
		createCell.getChars().put(BREED, 0);
		createCell.getChars().put(HEALTH, sharkLife);

		grid[index].getChars().put(BREED, 0);
	}

	private void eat(int index, Cell[] grid, Reader myReader, int animal, int eaten) {
		List<Cell> kelpNeighbors = generateNeighbors(grid, index, myReader, eaten);
		int randIndex = myRandom.nextInt(kelpNeighbors.size());
		Cell kelpCell = kelpNeighbors.get(randIndex);
		kelpCell.getChars().put(ANIMAL, animal);
		kelpCell.getChars().put(BREED, grid[index].getChars().get(BREED));
		kelpCell.getChars().put(HEALTH, grid[index].getChars().get(HEALTH));

		grid[index].getChars().put(ANIMAL, KELP);
		grid[index].getChars().put(BREED, 0);
		grid[index].getChars().put(HEALTH, 0);
	}

	private boolean has(int index, Cell[] grid, Reader myReader, int animal) {
		List<Cell> neighbors = findNeighbors(grid, index, myReader);
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get(ANIMAL) == animal) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Color getCellColor(int index, Grid myGrid) {
		Cell myCell = myGrid.getCell(index);
		if (myCell.getChars().get(ANIMAL) == KELP)
			return Color.GREEN;
		else if (myCell.getChars().get(ANIMAL) == FISH)
			return Color.BLUE;
		else
			return Color.YELLOW;
	}

	@Override
	public List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader) {
		int numCols = myReader.getGlobalChars().get("cols"); 
		int numRows = myReader.getGlobalChars().get("rows");
		int rowNum = index / numCols; // row number of cell
		int colNum = index % numCols; // col number of cell
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaRow = { -1, 0, 0, 1 };
		int[] deltaCol = { 0, 1, -1, 0 };
		int[] arrDelta = { -numCols, 1, -1, numCols };
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum + deltaRow[i], colNum + deltaCol[i], numRows, numCols)) {
				neighborsList.add(myArr[index + arrDelta[i]]);
			} 
		}
		return neighborsList;
	}
}
