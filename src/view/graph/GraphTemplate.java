package view.graph;

import java.util.ArrayList;

import data.Stats;
import javafx.scene.chart.XYChart;
import model.Cell;

public abstract class GraphTemplate {
	
	public abstract void update(Cell[] cells, Stats myStats, int stepNum,ArrayList<XYChart.Series<Number, Number>> allSeries);

}