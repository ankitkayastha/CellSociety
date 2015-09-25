package simulation.segregation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import data.Stats;
import javafx.scene.paint.Color;
import model.Cell;
import model.Grid;
import model.NeighborFactory;
import simulation.Simulation;
import simulation.spreading_fire.SpreadingFireCell;

public class Segregation extends Simulation {
	private final int EMPTY = 0; 
	private final int simNumber;
	private double threshold;
	private String thresh = "threshold";
	private Color[] myColors;
	private Random myRandom;
	private String agents = "agents";
	private final String agent = "agent";
	private SpreadingFireCell fireCellManager;
	
	public Segregation(Stats stats) {
		super(stats);
		if (stats.getGlobalChars().keySet().contains(thresh)){
			threshold = stats.getGlobalChars().get(thresh);
		}
		myRandom = new Random(1234);
		int numAgents = 0;
		if (stats.getGlobalChars().keySet().contains(agents)){
			numAgents = stats.getGlobalChars().get(agents);
		}
		myColors = new Color[numAgents + 1];
		addColors(numAgents);
		fireCellManager = new SpreadingFireCell
	}
	
	private void addColors(int numAgents) {
		myColors[0] = Color.WHITE;
		for (int i = 1; i <= numAgents; i++) {
			double r = myRandom.nextDouble();
			double g = myRandom.nextDouble();
			double b = myRandom.nextDouble();
			double o = myRandom.nextDouble();
			myColors[i] = new Color(r, g, b, o);
		}
	}

	@Override
	public void update(Grid myGrid, Stats myStats) {
		Cell[] oldGrid = super.copyGrid(myGrid, myStats);
		Cell[] myGridGrid = myGrid.getGrid();
		
		//moves all dissatisfied cells
		for (int i = 0; i < myStats.getSize(); i++) {
			if ((oldGrid[i].getChars().get(agent)!=EMPTY) & !isSatisfied(oldGrid, i, myStats, threshold)) {
				if (hasEmpty(myGridGrid)) {
					move(myGridGrid, i);
				}
			}
		}
	}


	
	}
