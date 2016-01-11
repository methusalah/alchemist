package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import presenter.common.SceneInputManager;
import presenter.scene.ScenePresenter;

public class SceneView extends Pane {
	private final ScenePresenter presenter;

	private final List<KeyCode> pressed = new ArrayList<KeyCode>();
	
	public SceneView() {
		presenter = new ScenePresenter();
		setFocusTraversable(true);
		ImageView image = new ImageView();
		setStyle("-fx-background-color: gray");

		image.fitHeightProperty().bind(heightProperty());
		image.fitWidthProperty().bind(widthProperty());
		image.setStyle("-fx-background-color: blue");

		image.setOnMousePressed(e -> presenter.getInputManager().onMousePressed(e));
		image.setOnMouseDragged(e -> presenter.getInputManager().onMouseMoved(e));
		image.setOnMouseReleased(e -> presenter.getInputManager().onMouseReleased(e));
		image.setOnMouseMoved(e -> presenter.getInputManager().onMouseMoved(e));
		image.setOnScroll(e -> presenter.getInputManager().onMouseScroll(e));
		presenter.getScene().bind(image);
		
		getChildren().add(image);
	}

	public void registerKeyInputs(Scene rootScene){
		// camera motion
		rootScene.setOnKeyPressed(e -> {
			presenter.getInputManager().onKeyPressed(e);
			pressed.add(e.getCode());
		});
		rootScene.setOnKeyReleased(e -> {
			if(pressed.contains(e.getCode())){
				presenter.getInputManager().onKeyReleased(e);
				pressed.remove(e.getCode());
			}
		});
	}
}
