package view.graph;

import java.util.ArrayList;

import data.Stats;
import javafx.scene.chart.XYChart;
import model.Cell;

public class GameOfLifeGraph extends GraphTemplate{

	@Override
	public void update(Cell[] cells, Stats myStats, int stepNum,ArrayList<XYChart.Series<Number, Number>> allSeries) {
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
		XYChart.Series<Number, Number> thisSeries = allSeries.get(0);
		thisSeries.getData().remove(0);
		thisSeries.getData().add(new XYChart.Data<Number, Number>(stepNum, alivePercent));
		XYChart.Series<Number, Number> thisSeries2 = allSeries.get(1);
		thisSeries2.getData().remove(0);
		thisSeries2.getData().add(new XYChart.Data<Number, Number>(stepNum, deadPercent));
	}
	
}
