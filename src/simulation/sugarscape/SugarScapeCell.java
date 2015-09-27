package simulation.sugarscape;

import java.util.Map;

import data.Stats;
import javafx.scene.paint.Color;
import model.Cell;

public class SugarScapeCell extends Cell {
	
	private String patchSugar = "amountSugar";
	private String maxPatchSugar = "maxPatchSugar";
	
	public SugarScapeCell(Map<String, Integer> characteristicMap) {
		super(characteristicMap);
	}

	@Override
	public void fillColorMap() {
		
	//	generateColor(getChars().get(patchSugar));
	}

	@Override
	public Color getCellColor(Stats stats) {
		return generateColor(stats);
	}
	
	private Color generateColor(Stats stats) {
		int sugar = getChars().get(patchSugar);

		if (sugar==0) {
			return Color.WHITE;
		}
		int maxSugar = stats.getGlobalChars().get(maxPatchSugar);
		double one = ((double) sugar / maxSugar);        
		return new Color(one, 0, 0, 0);
	}

}
