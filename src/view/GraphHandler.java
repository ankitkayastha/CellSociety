package view;

import java.util.ArrayList;

import data.Stats;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import model.Cell;

public class GraphHandler {
	private LineChart<Number, Number> graph;
	private ArrayList<XYChart.Series> allSeries;
	private NumberAxis xAxis;
	private NumberAxis yAxis;
	
	public GraphHandler() {
		allSeries = new ArrayList<XYChart.Series>();
	}

	public void updateGraph(Cell[] cells, Stats myStats, int stepNum) {
		if (myStats.getGlobalChars().get("sim")==0){
			int total = cells.length;
			int dead = 0;
			int alive = 0;
			for (int i=0; i<cells.length; i++) {
				if (cells[i].getChars().get("life")==0) {
					dead++;
				} else {
					alive++;
				}
			}
			int alivePercent = alive*100/total;
			int deadPercent = dead*100/total;
			XYChart.Series thisSeries = allSeries.get(0);
			thisSeries.getData().remove(0);
			thisSeries.getData().add(new XYChart.Data(stepNum, alivePercent));
			XYChart.Series thisSeries2 = allSeries.get(1);
			thisSeries2.getData().remove(0);
			thisSeries2.getData().add(new XYChart.Data(stepNum, deadPercent));
			xAxis.setLowerBound(stepNum-20);
			xAxis.setUpperBound(stepNum);
		}
	}

	public LineChart<Number, Number> createGraph(int top, int height, int width) {
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		xAxis.setAnimated(false);
		yAxis.setAnimated(false);
		xAxis.setAutoRanging(false);
		xAxis.setLowerBound(0);
		xAxis.setUpperBound(20);
		xAxis.setLabel("Step");
		yAxis.setLabel("%");
		graph = new LineChart<Number, Number>(xAxis, yAxis);
		graph.setCreateSymbols(false);
		graph.setAnimated(false);
		graph.setTitle("Populations");

		for (int seriesNum = 0; seriesNum < 2; seriesNum++) {
			XYChart.Series<Number, Number> series = new XYChart.Series();
			for (int i = 0; i < 20; i++) {
				series.getData().add(new XYChart.Data(i, 0));
			}
			graph.getData().add(series);
			allSeries.add(series);
			/*for (Data<Number, Number> data : series.getData()) {
	            Node node = data.getNode() ;
	            node.setCursor(Cursor.HAND);
	            node.setOnMouseDragged(e -> {
	                Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
	                double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
	                double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
	                Number x = xAxis.getValueForDisplay(xAxisLoc);
	                Number y = yAxis.getValueForDisplay(yAxisLoc);
	                data.setXValue(x);
	                data.setYValue(y);
	            });
	        }*/
		}

		graph.setMaxHeight(height);
		graph.setMaxWidth(width);
		graph.setLayoutY(top);
		return graph;
	}
}
