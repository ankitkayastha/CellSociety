package shape;

import data.Stats;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Square extends CellShape {

	@Override
	public Shape getCellShape(int index, Stats stats) {
		int height = stats.getGlobalChars().get("height");
		int width = stats.getGlobalChars().get("width");
		int rows = stats.getGlobalChars().get("rows");
		int cols = stats.getGlobalChars().get("cols");
		double ySize = (double) height / rows;
		double xSize = (double) width / cols;
		int colNum = index % cols;
		int rowNum = index / cols;
		Rectangle thisRect = new Rectangle(colNum * xSize, rowNum * ySize, xSize, ySize);
		thisRect.setStrokeWidth(1);
		thisRect.setStroke(Color.LIGHTGRAY);
		return thisRect;
	}

}
