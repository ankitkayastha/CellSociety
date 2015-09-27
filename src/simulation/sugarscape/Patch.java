package simulation.sugarscape;

import model.Cell;

public class Patch {

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
		if (counter % oldGrid[index].getChars().get("patchSugarGrowBackInterval") == 0) {
			int initSugar = oldGrid[index].getChars().get("patchSugar");
			int newSugar = initSugar + oldGrid[index].getChars().get("patchSugarGrowBackRate");
			int maxSugar = oldGrid[index].getChars().get("patchSugarCapacity");
			if (initSugar + newSugar <= maxSugar) {
				newGrid[index].getChars().put("patchSugar", newSugar);
			} else {
				newGrid[index].getChars().put("patchSugar", maxSugar);
			}
		}
	}
}
