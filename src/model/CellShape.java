package model;

import javafx.scene.shape.Shape;

public abstract class CellShape {
	
	public abstract Shape getCellShape(int index, Stats stats);
	
}
