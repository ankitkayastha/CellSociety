package simulation.wator;

import java.util.Map;

import data.Stats;
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
	public Color getCellColor(Stats stats) {
		return getColorMap().get(getChars().get(ANIMAL));
	}

	@Override
	public void change(Stats myStats) {
		myCharacteristicMap.put("animal", (myCharacteristicMap.get("animal")+1)%3);		
		myCharacteristicMap.put("life", myStats.getGlobalChars().get("sharkLife"));
		if (myCharacteristicMap.get("animal")==FISH) {
			myCharacteristicMap.put("breed", myStats.getGlobalChars().get("fishBreed"));
		} else {
			myCharacteristicMap.put("breed", myStats.getGlobalChars().get("sharkBreed"));
		}
	}

}
