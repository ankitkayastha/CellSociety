import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class WaTor extends Simulation {

	protected final int KELP = 0;
	protected final int FISH = 1;
	protected final int SHARK = 2;
	private int sharkBreed;
	private int fishBreed;
	private int sharkLife;
	
	Random myRandom = new Random();

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
		//Cell[] oldGrid = new Cell[myReader.getSize()];
		Cell[] myGridGrid = myGrid.getGrid();

		for (int i = 0; i < myReader.getSize(); i++) {
			Cell currentCell = myGrid.getCell(i);

			Map<String, Integer> myMap = currentCell.getChars();
			Map<String, Integer> oldMap = new HashMap<String, Integer>();
			for (String s : myMap.keySet()) {
				String tempS = new String(s);
				int tempInt = myMap.get(s);
				// maybe make string/integer primitive
				oldMap.put(tempS, tempInt);
			}
			Cell oldCell = new Cell(oldMap);
			//oldGrid[i] = oldCell;
			System.out.printf("Animal: %d, at Index %d\n", currentCell.getChars().get("animal"), currentCell.getChars().get("index"));

		}

		for (int i = 0; i < myReader.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];

			if (oldCell.getChars().get("animal") == SHARK) {
				System.out.printf("Actual Animal: %d, at Index %d\n", oldCell.getChars().get("animal"), i);

				if (oldCell.getChars().get("health") <= 0) {
					myCell.getChars().put("animal", KELP);
					myCell.getChars().put("breed", 0);
					myCell.getChars().put("health", 0);
				} else if (oldCell.getChars().get("breed") >= sharkBreed) {
					myCell.getChars().put("health", oldCell.getChars().get("health") - 1);
					myCell.getChars().put("breed", oldCell.getChars().get("breed") + 1);
					if (hasKelp(i, myGridGrid, myReader)) {
						createShark(i, myGridGrid, myReader);
					}
				} else if (hasFish(i, myGridGrid, myReader)) {
					//System.out.printf("Fish exists for shark at %d\n", i);
					myCell.getChars().put("breed", oldCell.getChars().get("breed") + 1);
					eatFish(i, myGridGrid, myReader);
				} else if (hasKelp(i, myGridGrid, myReader)) {
					myCell.getChars().put("health", oldCell.getChars().get("health") - 1);
					myCell.getChars().put("breed", oldCell.getChars().get("breed") + 1);
					move(i, myGridGrid, myReader);
				}
			}
		}
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];

			if (oldCell.getChars().get("animal") == FISH) {
				System.out.printf("Actual Animal: %d, at Index %d\n", oldCell.getChars().get("animal"), i);

				if (oldCell.getChars().get("breed") >= fishBreed) {
					if (hasKelp(i, myGridGrid, myReader)) {
						createFish(i, myGridGrid, myReader);
					}
				}
				if (hasKelp(i, myGridGrid, myReader)) {
					myCell.getChars().put("breed", oldCell.getChars().get("breed") + 1);
					eatKelp(i, myGridGrid, myReader);
				} else {
					myCell.getChars().put("breed", oldCell.getChars().get("breed") + 1);
				}
			}
		}
	}

	private void createFish(int index, Cell[] grid, Reader myReader) {
		List<Cell> neighbors = findNeighbors(grid, index, myReader);
		List<Cell> emptyNeighbors = new ArrayList<Cell>();
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get("animal") == KELP) {
				emptyNeighbors.add(neighbors.get(i));
			}
		}
		int randIndex = myRandom.nextInt(emptyNeighbors.size());
		Cell createCell = emptyNeighbors.get(randIndex);
		createCell.getChars().put("animal", FISH);
		createCell.getChars().put("breed", 0);
		createCell.getChars().put("health", sharkLife);

		grid[index].getChars().put("breed", 0);
		System.out.printf("Fish %d bred fish at %d\n", index, createCell.getChars().get("index"),index);

		return;
	}

	private void createShark(int index, Cell[] grid, Reader myReader) {
		List<Cell> neighbors = findNeighbors(grid, index, myReader);

		List<Cell> emptyNeighbors = new ArrayList<Cell>();
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get("animal") == KELP) {
				emptyNeighbors.add(neighbors.get(i));
			}
		}
		int randIndex = myRandom.nextInt(emptyNeighbors.size());
		Cell createCell = emptyNeighbors.get(randIndex);
		createCell.getChars().put("animal", SHARK);
		createCell.getChars().put("breed", 0);
		createCell.getChars().put("health", sharkLife);

		grid[index].getChars().put("breed", 0);
		System.out.printf("Shark %d bred shark at %d\n", index, createCell.getChars().get("index"),index);

		return;
	}

	private void eatKelp(int index, Cell[] grid, Reader myReader) {
		List<Cell> neighbors = findNeighbors(grid, index, myReader);

		List<Cell> kelpNeighbors = new ArrayList<Cell>();
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get("animal") == KELP) {
				kelpNeighbors.add(neighbors.get(i));
			}
		}
		int randIndex = myRandom.nextInt(kelpNeighbors.size());
		Cell kelpCell = kelpNeighbors.get(randIndex);
		kelpCell.getChars().put("animal", FISH);
		kelpCell.getChars().put("breed", grid[index].getChars().get("breed")+0);
		kelpCell.getChars().put("health", grid[index].getChars().get("health")+0);

		grid[index].getChars().put("animal", KELP);
		grid[index].getChars().put("breed", 0);
		grid[index].getChars().put("health", 0);
		System.out.printf("Fish %d ate kelp at %d\n", index, kelpCell.getChars().get("index"));
	}

	private void eatFish(int index, Cell[] grid, Reader myReader) {
		List<Cell> neighbors = findNeighbors(grid, index, myReader);

		List<Cell> fishNeighbors = new ArrayList<Cell>();
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get("animal") == FISH) {
				fishNeighbors.add(neighbors.get(i));
			}
		}
		int randIndex = myRandom.nextInt(fishNeighbors.size());
		Cell fishCell = fishNeighbors.get(randIndex);
		fishCell.getChars().put("animal", SHARK);
		fishCell.getChars().put("breed", grid[index].getChars().get("breed")+0);
		fishCell.getChars().put("health", grid[index].getChars().get("health")+0);

		grid[index].getChars().put("animal", KELP);
		grid[index].getChars().put("breed", 0);
		grid[index].getChars().put("health", 0);
		System.out.printf("Shark %d ate fish at %d\n", index, fishCell.getChars().get("index"));

		return;
	}

	private void move(int index, Cell[] grid, Reader myReader) {
		List<Cell> neighbors = findNeighbors(grid, index, myReader);

		List<Cell> emptyNeighbors = new ArrayList<Cell>();
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get("animal") == KELP) {
				emptyNeighbors.add(neighbors.get(i));
			}
		}
		int randIndex = myRandom.nextInt(emptyNeighbors.size());
		Cell moveCell = emptyNeighbors.get(randIndex);
		moveCell.getChars().put("animal", FISH);
		moveCell.getChars().put("breed", grid[index].getChars().get("breed")+0);
		moveCell.getChars().put("health", grid[index].getChars().get("health")+0);

		grid[index].getChars().put("animal", KELP);
		grid[index].getChars().put("breed", 0);
		grid[index].getChars().put("health", 0);
		System.out.printf("Shark %d moved to %d\n", index, moveCell.getChars().get("index"));

		return;
	}

	private boolean hasFish(int index, Cell[] grid, Reader myReader) {
		List<Cell> neighbors = findNeighbors(grid, index, myReader);
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get("animal") == FISH) {
				return true;
			}
		}
		return false;
	}

	private boolean hasKelp(int index, Cell[] grid, Reader myReader) {
		List<Cell> neighbors = findNeighbors(grid, index, myReader);
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get("animal") == KELP) {
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
			} else {
				// System.out.printf("InsideRow: %d, InsideCol: %d\n", rowNum +
				// deltaRow[i], colNum + deltaCol[i]);
			}
		}
		return neighborsList;
	}

	@Override
	public Shape getCellShape(int index, int width, int height, int rows, int cols) {
		double ySize = (double) height / rows;
		double xSize = (double) width / cols;
		int colNum = index % cols;
		int rowNum = index / cols;
		Rectangle thisRect = new Rectangle(colNum * xSize, rowNum * ySize + 40, xSize, ySize);
		thisRect.setStrokeWidth(4);
		thisRect.setStroke(Color.LIGHTGRAY);
		return thisRect;
	}

}
