package shape;

import data.Stats;
import javafx.scene.shape.Shape;

public abstract class CellShape {

	public abstract Shape getCellShape(int index, Stats stats);

}
