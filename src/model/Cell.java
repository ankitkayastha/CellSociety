package model;
import java.util.*;

import javafx.scene.paint.Color;

public abstract class Cell {
	protected Map<String, Integer> myCharacteristicMap;
	protected Map<Integer, Color> myColorMap;
	
	public Cell(Map<String, Integer> characteristicMap) {
		myCharacteristicMap = characteristicMap;
		myColorMap = new HashMap<Integer, Color>();
	}
	
	public Map<Integer, Color> getColorMap() {
		return myColorMap;
	}
	
	public Map<String, Integer> getChars() {
		return myCharacteristicMap;
	}
	
	public abstract void fillColorMap();

	public abstract Color getCellColor();

	
}
