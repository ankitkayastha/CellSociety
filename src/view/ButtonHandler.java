package view;

import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;



public class ButtonHandler {
	private Group root;
	private Timeline animation;
	private ResourceBundle myResources;
	private int height;
	private int width;
	private GuiClass gui;

	public ButtonHandler(GuiClass gui, Group root, Timeline animation, ResourceBundle myResources, int height, int width) {
		this.gui = gui;
		this.root = root;
		this.animation = animation;
		this.myResources = myResources;
		this.height = height;
		this.width = width;
	}
	
	private Button createButton(String title) {
		Button thisButton = new Button(title);
		thisButton.setDisable(true);
		thisButton.setMaxSize(60, 30);
		return thisButton;
	}
	
	private void setDisables(Button playButton, Button pauseButton, Button stopButton, Button ffBUtton, 
			Button sdButton, Button stepButton, Button openButton, Button wrapButton,
			boolean play, boolean pause, boolean stop, boolean ff, 
			boolean sd, boolean step, boolean open, boolean wrap) {
		
	}
		
	public void createButtons() {
		Button playButton = createButton(myResources.getString("PlayButtonText"));
		Button pauseButton = createButton(myResources.getString("PauseButtonText"));
		Button stopButton = createButton(myResources.getString("StopButtonText"));
		Button ffButton = createButton(myResources.getString("FFButtonText"));
		Button sdButton = createButton(myResources.getString("SDButtonText"));
		Button stepButton = createButton(myResources.getString("StepButtonText"));
		Button openButton = createButton(myResources.getString("OpenButtonText"));
		Button wrapButton = createButton(myResources.getString("WrapButtonText"));
		openButton.setDisable(false);

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
			gui.reset(animation);
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
			gui.step();
			playButton.setDisable(false);
			pauseButton.setDisable(true);
			stopButton.setDisable(true);
			ffButton.setDisable(true);
			sdButton.setDisable(true);
			openButton.setDisable(true);
		});
		openButton.setOnAction((event) -> {
			gui.initializeGrid();
			playButton.setDisable(false);
			pauseButton.setDisable(true);
			stopButton.setDisable(false);
			ffButton.setDisable(true);
			sdButton.setDisable(true);
			stepButton.setDisable(false);
			openButton.setDisable(true);
			wrapButton.setDisable(false);
		});
		wrapButton.setOnAction((event) -> {
			gui.toggleType();
		});

		// ffButton.setStyle("-fx-font-size: 15pt;");

		TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
		tileButtons.setPrefColumns(2);
		tileButtons.setPadding(new Insets(7.5, 0, 7.5, 7.5));
		tileButtons.setHgap(20.0);
		tileButtons.setVgap(10.0);
		tileButtons.getChildren().addAll(playButton, pauseButton, stepButton, stopButton, sdButton, ffButton,
				openButton, wrapButton);
		tileButtons.setLayoutX(width);
		tileButtons.setLayoutY(0);

		root.getChildren().add(tileButtons);
	}
	
	private void speedUp(Timeline animation, Button ffButton) {
		if (animation.getRate() <= 32){
			animation.setRate(animation.getRate()*2); 
		}
		ffButton.setDisable(!(animation.getRate() <= 32));
		animation.setRate(animation.getRate());
		System.out.printf("Rate: %f\n", animation.getRate());
	}

	private void slowDown(Timeline animation, Button sdButton) {
		if (animation.getRate() >= 1 / 32.0)
			animation.setRate(animation.getRate()/2); 
		sdButton.setDisable(!(animation.getRate() >= 1 / 32.0));
		animation.setRate(animation.getRate());
		System.out.printf("Rate: %f\n", animation.getRate());
	}
}
