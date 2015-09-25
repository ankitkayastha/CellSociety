package simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Cell;
import model.Grid;
import model.Reader;
import model.Stats;

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
			Cell oldCell = new Cell(oldMap);
			oldGrid[i] = oldCell;
		}
		return oldGrid;
	}
}
