package view.graph;

import java.util.ArrayList;

import data.Stats;
import javafx.scene.chart.XYChart;
import model.Cell;

public class WaTorGraph extends GraphTemplate{

	@Override
	public void update(Cell[] cells, Stats myStats, int stepNum,ArrayList<XYChart.Series<Number, Number>> allSeries) {
		int total = cells.length;
		int fish = 0;
		int shark = 0;
		int kelp = 0;
		for (int i = 0; i < cells.length; i++) {
			if (cells[i].getChars().get("animal") == 0) 
				kelp++;
			else if (cells[i].getChars().get("animal") == 1)
				fish++;
			else
				shark++;
		}
		int kelpPercent = kelp*100/total;
		int fishPercent = fish*100/total;
		int sharkPercent = shark*100/total;
		allSeries.get(0).getData().add(new XYChart.Data<Number, Number>(stepNum, kelpPercent));
		allSeries.get(1).getData().add(new XYChart.Data<Number, Number>(stepNum, fishPercent));
		allSeries.get(2).getData().add(new XYChart.Data<Number, Number>(stepNum, sharkPercent));
	}
}
