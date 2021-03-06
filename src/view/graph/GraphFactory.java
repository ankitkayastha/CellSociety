package view.graph;

import java.util.ArrayList;

import data.Stats;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class GraphFactory {
	private LineChart<Number, Number> graph;
	private ArrayList<XYChart.Series<Number, Number>> allSeries;
	private Stats myStats;

	public GraphFactory(LineChart<Number, Number> graph, ArrayList<XYChart.Series<Number, Number>> allSeries,
			Stats myStats) {
		this.graph = graph;
		this.allSeries = allSeries;
		this.myStats = myStats;
	}

	public void initializeSeries() {
		for (int seriesNum = 0; seriesNum < findSeriesNum(); seriesNum++) {
			XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
			for (int i = 0; i < 20; i++) {
				series.getData().add(new XYChart.Data<Number, Number>(i, 0));
			}
			graph.getData().add(series);
			allSeries.add(series);
		}
	}

	public GraphTemplate generateGraphTemplate(Scene myScene) {
		GraphTemplate thisGF = null;
		if (myStats.getGlobalChars().get("sim") == 0) {
			thisGF = new GameOfLifeGraph(myScene);
		} else if (myStats.getGlobalChars().get("sim") == 1) {
			thisGF = new SpreadingFireGraph(myScene);
		} else if (myStats.getGlobalChars().get("sim") == 2) {
			thisGF = new SegregationGraph(myScene);
		} else if (myStats.getGlobalChars().get("sim") == 3) {
			thisGF = new WaTorGraph(myScene);
		} else if (myStats.getGlobalChars().get("sim") == 4) {
			thisGF = new SugarScapeGraph(myScene);
		}
		return thisGF;
	}

	private int findSeriesNum() {
		if (myStats.getGlobalChars().get("sim") == 0 || myStats.getGlobalChars().get("sim") == 4) {
			return 2;
		} else {
			return 3;
		}
	}
}
