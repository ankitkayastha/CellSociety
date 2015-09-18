import java.util.*;

import javafx.scene.paint.Color;
public class Cell {
	private Map<String, Integer> myCharacteristicMap;
	// TODO change public
	
	public Cell(Map<String, Integer> map) {
		myCharacteristicMap = map;
	}
	
	public Map<String, Integer> getChars() {
		return myCharacteristicMap;
	}
	
	/*public void updateCell(Map<String, Integer> map) {
		myCharacteristicMap = map;
	}*/

}

