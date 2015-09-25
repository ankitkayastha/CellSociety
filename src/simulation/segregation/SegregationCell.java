package simulation.segregation;

import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import model.Cell;

public class SegregationCell extends Cell {
	private Random myRandom;
	private final String agent = "agent";
	
	public SegregationCell(Map<String, Integer> characteristicMap) {
		super(characteristicMap);
	}

	@Override
	public void fillColorMap() {
		if (!(getColorMap().containsKey(getChars().get(agent))))
			getColorMap().put(getChars().get(agent), generateColor());
	}
	
	@Override
	public Color getCellColor() {
		return getColorMap().get(getChars().get(agent));
	}
	
	public Color generateColor() {
		double[] arr = new double[4];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = myRandom.nextDouble();
		}
		return new Color(arr[0], arr[1], arr[2], arr[3]);
	}

}
