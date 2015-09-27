package simulation;

import data.Stats;
import simulation.game_of_life.GameOfLife;
import simulation.segregation.Segregation;
import simulation.spreading_fire.SpreadingFire;
import simulation.sugarscape.SugarScape;
import simulation.wator.WaTor;

public class SimulationFactory {
	private Stats myStats;
	public SimulationFactory(Stats myStats) {
		this.myStats = myStats;
	}
	
	public Simulation createSim() {
		Simulation sim = null;
		if (myStats.getGlobalChars().get("sim")==0) {
			sim = new GameOfLife(myStats);
		} else if (myStats.getGlobalChars().get("sim")==1) {
			sim= new SpreadingFire(myStats);
		}
		else if (myStats.getGlobalChars().get("sim")==2) {
			sim= new Segregation(myStats);
		}
		else if (myStats.getGlobalChars().get("sim")==3) {
			sim= new WaTor(myStats);
		}
		else if (myStats.getGlobalChars().get("sim") == 4) {
			sim = new SugarScape(myStats);
		}
		return sim;
	}
}
