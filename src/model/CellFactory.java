package model;

import java.util.Map;

import data.Stats;
import simulation.game_of_life.GameOfLifeCell;

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
