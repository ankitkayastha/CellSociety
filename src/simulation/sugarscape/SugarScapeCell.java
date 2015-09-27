package simulation.sugarscape;

import java.util.Map;

import data.Stats;
import javafx.scene.paint.Color;
import model.Cell;

public class SugarScapeCell extends Cell {

	public SugarScapeCell(Map<String, Integer> characteristicMap) {
		super(characteristicMap);
	}

	@Override
	public void fillColorMap() {
	}

	@Override
	public Color getCellColor(Stats stats) {
		return generateColor(stats);
	}

	private Color generateColor(Stats stats) {
		if (getChars().get("hasAnt") == 1) {
			return Color.BLACK;
		} else {
			int sugar = getChars().get("patchSugar");

			int maxSugar = getChars().get("patchSugarCapacity");
			double one = 1 - 0.5 * ((((double) sugar) / maxSugar) % 1);
			if (one < 0 || one > 1) {
				System.out.println("not within 0-1 range for color for ant stuff");
			}
			return new Color(one, 0, 0, 0.4);
		}
	}

	@Override
	public void change(Stats myStats) {
		myCharacteristicMap.put("hasAnt", 1 - myCharacteristicMap.get("hasAnt"));
	}

}
