package view.graph;

import java.util.ArrayList;

import data.Stats;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.Cell;

public class GraphHandler {
	private LineChart<Number, Number> graph;
	private ArrayList<XYChart.Series<Number, Number>> allSeries;
	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private GraphFactory gf;
	private Stats myStats;
	private GraphTemplate simGraph;

	public GraphHandler(Stats myStats) {
		allSeries = new ArrayList<XYChart.Series<Number, Number>>();
		this.myStats = myStats;
	}

	public void updateGraph(Cell[] cells, Stats myStats, int stepNum) {
		xAxis.setLowerBound(stepNum - 20);
		xAxis.setUpperBound(stepNum);
		simGraph.update(cells, myStats, stepNum, allSeries);
		for (int i = 0; i < allSeries.size(); i++) {
			allSeries.get(i).getData().remove(0);
		}
	}

	public LineChart<Number, Number> createGraph(int top, int height, int width, Scene myScene) {
		xAxis = new NumberAxis(0, 20, 5);
		yAxis = new NumberAxis(0, 100, 25);
		xAxis.setAnimated(false);
		yAxis.setAnimated(false);
		xAxis.setAutoRanging(false);
		yAxis.setAutoRanging(false);
		xAxis.setLabel("Step");
		yAxis.setLabel("%");
		graph = new LineChart<Number, Number>(xAxis, yAxis);
		graph.setCreateSymbols(false);
		graph.setAnimated(false);
		graph.setTitle("Populations");
		graph.setMaxHeight(height);
		graph.setMaxWidth(width);
		graph.setLayoutY(top);
		gf = new GraphFactory(graph, allSeries, myStats);
		gf.initializeSeries();
		simGraph = gf.generateGraphTemplate(myScene);
		return graph;
	}
}
