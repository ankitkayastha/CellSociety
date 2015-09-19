package simulation.segregation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import model.Cell;
import model.Grid;
import model.Reader;
import simulation.Simulation;

public class Segregation extends Simulation {
	private final int EMPTY = 0; 
	private double threshold;
	private String thresh = "threshold";
	private Color[] myColors;
	private Random myRandom;
	private String agents = "agents";
	private final String agent = "agent";

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
	
	private void addColors(int numAgents) {
		myColors[0] = Color.WHITE;
		for (int i = 1; i <= numAgents; i++) {
			double r = myRandom.nextDouble();
			double g = myRandom.nextDouble();
			double b = myRandom.nextDouble();
			double o = myRandom.nextDouble();
			myColors[i] = new Color(r, g, b, o);
		}
	}

	@Override
	public void update(Grid myGrid, Reader myReader) {
		Cell[] oldGrid = super.copyGrid(myGrid, myReader);
		Cell[] myGridGrid = myGrid.getGrid();
		
		//moves all dissatisfied cells
		for (int i = 0; i < myReader.getSize(); i++) {
			if ((oldGrid[i].getChars().get(agent)!=EMPTY) & !isSatisfied(oldGrid, i, myReader, threshold)) {
				if (hasEmpty(myGridGrid)) {
					move(myGridGrid, i);
				}
			}
		}
	}

	private void move(Cell[] myArr, int index) {
		Cell moveCell = findEmpty(myArr);
		int agentType = myArr[index].getChars().get(agent);
		moveCell.getChars().put(agent, agentType);

		myArr[index].getChars().put(agent, EMPTY);
	}
	
	private Cell findEmpty(Cell[] myArr) {
		List<Cell> returnCell = new ArrayList<Cell>();
		for (int i=0; i<myArr.length; i++) {
			if (myArr[i].getChars().get(agent)==EMPTY) {
				returnCell.add(myArr[i]);
			}
		}
		return returnCell.get(myRandom.nextInt(returnCell.size()));
	}
	
	private boolean hasEmpty(Cell[] myArr) {
		for (int i=0; i<myArr.length; i++) {
			if (myArr[i].getChars().get(agent)==EMPTY) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isSatisfied(Cell[] myArr, int index, Reader myReader, double threshold) {
		int numSameNeighbors = 0;
		List<Cell> myNeighborList = findNeighbors(myArr, index, myReader);
		int selectedCellState = myArr[index].getChars().get(agent); 
		
		for (Cell cell: myNeighborList) {
			if (cell.getChars().get(agent) == selectedCellState) {
				numSameNeighbors++;
			}
		}
		double percentageSame = (double) numSameNeighbors / myNeighborList.size();
		return percentageSame >= (threshold / 100.0);
	}
	
	@Override
	public Color getCellColor(int index, Grid myGrid) {
		Cell myCell = myGrid.getCell(index);
		if (!myCell.getChars().keySet().contains(agent)) {
			return Color.WHITE;
		}
		return myColors[myCell.getChars().get(agent)];
	}
	
	@Override
	public List<Cell> findNeighbors(Cell[] myArr, int index, Reader myReader) {
		int numCols = myReader.getGlobalChars().get("cols"); 
		int numRows = myReader.getGlobalChars().get("rows");
		int rowNum = index / numCols; // row number of cell
		int colNum = index % numCols; // col number of cell
		List<Cell> neighborsList = new ArrayList<Cell>();
		int[] deltaRow = { -1, 0, 0, 1, 1, -1, -1, 1 };
		int[] deltaCol = { 0, 1, -1, 0, 1, 1, -1, -1 };
		int[] arrDelta = { -numCols, 1, -1, numCols, numCols + 1, -numCols + 1, -numCols - 1, numCols - 1 };
		for (int i = 0; i < arrDelta.length; i++) {
			if (!isOutOfBounds(rowNum + deltaRow[i], colNum + deltaCol[i], numRows, numCols)){
				if ((myArr[index+arrDelta[i]].getChars().get(agent) != EMPTY)) {
					neighborsList.add(myArr[index+arrDelta[i]]);
				}
			}			
		}
		return neighborsList;
	}
}
