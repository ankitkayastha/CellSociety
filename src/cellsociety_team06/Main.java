package cellsociety_team06;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Pretty much identical to example Main.java. Used in the same way, to initialize the Game example.
 */
public class Main extends Application {
	
    private static final int FRAMES_PER_SECOND = 60;
    
    private static int DM = 1000 / FRAMES_PER_SECOND;
    private static double DS = 1.0 / FRAMES_PER_SECOND;
    
    private static int MILLISECOND_DELAY = DM;
    private static double SECOND_DELAY = DS;
    private Scene scene;
    private Stage stage;
    private Timeline animation;
    
    private GuiClass gui;

    /**
     * Set things up at the beginning.
     */
    @Override
    public void start (Stage s) {
    	
    	stage = s;
    	setStage();
        
    }
    
    //initializes the stage and the basic Timeline
    private void setStage(){
    	
    	gui = new GuiClass();
        stage.setTitle(gui.getTitle());    

        scene = gui.init();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> gui.step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
        
        scene.setOnMouseClicked(e -> click(e.getX(), e.getY()));
        
    }
    
    //talks to the GuiClass to see what button was clicked
    private void click(double x, double y) {
    	
    	switch(gui.handleMouseInput(x, y)){
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
    	}
	}

    //Speeds up the framerate 
    private void speedUp(){
    	if(MILLISECOND_DELAY<DM*8){
    		MILLISECOND_DELAY *= 2;
    		SECOND_DELAY *= 2;
    	}
    }
    
    //returns framerate to default
    private void slowDown(){
    	MILLISECOND_DELAY = DM;
    	SECOND_DELAY = DS;
    }
    

    public static void main (String[] args) {
        launch(args);
    }
}