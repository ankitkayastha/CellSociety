package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import model.Grid;
import data.Reader;
import shape.ShapeFactory;
import data.Stats;
import simulation.Simulation;
import simulation.SimulationFactory;

public class GuiClass {

	private Scene myScene;
	private Group root;
	private Grid myGrid;

	private Simulation thisSim;
	private Stats myStats;
	private List<Shape> shapeList;

	private double rate = 1;

	private int width = 500;
	private int height = 500;
	private int toolbar = 40;

	private ResourceBundle myResources;

	public GuiClass(ResourceBundle myResources) {
		this.myResources = myResources;
	}

	// step function for Timeline
	public void step() {
		thisSim.update(myGrid, myStats);
		display();
	}

	// sets up the basic Scene
	public Scene init(Stage stage, Timeline animation) {

		root = new Group();
		myScene = new Scene(root, width, height+toolbar, Color.ALICEBLUE);

		Button playButton = new Button(myResources.getString("PlayButtonText"));
		playButton.setDisable(true);
		Button pauseButton = new Button(myResources.getString("PauseButtonText"));
		pauseButton.setDisable(true);
		Button stopButton = new Button(myResources.getString("StopButtonText"));
		stopButton.setDisable(true);
		Button ffButton = new Button(myResources.getString("FFButtonText"));
		ffButton.setDisable(true);
		Button sdButton = new Button(myResources.getString("SDButtonText"));
		sdButton.setDisable(true);
		Button stepButton = new Button(myResources.getString("StepButtonText"));
		stepButton.setDisable(true);
		Button openButton = new Button(myResources.getString("OpenButtonText"));

		playButton.setOnAction((event) -> {
			animation.play();
			pauseButton.setDisable(false);
			stopButton.setDisable(false);
			ffButton.setDisable(false);
			sdButton.setDisable(false);
			stepButton.setDisable(true);
			openButton.setDisable(true);
		});
		pauseButton.setOnAction((event) -> {
			animation.pause();
			playButton.setDisable(false);
			stopButton.setDisable(false);
			ffButton.setDisable(true);
			sdButton.setDisable(true);
			stepButton.setDisable(false);
			openButton.setDisable(true);
		});
		stopButton.setOnAction((event) -> {
			reset(animation);
			playButton.setDisable(true);
			pauseButton.setDisable(true);
			ffButton.setDisable(true);
			sdButton.setDisable(true);
			stepButton.setDisable(true);
			openButton.setDisable(false);
		});
		ffButton.setOnAction((event) -> {
			speedUp(animation, ffButton);
			playButton.setDisable(true);
			pauseButton.setDisable(false);
			stopButton.setDisable(false);
			sdButton.setDisable(false);
			stepButton.setDisable(true);
			openButton.setDisable(true);
		});
		sdButton.setOnAction((event) -> {
			slowDown(animation, sdButton);
			playButton.setDisable(true);
			pauseButton.setDisable(false);
			stopButton.setDisable(false);
			ffButton.setDisable(false);
			stepButton.setDisable(true);
			openButton.setDisable(true);
		});
		stepButton.setOnAction((event) -> {
			step();
			playButton.setDisable(false);
			pauseButton.setDisable(true);
			stopButton.setDisable(true);
			ffButton.setDisable(true);
			sdButton.setDisable(true);
			openButton.setDisable(true);
		});
		openButton.setOnAction((event) -> {
			initializeGrid();
			playButton.setDisable(false);
			pauseButton.setDisable(true);
			stopButton.setDisable(false);
			ffButton.setDisable(true);
			sdButton.setDisable(true);
			stepButton.setDisable(false);
			openButton.setDisable(true);
		});

		// ffButton.setStyle("-fx-font-size: 15pt;");

		playButton.setMaxSize(60, 30);
		pauseButton.setMaxSize(60, 30);
		stopButton.setMaxSize(60, 30);
		ffButton.setMaxSize(60, 30);
		sdButton.setMaxSize(60, 30);
		stepButton.setMaxSize(60, 30);
		openButton.setMaxSize(60, 30);

		TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
		tileButtons.setPrefColumns(7);
		tileButtons.setPadding(new Insets(7.5, 0, 7.5, 7.5));
		tileButtons.setHgap(20.0);
		tileButtons.getChildren().addAll(playButton, pauseButton, stopButton, ffButton, sdButton, stepButton,
				openButton);
		tileButtons.setLayoutX(0);
		tileButtons.setLayoutY(height);

		root.getChildren().add(tileButtons);
		return myScene;
	}

	private void initializeGrid() {
		Reader myReader;
		try {
			myReader = new Reader();
			myStats = new Stats(myReader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myStats.putDimensions(height, width);
		SimulationFactory simFactory = new SimulationFactory(myStats);
		myGrid = new Grid(myStats);
		thisSim = simFactory.createSim();
		System.out.println(myStats.getGlobalChars().toString());
		System.out.printf("About to initialize Sim: %d\n", myStats.getGlobalChars().get("sim"));
		initDisplay();
	}

	public void reset(Timeline animation) {
		animation.stop();
		rate = 1;
		animation.setRate(rate);
		for (Shape thisShape : shapeList) {
			root.getChildren().remove(thisShape);
		}
	}

	private void speedUp(Timeline animation, Button ffButton) {
		if (rate <= 32){
			rate *= 2;
		}
		ffButton.setDisable(!(rate <= 32));
		animation.setRate(rate);
		System.out.printf("Rate: %f\n", rate);
	}

	private void slowDown(Timeline animation, Button sdButton) {
		if (rate >= 1 / 32.0)
			rate /= 2;
		sdButton.setDisable(!(rate >= 1 / 32.0));
		animation.setRate(rate);
		System.out.printf("Rate: %f\n", rate);
	}

	public void initDisplay() {
		shapeList = new ArrayList<Shape>();
		ShapeFactory myShapeFactory = new ShapeFactory(myStats);
		for (int i = 0; i < myStats.getSize(); i++) {
			Shape newShape = myShapeFactory.getShape(i);
			myGrid.getCell(i).fillColorMap();
			newShape.setFill(myGrid.getCell(i).getCellColor());
			root.getChildren().add(newShape);
			shapeList.add(newShape);
		}
		System.out.println("Display Initialized");
	}

	public void display() {
		for (int i = 0; i < myStats.getSize(); i++) {
			Shape currentShape = shapeList.get(i);
			currentShape.setFill(myGrid.getCell(i).getCellColor());
		}
	}
}
