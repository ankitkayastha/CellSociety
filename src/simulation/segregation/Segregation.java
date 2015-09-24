package simulation.segregation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import model.Cell;
import model.Grid;
import model.NeighborFactory;
import model.Reader;
import model.Stats;
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
		super(globalChars, EMPTY, EMPTY);
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
	public void update(Grid myGrid, Stats myStats) {
		Cell[] oldGrid = super.copyGrid(myGrid, myStats);
		Cell[] myGridGrid = myGrid.getGrid();
		
		//moves all dissatisfied cells
		for (int i = 0; i < myStats.getSize(); i++) {
			if ((oldGrid[i].getChars().get(agent)!=EMPTY) & !isSatisfied(oldGrid, i, myStats, threshold)) {
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
	
	private boolean isSatisfied(Cell[] myArr, int index, Stats myStats, double threshold) {
		int numSameNeighbors = 0;
		NeighborFactory myNeighborFactory = new NeighborFactory(myStats, super.thisSim, super.thisShape);
		
		List<Cell> cellNeighbors = myNeighborFactory.getNeighbors(myArr, index);		
		int selectedCellState = myArr[index].getChars().get(agent); 
		
		for (Cell cell: cellNeighbors) {
			if (cell.getChars().get(agent) == selectedCellState) {
				numSameNeighbors++;
			}
		}
		double percentageSame = (double) numSameNeighbors / cellNeighbors.size();
		return percentageSame >= (threshold / 100.0);
	}
}
