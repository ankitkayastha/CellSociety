package simulation.sugarscape;

import java.util.Map;

import javafx.scene.paint.Color;
import model.Cell;

public class SugarScapeCell extends Cell {
	
	private String string = "amountSugar";
	
	public SugarScapeCell(Map<String, Integer> characteristicMap) {
		super(characteristicMap);
	}

	@Override
	public void fillColorMap() {
		generateColor(getChars().get(string));
	}

	@Override
	public Color getCellColor() {
		return generateColor(getChars().get(string));
	}
	
	private Color generateColor(int sugarAmount) {
		if (sugarAmount==0) {
			return Color.WHITE;
		}
		double one = (13.49289/(double) sugarAmount) %1;        
		double two = (one * 13.429) %1;
		double three = (two * 9.232) %1;
		double four = (three * 12.042) %1;
		return new Color(one, two, three, four);
	}

}
