package model;

import javafx.scene.shape.Shape;

public class ShapeFactory {
	Stats myStats;
	public ShapeFactory(Stats myStats) {
		this.myStats = myStats;
	}
	
	public Shape getShape(int index) {
		CellShape thisCellShape = new CellShape();
		if (myStats.getGlobalChars().get("shape")==4) {
			return thisCellShape.getCellShape(index, myStats);
		}
	}
}
