package simulation.wator;

import java.util.Map;

import javafx.scene.paint.Color;
import model.Cell;

public class WaTorCell extends Cell {
	private final int KELP = 0;
	private final int FISH = 1;
	private final int SHARK = 2;
	private final String ANIMAL = "animal";
	
	public WaTorCell(Map<String, Integer> characteristicMap) {
		super(characteristicMap);
	}

	@Override
	public void fillColorMap() {
		getColorMap().put(KELP, Color.GREEN);
		getColorMap().put(FISH, Color.BLUE);
		getColorMap().put(SHARK, Color.YELLOW);
	}

	@Override
	public Color getCellColor() {
		return getColorMap().get(getChars().get(ANIMAL));
	}

}
