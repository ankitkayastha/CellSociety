package model;
import java.util.*;

public class Cell {
	private Map<String, Integer> myCharacteristicMap;

	public Cell(Map<String, Integer> map) {
		myCharacteristicMap = map;
	}

	public Map<String, Integer> getChars() {
		return myCharacteristicMap;
	}
}
