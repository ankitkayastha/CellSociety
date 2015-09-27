package simulation.sugarscape;

import model.Cell;
import model.NeighborFactory;

import java.util.*;

import data.Stats;
public class Agent {
	
	
	private String vision = "vision";
	private String sugar = "sugar"; //sugar of ant
	private String sugarMetabolism = "sugarMetabolism";
	private final String amountSugar = "sugarAmount"; //sugar of patch
	private final String cols = "cols";
	private final String index = "index";
	private final String hasAnt = "hasAnt";
	
	private NeighborFactory factory;
	
	public Agent(Stats stats) {
		factory = new NeighborFactory(stats);
	}
	
	
	public void doAgent(Cell[] oldGrid, Cell[] newGrid, Stats stats) {
		for (int i = 0; i < stats.getSize(); i++) {
			Cell cellToMove = getCellToMoveTo(oldGrid, i, stats);
			int newIndex = cellToMove.getChars().get(index);
			int antSugar = oldGrid[i].getChars().get(sugar);
			int patchSugar = oldGrid[newIndex].getChars().get(amountSugar);
			int sugarMetab = oldGrid[i].getChars().get(sugarMetabolism);
			
			
			newGrid[i].getChars().put(hasAnt, 0); //original square won't have ant
			newGrid[newIndex].getChars().put(hasAnt, 1);
			newGrid[newIndex].getChars().put(sugar, antSugar + patchSugar - sugarMetab);
			newGrid[newIndex].getChars().put(amountSugar, 0); //patch has no more sugar
		}
	}
	

	
	public double calculateShortestDistance(int rowOne, int colOne, int colTwo, int rowTwo) {
		return Math.sqrt(Math.pow((rowTwo - rowOne), 2) - Math.pow((colTwo - colOne), 2));
	}
	
	public Map<Cell, Double> findShortestDistanceCell(Map<Cell, Integer> sugarMap, int index, Stats stats) {
		Map<Cell, Double> distanceMap = new TreeMap<Cell, Double>();
		int numCols = stats.getGlobalChars().get(cols);
		int rowLocation = index / numCols;
		int colLocation = index % numCols;
		for (Cell cell: sugarMap.keySet()) {
			int rowCompare = cell.getChars().get(index) / numCols;
			int colCompare = cell.getChars().get(index) % numCols;
			distanceMap.put(cell, calculateShortestDistance(rowLocation, colLocation, rowCompare, colCompare));
		}
		
		return distanceMap;
	}
	
	
	public Cell getCellToMoveTo(Cell[] oldGrid, int index, Stats stats) {
		List<Cell> neighbors = factory.getNeighbors(oldGrid, index);
		Map<Cell, Integer> sugarMap = new TreeMap<Cell, Integer>();
		int maxSugar = -1;
		for (int i = 0; i < neighbors.size(); i++) {
			int patchSugar = neighbors.get(i).getChars().get(amountSugar);
			if (patchSugar >= maxSugar) {
				sugarMap.put(neighbors.get(i), patchSugar);
				maxSugar = patchSugar;
			}
		}
		//now have map of cell to max sugar
		
		Map<Cell, Double> distanceMap = findShortestDistanceCell(sugarMap, index, stats); 
		double min = Double.MAX_VALUE;
		Cell cellToMove = null;
		for (Cell cell: distanceMap.keySet()) {
			if (distanceMap.get(cell) < min) {
				min = distanceMap.get(cell);
				cellToMove = cell;
			}
		}
		return cellToMove;
		
		
		
	}

}
