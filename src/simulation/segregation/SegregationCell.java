package simulation.segregation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import model.Cell;
import model.NeighborFactory;
import model.Stats;
import simulation.Simulation;

public class SegregationCell extends Cell {
	private final int EMPTY = 0;
	private double threshold;
	private String thresh = "threshold";
	private Color[] myColors;
	private Random myRandom;
	private String agents = "agents";
	private final String agent = "agent";
	
	public SegregationCell(Map<String, Integer> characteristicMap, Map<Integer, Color> colorMap) {
		super(characteristicMap, colorMap);
	}

	@Override
	public void fillColorMap() {
		
	}

	@Override
	public Color getCellColor(int state) {
		// TODO Auto-generated method stub
		return null;
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
