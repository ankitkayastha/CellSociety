package view.graph;

import java.util.ArrayList;

import data.Stats;
import javafx.scene.chart.XYChart;
import model.Cell;

public class SpreadingFireGraph extends GraphTemplate{

	@Override
	public void update(Cell[] cells, Stats myStats, int stepNum,ArrayList<XYChart.Series<Number, Number>> allSeries) {
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
		allSeries.get(0).getData().add(new XYChart.Data<Number, Number>(stepNum, emptyPercent));
		allSeries.get(1).getData().add(new XYChart.Data<Number, Number>(stepNum, treePercent));
		allSeries.get(2).getData().add(new XYChart.Data<Number, Number>(stepNum, burningPercent));
	}
	
}
