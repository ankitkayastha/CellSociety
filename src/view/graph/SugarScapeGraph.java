package view.graph;

import java.util.ArrayList;

import data.Stats;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import model.Cell;

public class SugarScapeGraph extends GraphTemplate {

	public SugarScapeGraph(Scene myScene) {
		super(myScene);
		myScene.getStylesheets().add("/css/sugar_scape_graph.css");
	}

	@Override
	public void update(Cell[] cells, Stats myStats, int stepNum, ArrayList<XYChart.Series<Number, Number>> allSeries) {
		int total = cells.length;
		int ant = 0;
		int patch = 0;
		for (int i = 0; i < cells.length; i++) {
			if (cells[i].getChars().get("hasAnt") == 0) {
				patch++;
			} else {
				ant++;
			}
		}
		int antPercent = ant * 100 / total;
		int patchPercent = patch * 100 / total;
		allSeries.get(0).getData().add(new XYChart.Data<Number, Number>(stepNum, antPercent));
		allSeries.get(1).getData().add(new XYChart.Data<Number, Number>(stepNum, patchPercent));
	}

}
