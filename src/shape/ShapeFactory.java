package shape;

import data.Stats;
import javafx.scene.shape.Shape;

public class ShapeFactory {
	Stats myStats;
	public ShapeFactory(Stats myStats) {
		this.myStats = myStats;
	}
	
	public Shape getShape(int index) {
		if (myStats.getGlobalChars().get("shape")==4) {
			CellShape thisCellShape = new Square();
			return thisCellShape.getCellShape(index, myStats);
		}
		else if (myStats.getGlobalChars().get("shape")==3) {
			CellShape thisCellShape = new Triangle();
			return thisCellShape.getCellShape(index,  myStats);
		}
		return null;
	}
}
