package simulation.spreading_fire;

import java.util.*;
import javafx.scene.paint.Color;
import model.Cell;

public class SpreadingFireCell extends Cell {
	private final int EMPTY = 0; // empty ground or burnt tree
	private final int TREE = 1; // non burning tree
	private final int BURNING = 2; // burning tree
	private final String characteristicFire = "fire";

	public SpreadingFireCell(Map<String, Integer> characteristicMap) {
		super(characteristicMap);
	}

	@Override
	public void fillColorMap() {
		getColorMap().put(EMPTY, Color.YELLOW);
		getColorMap().put(TREE, Color.GREEN);
		getColorMap().put(BURNING, Color.RED);
	}

	@Override
	public Color getCellColor() {
		return getColorMap().get(getChars().get(characteristicFire));
	}

}
