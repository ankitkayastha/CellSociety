import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.*;
public class Segregation extends Simulation {
	
	private final int EMPTY = 0; //if cell is empty
	private double threshold;
	private String thresh = "threshold";
	private List<Color> listColor = new ArrayList<>();
	private List<Integer> vacantCells;
	private Color[] myColors;
	private Random myRandom;
	private String numRows = "rows";
	private String numCols = "cols";
	private String agents = "agents";
	private final String characteristicSegregation = "agent";

	public Segregation(Map<String, Integer> globalChars) {
		super(globalChars);
		threshold = globalChars.get(thresh);
		vacantCells = new ArrayList<Integer>(); //contains indices from array where empty cells are
		myRandom = new Random(1234);
		int numAgents = globalChars.get(agents);
		myColors = new Color[numAgents + 1];
		addColors(numAgents);
	}
	
	public void addColors(int numAgents) {
		System.out.println(numAgents);
		myColors[0] = Color.WHITE;
		for (int i = 1; i <= numAgents; i++) {
			double r = myRandom.nextDouble();
			double g = myRandom.nextDouble();
			double b = myRandom.nextDouble();
			double o = myRandom.nextDouble();
			myColors[i] = new Color(r, g, b, o);
		}
		for (int i = 0; i < myColors.length; i++) {
			//System.out.println(myColors[i].toString());
		}
	}
	@Override
	public Color getCellColor(int index, Grid myGrid) {
		Cell myCell = myGrid.getCell(index);
		//System.out.println("index is " + myCell.getChars().get(characteristicSegregation));
		return myColors[myCell.getChars().get(characteristicSegregation)];
	}

	@Override
	public void update(Grid myGrid, Reader myReader) {
		Cell[] oldGrid = new Cell[myReader.getSize()];
		Cell[] myGridGrid = myGrid.getGrid();
		//copy new grid to old grid
		//System.out.println(vacantCells.)
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell currentCell = myGrid.getCell(i);
			Map<String, Integer> myMap = currentCell.getChars();
			Map<String, Integer> oldMap = new HashMap<String, Integer>();
			for (String s : myMap.keySet()) {
				// maybe make string/integer primitive
				oldMap.put(s, myMap.get(s));
			}
			Cell oldCell = new Cell(oldMap);
			oldGrid[i] = oldCell;
		}
		for (int i = 0; i < myGridGrid.length; i++) {
			Cell cell = myGridGrid[i];
			if (cell.getChars().get(characteristicSegregation) == EMPTY) {
				vacantCells.add(i);
				//System.out.println("Adding to vacant cells at index" + i);
			}
		}
		
		
		//System.out.println("Vacant cells added and size is " + vacantCells.size());
		//moves all dissatisfied cells
		for (int i = 0; i < myReader.getSize(); i++) {
			Cell oldCell = oldGrid[i];
			Cell myCell = myGridGrid[i];
			List<Cell> cellNeighbors = findNeighbors(oldGrid, i, myReader);
			if (!isSatisfied(oldGrid, i, myReader, threshold)) {
				System.out.println("Cell at index" + i + " is not satisfied");
				moveDissatisfiedCell(myGridGrid, i); }
		}
		//reset vacant cells
		//System.out.println(vacantCells.size());
		//for (int i = 0; i < vacantCells.size(); i++) {
			//System.out.println("Removing index " + i + " from vacant cells");
		//	vacantCells.remove(i);
		//}
		vacantCells.clear();
		//System.out.println("Vacant cells size after clearing is " + vacantCells.size());
		//System.out.println("After removing vacant cells" + vacantCells.size());
		
	}
	

	@Override
	public List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader) {
		Map<String, Integer> myMap = myReader.getGlobalChars();
		int numCols = myMap.get("cols"); // returns number of columns
		int numRows = myMap.get("rows");
		int rowNum = index / numCols; // row number of cell
		int colNum = index % numCols; // col number of cell
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaRow = { -1, 0, 0, 1, 1, -1, -1, 1 };
		int[] deltaCol = { 0, 1, -1, 0, 1, 1, -1, -1 };
		int[] arrDelta = { -numCols, 1, -1, numCols, numCols + 1, -numCols + 1, -numCols - 1, numCols - 1 };
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum + deltaRow[i], colNum + deltaCol[i], numRows, numCols) && (!(myArr[i].getChars().get(characteristicSegregation) == EMPTY))) {
				neighborsList.add(myArr[index + arrDelta[i]]);
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
		Rectangle thisRect = new Rectangle(colNum*xSize, rowNum*ySize+40, xSize, ySize);
		thisRect.setStrokeWidth(4);
		thisRect.setStroke(Color.LIGHTGRAY);
		return thisRect;
	}
	
	
	public void moveDissatisfiedCell(Cell[] myArr, int index) {
		int indexVacantList = myRandom.nextInt(vacantCells.size()) ; //random number between 0 and size of list
		int indexGrid = vacantCells.get(indexVacantList);
		int agentType = myArr[index].getChars().get(characteristicSegregation);
		myArr[index].getChars().put(characteristicSegregation, EMPTY); //update dissatisfied cell's original location
		//vacantCells.add(index);
		System.out.println("Moving cell from index " + index + " to " + indexGrid);
		myArr[indexGrid].getChars().put(characteristicSegregation, agentType); //update empty cell to dissatisfied cell's agent type
	}
	
	public boolean isSatisfied(Cell[] myArr, int index, Reader myReader, double threshold) {
		boolean boolToReturn = false;
		int numSameNeighbors = 0;
		List<Cell> myNeighborList = findNeighbors(myArr, index, myReader);
		//System.out.println(myNeighborList.size());
		int selectedCellState = myArr[index].getChars().get(characteristicSegregation); 
		
		for (Cell cell: myNeighborList) {
			if (cell.getChars().get(characteristicSegregation) == selectedCellState)
				numSameNeighbors++;
		}
		//System.out.println("index " + index + " has " + numSameNeighbors);
		double percentageSame = (double) numSameNeighbors / myNeighborList.size();
		//System.out.println(threshold);
		return percentageSame >= (threshold / 100);
	}

}
