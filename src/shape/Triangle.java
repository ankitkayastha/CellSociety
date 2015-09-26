package shape;

import data.Stats;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Triangle extends CellShape{

	@Override
	public Shape getCellShape(int index, Stats stats) {
		int height = stats.getGlobalChars().get("height");
		int width = stats.getGlobalChars().get("width");
		int rows = stats.getGlobalChars().get("rows");
		int cols = stats.getGlobalChars().get("cols");
		
		int colNum = index % cols;
		int rowNum = index / cols;
		
		double sideLength = 2.0 * width / (cols+1);
		double triangleHeight = sideLength * (Math.sqrt(3) / 2);
		
		double x1 = 0;
		double y1 = 0;
		double x2 = 0;
		double y2 = 0;
		double x3 = 0;
		double y3 = 0;
		
		if (rowNum % 2 == 0 && index % 2 == 0) {
			int shiftIndex = index % cols;
			x1 = (sideLength * (shiftIndex /2))+ sideLength/2;
			y1 = triangleHeight * (rowNum) +triangleHeight;
			x2 = x1-sideLength/2;
			y2 = y1-triangleHeight;
			x3 = x1+sideLength/2;
			y3 = y2;
		}
		else if (rowNum % 2 == 0 && index % 2 ==1) {
			int shiftIndex = (index-1) % cols;
			x2 = (sideLength * ((shiftIndex) /2))+ sideLength/2;
			y2 = triangleHeight * (rowNum) + triangleHeight;
			x1 = x2 + sideLength/2;
			y1 = y2 - triangleHeight;
			x3 = x2 + sideLength;
			y3 = y2;
		}
		else if (rowNum % 2 == 1 && index % 2 == 0) {
			int shiftIndex = index % (cols);
			x1 = (sideLength * (shiftIndex /2))+ sideLength/2;
			y1 = (rowNum-1)*triangleHeight + triangleHeight;
			x2 = x1 - sideLength/2;
			y2 = y1+triangleHeight;
			x3 = x1 + sideLength/2;
			y3 = y2;
		}
		else if (rowNum %2 == 1 && index % 2 == 1) {
			int shiftIndex = (index-1) % cols;
			x2 = (sideLength * ((shiftIndex) /2))+ sideLength/2;
			y2 = (rowNum-1)*triangleHeight + triangleHeight;	
			x1 = x2 + sideLength/2;
			y1 = y2 + triangleHeight;
			x3 = x2 + sideLength;
			y3 = y2;
		}
		
		Polygon thisTriangle = new Polygon();
		thisTriangle.getPoints().addAll(new Double[]{
			    x1, y1,
			    x2, y2,
			    x3, y3 });
		thisTriangle.setStrokeWidth(2);
		thisTriangle.setStroke(Color.LIGHTGRAY);
		return thisTriangle;
	}
}
