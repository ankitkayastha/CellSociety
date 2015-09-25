package model;

import java.util.Map;

public class CellFactory {
	Stats myStats;
	public CellFactory(Stats myStats) {
		this.myStats = myStats;
	}
	
	public Cell createCell(Map<String, Integer> charMap) {
		if (myStats.getGlobalChars().get("sim")==0) {
			return new GameOfLifeCell(charMap);
		}
		return null;
	}
}
