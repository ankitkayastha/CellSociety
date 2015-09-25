package simulation;

import java.util.HashMap;
import java.util.Map;

import model.Cell;
import model.CellFactory;
import model.Grid;
import data.Stats;

public abstract class Simulation {	
	protected Stats myStats;
	
	public Simulation(Stats stats){
		myStats = stats;
	}
		
	public abstract void update(Grid currentGrid, Stats myStats);
	
	protected Cell[] copyGrid(Grid myGrid, Stats myStats) {
		Cell[] oldGrid = new Cell[myStats.getSize()];
		for (int i = 0; i < myStats.getSize(); i++) {
			Cell currentCell = myGrid.getCell(i);
			Map<String, Integer> myMap = currentCell.getChars();
			Map<String, Integer> oldMap = new HashMap<String, Integer>();
			for (String s : myMap.keySet()) {
				oldMap.put(s, myMap.get(s));
			}
			CellFactory thisCellFactory = new CellFactory(myStats);
			Cell oldCell = thisCellFactory.createCell(myStats.getCellData(i));
			oldGrid[i] = oldCell;
		}
		return oldGrid;
	}
}
