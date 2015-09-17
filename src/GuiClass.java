
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
public class GuiClass {
	
	private final String TITLE = "CellSociety";
	private Scene myScene;
	private Group root;
	
	private ImageView play = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("play.png")));
	private ImageView pause = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("pause.png")));
	private ImageView stop = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("stop.png")));
	private ImageView ff = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("ff.png")));
	
	private ImageView test;
	private int width = 500;
	private int height = 540;
	private int speed = 50;
	
	//
	public String getTitle(){
		return TITLE;
	}
	
	//step function for Timeline
	public void step(double secondDelay) {
		
		if(test.getX() > width - test.getBoundsInLocal().getWidth()
				|| test.getX() < 0){
			speed = -speed;
		}
		test.setX(test.getX() + speed * secondDelay);
		
	}
	
	//sets up the basic Scene
	public Scene init (){
		
		rootCreate();
        myScene = new Scene(root, width, height, Color.WHITE);
        
		return myScene;	
		
	}
	
	//basic reset method (TO BE REMADE)
	public void reset(){
		
		test.setX(width / 2 - test.getBoundsInLocal().getWidth() / 2);
        test.setY(height / 2  - test.getBoundsInLocal().getHeight() / 2);
        
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
		return 0;
		
	}
	
	//sets up toolbar defaults
	public void toolbar(){
		play.setX(0);
		play.setY(0);
        root.getChildren().add(play);
        
        pause.setX(40);
		pause.setY(0);
        root.getChildren().add(pause);
        
        stop.setX(80);
		stop.setY(0);
        root.getChildren().add(stop);
        
        ff.setX(120);
		ff.setY(0);
        root.getChildren().add(ff);
	}
	
	//sets up test image
	public void testImg(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("jmetempat.gif"));
        test = new ImageView(image);
        // x and y represent the top left corner, so center it
        test.setX(width / 2 - test.getBoundsInLocal().getWidth() / 2);
        test.setY(height / 2  - test.getBoundsInLocal().getHeight() / 2);
        root.getChildren().add(test);
	}
	
	public void rootCreate(){
		root = new Group();
		toolbar();
		testImg();
	}
	
	
}
