package model;
import java.util.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import simulation.Simulation;

public abstract class Cell {
	private Map<String, Integer> myCharacteristicMap;

	public Cell(Map<String, Integer> map) {
		myCharacteristicMap = map;
	}

	public Map<String, Integer> getChars() {
		return myCharacteristicMap;
	}
	
	public abstract Color getCellColor(Simulation mySim);
	
	public abstract Shape getCellShape();
}
