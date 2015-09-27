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
		allSeries.get(0).getData().add(new XYChart.Data<Number, Number>(stepNum, alivePercent));
		allSeries.get(1).getData().add(new XYChart.Data<Number, Number>(stepNum, deadPercent));
	}
	
}
