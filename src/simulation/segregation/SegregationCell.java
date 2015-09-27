package simulation.segregation;

import java.util.Map;

import data.Stats;
import javafx.scene.paint.Color;
import model.Cell;

public class SegregationCell extends Cell {
	private final String agent = "agent";
	
	public SegregationCell(Map<String, Integer> characteristicMap) {
		super(characteristicMap);
	}

	@Override
	public void fillColorMap() {
		generateColor(getChars().get(agent));
	}
	
	@Override
	public Color getCellColor(Stats stats) {
		return generateColor(getChars().get(agent));
	}
	
	
	private Color generateColor(int agent) {
		if (agent==0) {
			return Color.WHITE;
		}
		double one = (13.49289/(double) agent) %1;        
		double two = (one * 13.429) %1;
		double three = (two * 9.232) %1;
		double four = (three * 12.042) %1;
		return new Color(one, two, three, four);
	}

	@Override
	public void change(Stats myStats) {
		int cellTypes = myStats.getGlobalChars().get("agents")+1;
		myCharacteristicMap.put("agent", (myCharacteristicMap.get("agent")+1)%cellTypes);			
	}

}
