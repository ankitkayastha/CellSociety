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
	
	public void createButtons() {
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
