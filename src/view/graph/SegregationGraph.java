package view.graph;

import java.util.ArrayList;

import data.Stats;
import javafx.scene.chart.XYChart;
import model.Cell;

public class SegregationGraph extends GraphTemplate{

	@Override
	public void update(Cell[] cells, Stats myStats, int stepNum,ArrayList<XYChart.Series<Number, Number>> allSeries) {
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
		allSeries.get(0).getData().add(new XYChart.Data<Number, Number>(stepNum, emptyPercent));
		allSeries.get(1).getData().add(new XYChart.Data<Number, Number>(stepNum, agentOnePercent));
		allSeries.get(2).getData().add(new XYChart.Data<Number, Number>(stepNum, agentTwoPercent));
	}
	
}
