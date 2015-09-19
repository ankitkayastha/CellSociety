package view;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.Grid;
import model.Reader;
import simulation.Simulation;
public class GuiClass {
	
	private final String TITLE = "CellSociety";
	private Scene myScene;
	private Group root;
	private Grid myGrid;
	
	private ImageView play = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("play.png")));
	private ImageView pause = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("pause.png")));
	private ImageView stop = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("stop.png")));
	private ImageView ff = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("ff.png")));
	private ImageView sd = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("sd.png")));
	private ImageView open = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("open.gif")));
	private ImageView step = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("step.png")));
	
	private int status = 0;
	private Simulation thisSim;
	private Reader myReader;
	private List<Shape> shapeList;
	
	private int width = 500;
	private int height = 540;
	private int heightWithoutToolbar = 500;
	private final int TOOLBAR_HEIGHT = 40;
	
		public String getTitle(){
		return TITLE;
	}
	
	//step function for Timeline
	public void step(double secondDelay) {
		thisSim.update(myGrid, myReader);
		display(myGrid, myReader, thisSim);
	}
	
	//sets up the basic Scene
	public Scene init (){
		
		rootCreate();
        myScene = new Scene(root, width, height, Color.ALICEBLUE);
        
		return myScene;	
	}
	
	//basic reset method (TO BE REMADE)
	public void reset(){
		
		//test.setX(width / 2 - test.getBoundsInLocal().getWidth() / 2);
        //test.setY(height / 2  - test.getBoundsInLocal().getHeight() / 2);
        
	}
	
	//checks for clicked button and passes it to the main
	public int handleMouseInput(double x, double y){
		
		if(play.contains(x, y))
			return 1;
		if(pause.contains(x, y))
			return 2;
		if(stop.contains(x, y))
			return 3;
		if(ff.contains(x, y))
			return 4;
		if(open.contains(x,y))
			return 5;
		if(step.contains(x,y))
			return 6;
		if(sd.contains(x,y))
			return 7;
		return 0;
	}
		
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	
	//sets up toolbar defaults
	public void toolbar(){
		play.setX(0);
		play.setY(0);
        root.getChildren().add(play);
        
        pause.setX(70);
		pause.setY(0);
        root.getChildren().add(pause);
        
        stop.setX(140);
		stop.setY(0);
        root.getChildren().add(stop);
        
        ff.setX(210);
		ff.setY(0);
        root.getChildren().add(ff);
        
        sd.setX(280);
		sd.setY(0);
        root.getChildren().add(sd);
       
        step.setX(350);
        step.setY(0);
        root.getChildren().add(step);
        
        open.setX(420);
		open.setY(0);
        root.getChildren().add(open);
        
	}
	
	public void rootCreate(){
		root = new Group();
		toolbar();
	}
	
	public void initDisplay(Grid myGrid, Reader myReader, Simulation mySim, int simNum) {
		shapeList = new ArrayList<Shape>();
		this.myGrid = myGrid;
		this.myReader = myReader;
		thisSim = mySim;
		for (int i = 0; i < myReader.getSize(); i++) {
			Shape newShape = mySim.getCellShape(i, width, heightWithoutToolbar, myReader.getGlobalChars().get("rows"), myReader.getGlobalChars().get("cols"));
			newShape.setFill(mySim.getCellColor(i, myGrid));
			root.getChildren().add(newShape);
			shapeList.add(newShape);
		}
		System.out.println("Display Initialized");
	}

	public void display(Grid myGrid, Reader myReader, Simulation mySim) {
		for (int i = 0; i < myReader.getSize(); i++) {
			Shape currentShape = shapeList.get(i);
			currentShape.setFill(mySim.getCellColor(i, myGrid));
		}
	}
	
}
