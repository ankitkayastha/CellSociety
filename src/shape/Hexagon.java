package shape;

import data.Stats;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Hexagon extends CellShape{
	@Override
	public Shape getCellShape(int index, Stats stats) {
		int height = stats.getGlobalChars().get("height");
		int width = stats.getGlobalChars().get("width");
		int rows = stats.getGlobalChars().get("rows");
		int cols = stats.getGlobalChars().get("cols");
		
		int colNum = index % cols;
		int rowNum = index / cols;
		
		double sideLength = width / (cols+(cols+1)*0.5);
		double hexHeight = (sideLength * (Math.sqrt(3) / 2))*2;
		double triangleHeight = (sideLength * (Math.sqrt(3) /2));
		
		double x1 = 0;
		double y1 = 0;
		double x2 = 0;
		double y2 = 0;
		double x3 = 0;
		double y3 = 0;
		double x4 = 0;
		double y4 = 0;
		double x5 = 0;
		double y5 = 0;
		double x6 = 0;
		double y6 = 0;
		
		if (index % 2 == 0) {
			int shiftIndex = index % cols;
			// top shift
			x1 = 0.5*sideLength + 3*sideLength * (shiftIndex /2);
			y1 = rowNum * hexHeight;
			x2 = x1 + sideLength;
			y2 = y1;
			x3 = x2 + 0.5*sideLength;
			y3 = y1 + triangleHeight;
			x4 = x2;
			y4 = y1 + hexHeight;
			x5 = x1;
			y5 = y4;
			x6 = x1 - 0.5*sideLength;
			y6 = y3;
		} else {
			int shiftIndex = (index-1) % cols;
			// top shift
			x1 = 2*sideLength + 3*sideLength * (shiftIndex /2);
			y1 = rowNum * hexHeight + triangleHeight;
			x2 = x1 + sideLength;
			y2 = y1;
			x3 = x2 + 0.5*sideLength;
			y3 = y1 + triangleHeight;
			x4 = x2;
			y4 = y1 + hexHeight;
			x5 = x1;
			y5 = y4;
			x6 = x1 - 0.5*sideLength;
			y6 = y3;
		}
		
		Polygon thisHex = new Polygon();
		thisHex.getPoints().addAll(new Double[]{
			    x1, y1,
			    x2, y2,
			    x3, y3,
			    x4, y4,
			    x5, y5,
			    x6, y6});
		thisHex.setStrokeWidth(2);
		thisHex.setStroke(Color.LIGHTGRAY);
		return thisHex;
	}
}
