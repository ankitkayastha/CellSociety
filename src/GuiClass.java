import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public class GuiClass {
	
	private final String TITLE = "CellSociety";
	private Scene myScene;
	private Group root;
	private Grid myGrid;
	
	private ImageView play = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("play.png")));
	private ImageView pause = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("pause.png")));
	private ImageView stop = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("stop.png")));
	private ImageView ff = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("ff.png")));
	private ImageView open = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("open.gif")));
	
	private int status = 0;
	private int simNum = 0;
	
	private int width = 500;
	private int height = 540;
	private int heightWithoutToolbar = 500;
	private final int TOOLBAR_HEIGHT = 40;
	
	private double cellX;
	private double cellY;
	
	//
	public String getTitle(){
		return TITLE;
	}
	
	//step function for Timeline
	public void step(double secondDelay) {
		System.out.println(secondDelay);
		if (status != 1) {
			return;
		}
		myGrid.step();
	}
	
	//sets up the basic Scene
	public Scene init (){
		
		rootCreate();
        myScene = new Scene(root, width, height, Color.WHITE);
        
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
        
        pause.setX(40);
		pause.setY(0);
        root.getChildren().add(pause);
        
        stop.setX(80);
		stop.setY(0);
        root.getChildren().add(stop);
        
        ff.setX(120);
		ff.setY(0);
        root.getChildren().add(ff);
        
        open.setX(160);
		open.setY(0);
        root.getChildren().add(open);
	}
	
	public void rootCreate(){
		root = new Group();
		toolbar();
	}
	
	public void initDisplay(Grid myGrid, Reader myReader) {
		this.myGrid = myGrid;
		cellX = (double)heightWithoutToolbar/myReader.getCols();
		cellY = (double)width/myReader.getRows();
		for (int row = 0; row < myReader.getRows(); row++) {
			for (int col = 0; col < myReader.getCols(); col++) {
				System.out.println("INDEX: row: "+row+", col: "+col);
				System.out.println("DATAS: row: "+myGrid.getCell(row, col).getChars().get("row")+ ", col: "+myGrid.getCell(row,  col).getChars().get("col"));
				Rectangle newRectangle = new Rectangle(col*cellX, row*cellY+TOOLBAR_HEIGHT, cellX, cellY);
				Color rectColor = myGrid.getCell(row, col).getColor();
				newRectangle.setFill(rectColor);
				root.getChildren().add(newRectangle);
				rectList[row][col] = newRectangle;
			}
		}
	}
	
	public void display(Grid myGrid, Reader myReader) {
		for (int row = 0; row < myReader.getRows(); row++) {
			for (int col = 0; col < myReader.getCols(); col++) {
				Rectangle tempRect = rectList[row][col];
				Color rectColor = myGrid.getCell(row, col).getColor();
				tempRect.setFill(rectColor);
				System.out.printf("Color: %d %d %s\n", row, col,rectColor.toString());
			}
		}
	}
	
}