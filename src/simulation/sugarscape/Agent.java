package simulation.sugarscape;

import model.Cell;
import model.NeighborFactory;

import java.util.*;

import data.Stats;
public class Agent {
	
	
	private String vision = "vision";
	private String sugar = "sugar";
	private String sugarMetabolism = "sugarMetabolism";
	private final String amountSugar = "sugarAmount";
	private final String cols = "cols";
	
	public void doAgent(Cell[] oldGrid, Cell[] newGrid) {
		
		
	}
	
	public Cell findShortestDistanceCell(List<Cell> cellList, int index, Stats stats) {
		int numCols = stats.getGlobalChars().get(cols);
		int rowLocation = index / numCols;
		int colLocation = index % numCols;
		Cell cellToReturn;
		if (cellList.size() == 1)
			return cellList.get(0);
		for (int i = 0; i < cellList.size(); i++) {
			Cell cellCompare = cellList.get(i);
			int rowCompare = cellCompare.getChars().get
			
			
		}
			
		
		return cellToReturn;
		
	}
	
	public List<Cell> generateNeighbors(Cell[] oldGrid, int index, NeighborFactory factory, int vision, Stats stats) {
		List<Cell> neighbors = factory.getNeighbors(oldGrid, index);
		List<Integer> sugarVals = new ArrayList<Integer>();
		for (int i = 0; i < neighbors.size(); i++) {
			sugarVals.add(neighbors.get(i).getChars().get(amountSugar));
		}
		Collections.sort(sugarVals);
		int max = Collections.max(sugarVals);
		List<Integer> maxVals = new ArrayList<Integer>();
		for (int i = 0; i < sugarVals.size(); i++) {
			if (sugarVals.get(i) == max)
				maxVals.add(sugarVals.get(i));
		}
		List<Cell> maxNeighbors = new ArrayList<Cell>();
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getChars().get(amountSugar) == max)
				maxNeighbors.add(neighbors.get(i));
		}
		
		//if (vision > 1) {
			
		//}
		
		
		return neighbors;
	}
}
