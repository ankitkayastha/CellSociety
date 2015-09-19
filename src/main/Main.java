package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Grid;
import model.Reader;
import simulation.Simulation;
import simulation.game_of_life.GameOfLife;
import simulation.segregation.Segregation;
import simulation.spreading_fire.SpreadingFire;
import simulation.wator.WaTor;
import view.GuiClass;


public class Main extends Application {

	private static final int FRAMES_PER_SECOND = 60;

	private static int DM = 60000 / FRAMES_PER_SECOND;
	private static double DS = 1.0 / FRAMES_PER_SECOND;

	private static int MILLISECOND_DELAY = DM;
	private static double SECOND_DELAY = DS;
	private Scene scene;
	private Stage stage;
	private Timeline animation;

	private GuiClass gui;
	private Simulation[] sims = new Simulation[4];

	@Override
	public void start(Stage s) {
		stage = s;
		setStage();
	}

	// initializes the stage and the basic Timeline
	private void setStage() {
		gui = new GuiClass();
		stage.setTitle(gui.getTitle());

		scene = gui.init();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> gui.step(SECOND_DELAY));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		animation.pause();

		scene.setOnMouseClicked(e -> click(e.getX(), e.getY()));
	}

	// talks to the GuiClass to see what button was clicked
	private void click(double x, double y) {
		switch (gui.handleMouseInput(x, y)) {
		case 0:
			return;
		case 1:
			animation.play();
			return;
		case 2:
			animation.pause();
			return;
		case 3:
			gui.reset();
			slowDown();
			animation.pause();
			return;
		case 4:
			speedUp();
			return;
		case 5:
			initialize();
		}
	}

	private void initialize() {
		Reader myReader = new Reader();
		Simulation gameOfLife = new GameOfLife(myReader.getGlobalChars());
		sims[0] = gameOfLife;
		Simulation spreadingFire = new SpreadingFire(myReader.getGlobalChars());
		sims[1] = spreadingFire;
		Simulation segregation = new Segregation(myReader.getGlobalChars());
		sims[2] = segregation;
		Simulation waTor = new WaTor(myReader.getGlobalChars());
		sims[3] = waTor;
		Grid myGrid = new Grid(myReader, gui);
		Simulation mySim = sims[myReader.getSimNum()];
		gui.initDisplay(myGrid, myReader, mySim, myReader.getSimNum());
		gui.setStatus(1);
	}

	// Speeds up the framerate
	private void speedUp() {
		if (MILLISECOND_DELAY < DM * 8) {
			MILLISECOND_DELAY *= 2;
			SECOND_DELAY *= 2;
		}
	}

	// returns framerate to default
	private void slowDown() {
		MILLISECOND_DELAY = DM;
		SECOND_DELAY = DS;
	}

	public static void main(String[] args) {
		launch(args);
	}
}