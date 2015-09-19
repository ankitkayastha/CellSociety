import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;

public class WaTor extends Simulation {

	protected final int KELP = 0;
	protected final int FISH = 1;
	protected final int SHARK = 2;
	private int sharkBreed;
	private int fishBreed;
	private int sharkLife;
	
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
	public Color getCellColor(int index, Grid myGrid) {
		Cell myCell = myGrid.getCell(index);
		if (myCell.getChars().get("animal") == KELP)
			return Color.GREEN;
		else if (myCell.getChars().get("animal") == FISH)
			return Color.BLUE;
		else
			return Color.YELLOW;
	}

	@Override
	public void update(Grid myGrid, Reader myReader) {
		Cell[] oldGrid = new Cell[myReader.getSize()];
		Cell[] myGridGrid = myGrid.getGrid();

		for (int i = 0; i < myReader.getSize(); i++) {
			Cell currentCell = myGrid.getCell(i);

			Map<String, Integer> myMap = currentCell.getChars();
			Map<String, Integer> oldMap = new HashMap<String, Integer>();
			for (String s : myMap.keySet()) {
				oldMap.put(s, myMap.get(s));
			}
			Cell oldCell = new Cell(oldMap);
			oldGrid[i] = oldCell;
		}
		
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];

			if (oldCell.getChars().get("animal") == FISH) {
				if (oldCell.getChars().get("breed") >= fishBreed) {
					if (has(i, myGridGrid, myReader, KELP)) {
						create(i, myGridGrid, myReader, FISH);
					}
				}
				if (has(i, myGridGrid, myReader, KELP)) {
					myCell.getChars().put("breed", myCell.getChars().get("breed") + 1);
					eat(i, myGridGrid, myReader, FISH, KELP);
				} else {
					myCell.getChars().put("breed", myCell.getChars().get("breed") + 1);
				}
			}
		}

		for (int i = 0; i < myReader.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];

			if (oldCell.getChars().get("animal") == SHARK) {
				if (oldCell.getChars().get("health") <= 0) {
					myCell.getChars().put("animal", KELP);
					myCell.getChars().put("breed", 0);
					myCell.getChars().put("health", 0);
				} else if (oldCell.getChars().get("breed") >= sharkBreed) {
					myCell.getChars().put("health", myCell.getChars().get("health") - 1);
					myCell.getChars().put("breed", myCell.getChars().get("breed") + 1);
					if (has(i, myGridGrid, myReader, KELP)) {
						create(i, myGridGrid, myReader, SHARK);
					}
				} else if (has(i, myGridGrid, myReader, FISH)) {
					myCell.getChars().put("breed", myCell.getChars().get("breed") + 1);
					eat(i, myGridGrid, myReader, SHARK, FISH);
				} else if (has(i, myGridGrid, myReader, KELP)) {
					myCell.getChars().put("health", myCell.getChars().get("health") - 1);
					myCell.getChars().put("breed", myCell.getChars().get("breed") + 1);
					eat(i, myGridGrid, myReader, SHARK, KELP);
				}
			}
		}
	}
	
	private List<Cell> generateNeighbors(Cell[] grid, int index, Reader myReader, int animal) {
		List<Cell> neighbors = findNeighbors(grid, index, myReader);
		List<Cell> emptyNeighbors = new ArrayList<Cell>();
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get("animal") == animal) {
				emptyNeighbors.add(neighbors.get(i));
			}
		}
		return emptyNeighbors;
	}

	private void create(int index, Cell[] grid, Reader myReader, int animal) {
		List<Cell> emptyNeighbors = generateNeighbors(grid, index, myReader, KELP);
		int randIndex = myRandom.nextInt(emptyNeighbors.size());
		Cell createCell = emptyNeighbors.get(randIndex);
		createCell.getChars().put("animal", animal);
		createCell.getChars().put("breed", 0);
		createCell.getChars().put("health", sharkLife);

		grid[index].getChars().put("breed", 0);
	}

	private void eat(int index, Cell[] grid, Reader myReader, int animal, int eaten) {
		List<Cell> kelpNeighbors = generateNeighbors(grid, index, myReader, eaten);
		int randIndex = myRandom.nextInt(kelpNeighbors.size());
		Cell kelpCell = kelpNeighbors.get(randIndex);
		kelpCell.getChars().put("animal", animal);
		kelpCell.getChars().put("breed", grid[index].getChars().get("breed"));
		kelpCell.getChars().put("health", grid[index].getChars().get("health"));

		grid[index].getChars().put("animal", KELP);
		grid[index].getChars().put("breed", 0);
		grid[index].getChars().put("health", 0);
	}

	private boolean has(int index, Cell[] grid, Reader myReader, int animal) {
		List<Cell> neighbors = findNeighbors(grid, index, myReader);
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get("animal") == animal) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader) {
		Map<String, Integer> myMap = myReader.getGlobalChars();
		int numCols = myMap.get("cols"); // returns number of columns
		int numRows = myMap.get("rows");
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
