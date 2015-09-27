package main;

import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.GuiClass;

public class Main extends Application {

	private static final int FRAMES_PER_SECOND = 60;
	private static int MILLISECOND_DELAY = 60000 / FRAMES_PER_SECOND;

	private final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	private Scene scene;
	private Stage stage;
	private Timeline animation;
	private GuiClass gui;

	@Override
	public void start(Stage s) {
		stage = s;
		setStage();
	}

	private void setStage() {
		ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "text");
		stage.setTitle(myResources.getString("Title"));

		gui = new GuiClass(myResources);

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> gui.step());

		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		animation.pause();

		scene = gui.init(stage, animation);
		scene.setOnMouseClicked(e -> gui.click(e.getX(), e.getY()));

		stage.setScene(scene);
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}