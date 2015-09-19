package view;
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
import model.Reader;
import simulation.Simulation;
import simulation.game_of_life.GameOfLife;
import simulation.segregation.Segregation;
import simulation.spreading_fire.SpreadingFire;
import simulation.wator.WaTor;

public class GuiClass {
	
	private Scene myScene;
	private Group root;
	private Grid myGrid;
	
	private Simulation thisSim;
	private Reader myReader;
	private List<Shape> shapeList;
	private Simulation[] sims = new Simulation[4];

    private double rate = 1;
	
	private int width = 500;
	private int height = 540;
	private int heightWithoutToolbar = 500;
	private final int TOOLBAR_HEIGHT = 40;
	
	private ResourceBundle myResources;

	public GuiClass(ResourceBundle myResources) {
		this.myResources = myResources;
	}
	
	//step function for Timeline
	public void step() {
		thisSim.update(myGrid, myReader);
		display();
	}
	
	//sets up the basic Scene
	public Scene init (Stage stage, Timeline animation){
		
		root = new Group();
        myScene = new Scene(root, width, height, Color.ALICEBLUE);
        
        Button playButton = new Button(myResources.getString("PlayButtonText"));
		playButton.setOnAction((event) -> {
		    animation.play();
		});
               
        Button pauseButton = new Button(myResources.getString("PauseButtonText"));
        pauseButton.setOnAction((event) -> {
		    animation.pause();
		});
        Button stopButton = new Button(myResources.getString("StopButtonText"));
        stopButton.setOnAction((event) -> {
        	reset(animation);
		});
        Button ffButton = new Button(myResources.getString("FFButtonText"));
        ffButton.setOnAction((event) -> {
		   	speedUp(animation);
		});
        Button sdButton = new Button(myResources.getString("SDButtonText"));
        sdButton.setOnAction((event) -> {
		    slowDown(animation);
		});
        Button stepButton = new Button(myResources.getString("StepButtonText"));
        stepButton.setOnAction((event) -> {
		    step();
		});
        Button openButton = new Button(myResources.getString("OpenButtonText"));
        openButton.setOnAction((event) -> {
		    initializeGrid();
		});

        //ffButton.setStyle("-fx-font-size: 15pt;");

        playButton.setMaxSize(60, 30);
        pauseButton.setMaxSize(60, 30);
        stopButton.setMaxSize(60, 30);
        ffButton.setMaxSize(60, 30);
        sdButton.setMaxSize(60, 30);
        stepButton.setMaxSize(60, 30);
        openButton.setMaxSize(60,  30);

        TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
        tileButtons.setPrefColumns(7);
        tileButtons.setPadding(new Insets(7.5, 0, 7.5, 7.5));
        tileButtons.setHgap(20.0);
        tileButtons.getChildren().addAll(playButton, pauseButton, stopButton, ffButton, sdButton, stepButton, openButton);
    
		root.getChildren().add(tileButtons);  
		return myScene;	
	}
	
	private void initializeGrid() {
		myReader = new Reader();
		Simulation gameOfLife = new GameOfLife(myReader.getGlobalChars());
		sims[0] = gameOfLife;
		Simulation spreadingFire = new SpreadingFire(myReader.getGlobalChars());
		sims[1] = spreadingFire;
		Simulation segregation = new Segregation(myReader.getGlobalChars());
		sims[2] = segregation;
		Simulation waTor = new WaTor(myReader.getGlobalChars());
		sims[3] = waTor;
		myGrid = new Grid(myReader);
		thisSim = sims[myReader.getSimNum()];
		System.out.println(myReader.getGlobalChars().toString());
		System.out.printf("About to initialize Sim: %d\n", myReader.getSimNum());
		initDisplay();
	}
	
	public void reset(Timeline animation){
		animation.stop();
		rate = 1;
		for (Shape thisShape : shapeList) {
			root.getChildren().remove(thisShape);
		}
	}
	
	//Speeds up the framerate 
    private void speedUp(Timeline animation){
    	if(rate<=32)
    		rate *= 2;
    	animation.setRate(rate);
    	System.out.printf("Rate: %f\n", rate);
    }
    
    //returns framerate to default
    private void slowDown(Timeline animation){
    	if(rate>=1/32.0)
    		rate /=2;
    	animation.setRate(rate);
    	System.out.printf("Rate: %f\n", rate);
    }
	
	public void initDisplay() {
		shapeList = new ArrayList<Shape>();
		for (int i = 0; i < myReader.getSize(); i++) {
			Shape newShape = thisSim.getCellShape(i, width, heightWithoutToolbar, myReader.getGlobalChars().get("rows"), myReader.getGlobalChars().get("cols"));
			newShape.setFill(thisSim.getCellColor(i, myGrid));
			root.getChildren().add(newShape);
			shapeList.add(newShape);
		}
		System.out.println("Display Initialized");
	}

	public void display() {
		for (int i = 0; i < myReader.getSize(); i++) {
			Shape currentShape = shapeList.get(i);
			currentShape.setFill(thisSim.getCellColor(i, myGrid));
		}
	}
	
}
