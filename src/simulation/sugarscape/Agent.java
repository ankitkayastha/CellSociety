package simulation.sugarscape;

import model.Cell;
import model.NeighborFactory;

import java.util.*;

import data.Stats;
public class Agent {
	private Stats myStats;
	private NeighborFactory factory;
	
	public Agent(Stats stats) {
		factory = new NeighborFactory(stats);
		this.myStats = stats;
	}
	
	public void doAgent(Cell[] oldGrid, Cell[] newGrid, Stats stats) {
		for (int i = 0; i < stats.getSize(); i++) {
			if (newGrid[i].getChars().get("hasAnt")==0) {
				continue;
			}
			Cell cellToMove = getCellToMoveTo(newGrid, i, stats);
			if (cellToMove == null) {
				continue;
			}
			int newIndex = cellToMove.getChars().get("index");
			int antSugar = oldGrid[i].getChars().get("antSugar");
			int patchSugar = oldGrid[newIndex].getChars().get("patchSugar");
			int sugarMetab = oldGrid[i].getChars().get("antSugarMetabolism");
			
			System.out.printf("current grid: %d\n", i);
			System.out.printf("neighbor got: %d\n", newIndex);
			
			newGrid[i].getChars().put("hasAnt", 0); //original square won't have ant
			newGrid[newIndex].getChars().put("hasAnt", 1);
			newGrid[newIndex].getChars().put("antSugar", antSugar + patchSugar - sugarMetab);
			newGrid[newIndex].getChars().put("patchSugar", 0); //patch has no more sugar
			if (newGrid[newIndex].getChars().get("antSugar")<=0) {
				newGrid[newIndex].getChars().put("hasAnt", 0);
			}
		}
	}
	
	private int calculateShortestDistance(int index, int index2) {
		int rowOne = index / myStats.getGlobalChars().get("cols");
		int rowTwo = index2 / myStats.getGlobalChars().get("cols");
		int colOne = index % myStats.getGlobalChars().get("cols");
		int colTwo = index % myStats.getGlobalChars().get("cols");
		return Math.abs(rowOne-rowTwo) + Math.abs(colOne-colTwo);
	}
	
	private Cell getCellToMoveTo(Cell[] oldGrid, int index, Stats stats) {
		List<Cell> neighbors = factory.getNeighbors(oldGrid, index);
		// iterate through patches without ants
		for (int i=neighbors.size()-1; i>=0; i--) {
			if (neighbors.get(i).getChars().get("hasAnt")==1) {
				neighbors.remove(i);
			}
		}
		if (neighbors.size()==0) {
			return null;
		}
		// find patch with max sugar
		int maxSugar = -1;
		int minDistance = Integer.MAX_VALUE;
		Cell maxSugarCell = neighbors.get(0);
		
		for (int i = 0; i < neighbors.size(); i++) {
			int patchSugar = neighbors.get(i).getChars().get("patchSugar");
			if (patchSugar >= maxSugar) {
				if (calculateShortestDistance(index, neighbors.get(i).getChars().get("index"))<minDistance) {
					maxSugarCell = neighbors.get(i);
					minDistance = calculateShortestDistance(index, neighbors.get(i).getChars().get("index"));
				}
				maxSugar = patchSugar;
			}
		}
		return maxSugarCell;
	}

}
