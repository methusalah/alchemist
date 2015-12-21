package view;

import java.util.ArrayList;
import java.util.List;

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
	private List<KeyCode> pressed = new ArrayList<KeyCode>();
	
	public ImageView getImage() {
		return image;
	}

	
	public SceneViewer() {
		setFocusTraversable(true);
		image = new ImageView();
		setStyle("-fx-background-color: gray");

		image.fitHeightProperty().bind(heightProperty());
		image.fitWidthProperty().bind(widthProperty());
		image.setStyle("-fx-background-color: blue");


		// Camera rotation
		image.setOnMousePressed(e -> inputManager.onMousePressed(e));
		image.setOnMouseDragged(e -> inputManager.onMouseMoved(e));
		image.setOnMouseReleased(e -> inputManager.onMouseReleased(e));
		image.setOnMouseMoved(e -> inputManager.onMouseMoved(e));
		
		// camera zoom
		image.setOnScroll(e -> inputManager.onMouseScroll(e));
		
		getChildren().add(image);

	}

	public void setInputManager(SceneInputManager inputManager) {
		this.inputManager = inputManager;
	}
	
	public void registerKeyInputs(Scene rootScene){
		// camera motion
		rootScene.setOnKeyPressed(e -> {
			inputManager.onKeyPressed(e);
			pressed.add(e.getCode());
		});
		rootScene.setOnKeyReleased(e -> {
			if(pressed.contains(e.getCode())){
				inputManager.onKeyReleased(e);
				pressed.remove(e.getCode());
			}
		});
	}
}
