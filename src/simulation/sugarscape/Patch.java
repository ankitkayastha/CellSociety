package simulation.sugarscape;

import model.Cell;

public class Patch {
	
	
	private final String amountSugar = "sugarAmount";
	private final String maxCapacity = "maxCapacity";
	private final String sugarGrowBackRate = "sugarGrowBackRate";
	private final String sugarGrowBackInterval = "sugarGrowBackInterval";
	private int counter;
	
	public Patch() {
		counter = 0;
	}
	
	public void doPatch(Cell[] oldGrid, Cell[] newGrid) {
		for (int i = 0; i < oldGrid.length; i++) {
			updateSugar(oldGrid, newGrid, i);
		}
		counter++;
		
	}
	
	
	public void updateSugar(Cell[] oldGrid, Cell[] newGrid, int index) {
		if (counter % oldGrid[index].getChars().get(sugarGrowBackInterval) == 0) {
			int initSugar = oldGrid[index].getChars().get(amountSugar);
			int newSugar = initSugar + oldGrid[index].getChars().get(sugarGrowBackRate);
			int maxSugar = oldGrid[index].getChars().get(maxCapacity);
			if (initSugar + newSugar <= oldGrid[index].getChars().get(maxCapacity)) {
				newGrid[index].getChars().put(amountSugar, newSugar);
			}
			else {
				newGrid[index].getChars().put(amountSugar, maxSugar);
			}
		}
		
	}
}
