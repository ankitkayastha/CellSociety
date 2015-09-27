package model;

import java.util.Map;

import data.Stats;
import simulation.game_of_life.GameOfLifeCell;
import simulation.segregation.SegregationCell;
import simulation.spreading_fire.SpreadingFireCell;
import simulation.sugarscape.SugarScapeCell;
import simulation.wator.WaTorCell;

public class CellFactory {
	Stats myStats;
	public CellFactory(Stats myStats) {
		this.myStats = myStats;
	}
	
	public Cell createCell(Map<String, Integer> charMap) {
		if (myStats.getGlobalChars().get("sim")==0) {
			return new GameOfLifeCell(charMap);
		}
		else if (myStats.getGlobalChars().get("sim")==1) {
			return new SpreadingFireCell(charMap);
		}
		else if (myStats.getGlobalChars().get("sim")==2) {
			return new SegregationCell(charMap);
		}
		else if (myStats.getGlobalChars().get("sim")==3) {
			 return new WaTorCell(charMap);
		}
		else if (myStats.getGlobalChars().get("sim") == 4) {
			return new SugarScapeCell(charMap);
		}
		return null;
	}
}
