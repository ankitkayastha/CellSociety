package view.graph;

import java.util.ArrayList;

import data.Stats;
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
		xAxis.setLowerBound(stepNum-20);
		xAxis.setUpperBound(stepNum);
		simGraph.update(cells, myStats, stepNum, allSeries);
		if (myStats.getGlobalChars().get("sim") == 1) {
			int total = cells.length;
			int empty = 0;
			int tree = 0;
			int burning = 0;
			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getChars().get("fire") == 0)
					empty++;
				else if (cells[i].getChars().get("fire") == 1)
					tree++;
				else
					burning++;
			}
			int emptyPercent = empty*100/total;
			int treePercent = tree*100/total;
			int burningPercent = burning*100/total;
			XYChart.Series<Number, Number> thisSeries = allSeries.get(0);
			thisSeries.getData().remove(0);
			thisSeries.getData().add(new XYChart.Data<Number, Number>(stepNum, emptyPercent));
			XYChart.Series<Number, Number> thisSeries2 = allSeries.get(1);
			thisSeries2.getData().remove(0);
			thisSeries2.getData().add(new XYChart.Data<Number, Number>(stepNum, treePercent));
			XYChart.Series<Number, Number> thisSeries3 = allSeries.get(2);
			thisSeries3.getData().remove(0);
			thisSeries3.getData().add(new XYChart.Data<Number, Number>(stepNum, burningPercent));
		}
		
		else if (myStats.getGlobalChars().get("sim") == 2) {
			int total = cells.length;
			int empty = 0;
			int agentOne = 0;
			int agentTwo = 0;
			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getChars().get("agent") == 0)
					empty++;
				else if (cells[i].getChars().get("agent") == 1)
					agentOne++;
				else
					agentTwo++;
			}
			int emptyPercent = empty*100/total;
			int agentOnePercent = agentOne*100/total;
			int agentTwoPercent = agentTwo*100/total;
			XYChart.Series<Number, Number> thisSeries = allSeries.get(0);
			thisSeries.getData().remove(0);
			thisSeries.getData().add(new XYChart.Data<Number, Number>(stepNum, emptyPercent));
			XYChart.Series<Number, Number> thisSeries2 = allSeries.get(1);
			thisSeries2.getData().remove(0);
			thisSeries2.getData().add(new XYChart.Data<Number, Number>(stepNum, agentOnePercent));
			XYChart.Series<Number, Number> thisSeries3 = allSeries.get(2);
			thisSeries3.getData().remove(0);
			thisSeries3.getData().add(new XYChart.Data<Number, Number>(stepNum, agentTwoPercent));
			xAxis.setLowerBound(stepNum-20);
			xAxis.setUpperBound(stepNum);
			System.out.println("Emtpy Percent is" + emptyPercent);
			System.out.println("AgentOne Percent is" + agentOnePercent);
			System.out.println("AgentTwo Percent is" + agentTwoPercent);
		}
		
		
		else if (myStats.getGlobalChars().get("sim") == 3) {
			int total = cells.length;
			int fish = 0;
			int shark = 0;
			int kelp = 0;
			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getChars().get("animal") == 0) //kelp
					kelp++;
				else if (cells[i].getChars().get("animal") == 1)
					fish++;
				else
					shark++;
			}
			int kelpPercent = kelp*100/total;
			int fishPercent = fish*100/total;
			int sharkPercent = shark*100/total;
			XYChart.Series<Number, Number> thisSeries = allSeries.get(0);
			thisSeries.getData().remove(0);
			thisSeries.getData().add(new XYChart.Data<Number, Number>(stepNum, kelpPercent));
			XYChart.Series<Number, Number> thisSeries2 = allSeries.get(1);
			thisSeries2.getData().remove(0);
			thisSeries2.getData().add(new XYChart.Data<Number, Number>(stepNum, fishPercent));
			XYChart.Series<Number, Number> thisSeries3 = allSeries.get(2);
			thisSeries3.getData().remove(0);
			thisSeries3.getData().add(new XYChart.Data<Number, Number>(stepNum, sharkPercent));
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
		graph.setMaxHeight(height);
		graph.setMaxWidth(width);
		graph.setLayoutY(top);
		gf = new GraphFactory (graph, allSeries, myStats);
		gf.initializeSeries();
		simGraph = gf.generateGraphTemplate();
		return graph;
	}
}
