package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import model.Grid;
import data.Reader;
import shape.ShapeFactory;
import data.Stats;
import simulation.Simulation;
import simulation.SimulationFactory;
import view.graph.GraphHandler;

public class GuiClass {

	private Scene myScene;
	private Group root;
	private Grid myGrid;

	private Simulation thisSim;
	private Stats myStats;
	private List<Shape> shapeList;
	private Map<Shape, Integer> shapeMap;
	private GraphHandler gh;
	private LineChart<Number, Number> lineChart;
	private int stepNum;

	private int width = 500;
	private int height = 500;
	private int toolbar = 150;
	private int graphbar = 150;
	private int graphbarSmall = 100;

	private ResourceBundle myResources;

	public GuiClass(ResourceBundle myResources) {
		this.myResources = myResources;
		stepNum = 19;
	}

	// step function for Timeline
	public void step() {
		stepNum++;
		thisSim.update(myGrid, myStats);
		gh.updateGraph(myGrid.getGrid(), myStats, stepNum);
		display();
	}

	// sets up the basic Scene
	public Scene init(Stage stage, Timeline animation) {
		root = new Group();
		myScene = new Scene(root, width + toolbar, height + graphbar, Color.ALICEBLUE);

		ButtonHandler buttonHandler = new ButtonHandler(this, root, animation, myResources, height, width);
		buttonHandler.createButtons();
		TextField parameter = buttonHandler.createParam(thisSim);
		parameter.setOnAction(event -> {
			thisSim.setParam(Double.parseDouble(parameter.getText()));
		});
		root.getChildren().add(parameter);
		return myScene;
	}

	public void initializeGrid() {
		Reader myReader;
		try {
			myReader = new Reader();
			myStats = new Stats(myReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		myStats.putDimensions(height, width);
		SimulationFactory simFactory = new SimulationFactory(myStats);
		myGrid = new Grid(myStats);
		thisSim = simFactory.createSim();
		
		gh = new GraphHandler(myStats);
		lineChart = gh.createGraph(height, graphbarSmall, width, myScene);
		root.getChildren().add(lineChart);
		
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
		root.getChildren().remove(lineChart);
	}

	public void toggleType() {
		myStats.flipType();
	}
	
	public void click(double x, double y) {
		for (int i=0; i<shapeList.size(); i++) {
			Shape thisShape = shapeList.get(i);
			if (thisShape.contains(x,y)) {
				int index = shapeMap.get(thisShape);
				myGrid.changeCell(index, myStats);
				display();
			}
		}
	}

	public void initDisplay() {
		shapeList = new ArrayList<Shape>();
		shapeMap = new HashMap<Shape, Integer>();
		ShapeFactory myShapeFactory = new ShapeFactory(myStats);
		for (int i = 0; i < myStats.getSize(); i++) {
			Shape newShape = myShapeFactory.getShape(i);
			myGrid.getCell(i).fillColorMap();
			newShape.setFill(myGrid.getCell(i).getCellColor(myStats));
			root.getChildren().add(newShape);
			shapeList.add(newShape);
			shapeMap.put(newShape, i);
		}
		System.out.println("Display Initialized");
	}

	public void display() {
		for (int i = 0; i < myStats.getSize(); i++) {
			Shape currentShape = shapeList.get(i);
			currentShape.setFill(myGrid.getCell(i).getCellColor(myStats));
		}
	}
}
