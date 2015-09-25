package simulation.game_of_life;

import java.util.Map;

import javafx.scene.paint.Color;
import model.Cell;

public class GameOfLifeCell extends Cell {
	private int DEAD = 0;
	private int ALIVE = 1;
	
	public GameOfLifeCell(Map<String, Integer> characteristicMap) {
		super(characteristicMap);
	}
	
	@Override
	public void fillColorMap() {
		getColorMap().put(DEAD, Color.BLACK);
		getColorMap().put(ALIVE, Color.GREEN);
	}
	@Override
	public Color getCellColor() {
		return getColorMap().get(getChars().get("life"));
	}


}
