package view;

import application.topDownScene.SceneInputManager;
import application.topDownScene.TopDownSceneController;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class SceneViewer extends Pane {
	
	private SceneInputManager inputManager;
	private final ImageView image;
	
	
	public ImageView getImage() {
		return image;
	}

	
	public SceneViewer() {
		setFocusTraversable(false);
		image = new ImageView();
		setStyle("-fx-background-color: gray");

		image.fitHeightProperty().bind(heightProperty());
		image.fitWidthProperty().bind(widthProperty());
		image.setStyle("-fx-background-color: blue");


		// Camera rotation
		image.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				inputManager.onMousePressed(e);
			}
		});
		image.setOnMouseDragged(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent e) {
				inputManager.onMouseMoved(e);
			}
		});

		image.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				inputManager.onMouseReleased(e);
			}
		});
		image.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				inputManager.onMouseMoved(e);
			}
		});
		
		// camera zoom
		image.setOnScroll(new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent e) {
				inputManager.onMouseScroll(e);
			}
		});
		
		getChildren().add(image);

	}

	public void setInputManager(SceneInputManager inputManager) {
		this.inputManager = inputManager;
	}
	
	public void registerKeyInputs(Scene rootScene){
		// camera motion
		rootScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				inputManager.onKeyPressed(e);
			}
		});

		rootScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				inputManager.onKeyReleased(e);
			}
		});
	}
}
