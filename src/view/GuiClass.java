package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
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

	private int width = 500;
	private int height = 500;
	private int toolbar = 150;
	private int graphbar = 100;

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
		myScene = new Scene(root, width + toolbar, height+graphbar, Color.ALICEBLUE);
		ButtonHandler buttonHandler = new ButtonHandler(this, root, animation, myResources, height, width);
		buttonHandler.createButtons();
		return myScene;
	}

	public void initializeGrid() {
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
		animation.setRate(1);
		for (Shape thisShape : shapeList) {
			root.getChildren().remove(thisShape);
		}
	}
	
	public void toggleType() {
		myStats.flipType();
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
