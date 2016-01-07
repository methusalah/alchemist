package view;

import java.util.ArrayList;
import java.util.List;

import application.topDownScene.SceneInputManager;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import presenter.ScenePresenter;

public class SceneView extends Pane {
	private final ScenePresenter presenter;
	private final SceneInputManager inputManager = new SceneInputManager();

	private final List<KeyCode> pressed = new ArrayList<KeyCode>();
	
	public SceneView() {
		presenter = new ScenePresenter(inputManager);
		setFocusTraversable(true);
		ImageView image = new ImageView();
		setStyle("-fx-background-color: gray");

		image.fitHeightProperty().bind(heightProperty());
		image.fitWidthProperty().bind(widthProperty());
		image.setStyle("-fx-background-color: blue");

		image.setOnMousePressed(e -> inputManager.onMousePressed(e));
		image.setOnMouseDragged(e -> inputManager.onMouseMoved(e));
		image.setOnMouseReleased(e -> inputManager.onMouseReleased(e));
		image.setOnMouseMoved(e -> inputManager.onMouseMoved(e));
		image.setOnScroll(e -> inputManager.onMouseScroll(e));
		presenter.getScene().bind(image);
		
		getChildren().add(image);
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
