package simulation.segregation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Cell;
import model.Grid;
import model.Reader;
import simulation.Simulation;

public class Segregation extends Simulation {
	
	private final int EMPTY = 0; //if cell is empty
	private double threshold;
	private String thresh = "threshold";
	private Color[] myColors;
	private Random myRandom;
	private String agents = "agents";
	private final String characteristicSegregation = "agent";

	public Segregation(Map<String, Integer> globalChars) {
		super(globalChars);
		if (globalChars.keySet().contains(thresh)){
			threshold = globalChars.get(thresh);
		}
		myRandom = new Random(1234);
		int numAgents = 0;
		if (globalChars.keySet().contains(agents)){
			numAgents = globalChars.get(agents);
		}
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
				String tempS = new String(s);
				int tempInt = myMap.get(s);
				oldMap.put(tempS, tempInt);
			}
			Cell oldCell = new Cell(oldMap);
			oldGrid[i] = oldCell;
		}		
		
		//System.out.println("Vacant cells added and size is " + vacantCells.size());
		//moves all dissatisfied cells
		for (int i = 0; i < myReader.getSize(); i++) {
			if ((oldGrid[i].getChars().get(characteristicSegregation)!=EMPTY) & !isSatisfied(oldGrid, i, myReader, threshold)) {
				if (hasEmpty(myGridGrid)) {
					moveDissatisfiedCell(myGridGrid, i);
				}
			}
		}
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
			if (!isOutOfBounds(rowNum + deltaRow[i], colNum + deltaCol[i], numRows, numCols)){
				if ((myArr[index+arrDelta[i]].getChars().get(characteristicSegregation) != EMPTY)) {
					neighborsList.add(myArr[index+arrDelta[i]]);
				}
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
	
	
	private void moveDissatisfiedCell(Cell[] myArr, int index) {
		
		Cell moveCell = findEmpty(myArr);
		int agentType = myArr[index].getChars().get(characteristicSegregation);
		// may have to add in from old
		moveCell.getChars().put(characteristicSegregation, agentType);

		myArr[index].getChars().put(characteristicSegregation, EMPTY);
		System.out.printf("Moved index %d\n", index);
	}
	
	private Cell findEmpty(Cell[] myArr) {
		List<Cell> returnCell = new ArrayList<Cell>();
		for (int i=0; i<myArr.length; i++) {
			if (myArr[i].getChars().get(characteristicSegregation)==EMPTY) {
				returnCell.add(myArr[i]);
			}
		}
		return returnCell.get(myRandom.nextInt(returnCell.size()));
	}
	
	private boolean hasEmpty(Cell[] myArr) {
		for (int i=0; i<myArr.length; i++) {
			if (myArr[i].getChars().get(characteristicSegregation)==EMPTY) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isSatisfied(Cell[] myArr, int index, Reader myReader, double threshold) {
		int numSameNeighbors = 0;
		List<Cell> myNeighborList = findNeighbors(myArr, index, myReader);
		int selectedCellState = myArr[index].getChars().get(characteristicSegregation); 
		
		for (Cell cell: myNeighborList) {
			if (cell.getChars().get(characteristicSegregation) == selectedCellState)
				numSameNeighbors++;
		}
		//System.out.println("index " + index + " has " + numSameNeighbors);
		double percentageSame = (double) numSameNeighbors / myNeighborList.size();
		//System.out.printf("index: %d, threshold: %f, percsame: %f\n",index, threshold, percentageSame);
		if (percentageSame<(threshold/100.0)) {
			//System.out.println("Cell at index" + index + " is not satisfied");
			//System.out.printf("Length of neighbors: %d\n", myNeighborList.size());
		}
		return percentageSame >= (threshold / 100.0);
	}

}
