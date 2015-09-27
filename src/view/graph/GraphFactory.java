package view.graph;

import java.util.ArrayList;

import data.Stats;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class GraphFactory {
	private LineChart<Number, Number> graph;
	private ArrayList<XYChart.Series<Number, Number>> allSeries;
	private Stats myStats;


	public GraphFactory(LineChart<Number, Number> graph, ArrayList<XYChart.Series<Number, Number>> allSeries, Stats myStats) {
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
	
	public GraphTemplate generateGraphTemplate() {
		GraphTemplate thisGF = null;
		if (myStats.getGlobalChars().get("sim")==0) {
			thisGF = new GameOfLifeGraph();
		}
		else if (myStats.getGlobalChars().get("sim")==1) {
			thisGF = new SpreadingFireGraph();
		}
		else if (myStats.getGlobalChars().get("sim")==2) {
			thisGF = new SegregationGraph();
		}
		else if (myStats.getGlobalChars().get("sim")==3) {
			thisGF = new WaTorGraph();
		}
		return thisGF;
	}
	
	private int findSeriesNum() {
		if (myStats.getGlobalChars().get("sim")==0) {
			return 2;
		} else if (myStats.getGlobalChars().get("sim")==1 || myStats.getGlobalChars().get("sim")==3) {
			return 3;
		} else {
			return myStats.getGlobalChars().get("agents")+1;
		}
	}

}
