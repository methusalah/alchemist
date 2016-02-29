package com.brainless.alchemist.view.tab.scene;

import java.util.ArrayList;
import java.util.List;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.model.ECS.blueprint.Blueprint;
import com.brainless.alchemist.model.state.SceneSelectorState;
import com.brainless.alchemist.presentation.sceneView.SceneViewPresenter;
import com.brainless.alchemist.presentation.sceneView.SceneViewViewer;
import com.brainless.alchemist.view.BaseCamera;
import com.brainless.alchemist.view.ViewPlatform;
import com.brainless.alchemist.view.util.Dragpool;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import util.geometry.geom2d.Point2D;

public class SceneView extends Pane implements SceneViewViewer{
	private final SceneViewPresenter presenter;
	private final List<KeyCode> pressed = new ArrayList<KeyCode>();

	public SceneView() {
		presenter = new SceneViewPresenter(this);
		
		ViewPlatform.getSceneInputManager().addListener(new BaseCamera());

		ImageView image = new ImageView();
		setStyle("-fx-background-color: gray");
		
		configureDragAndDrop();

		image.fitHeightProperty().bind(heightProperty());
		image.fitWidthProperty().bind(widthProperty());
		image.setStyle("-fx-background-color: blue");

		image.setOnMousePressed(e -> ViewPlatform.getSceneInputManager().onMousePressed(e));
		image.setOnMouseDragged(e -> ViewPlatform.getSceneInputManager().onMouseDragged(e));
		image.setOnMouseReleased(e -> ViewPlatform.getSceneInputManager().onMouseReleased(e));
		image.setOnMouseMoved(e -> ViewPlatform.getSceneInputManager().onMouseMoved(e));
		image.setOnScroll(e -> ViewPlatform.getSceneInputManager().onMouseScroll(e));
		ViewPlatform.getScene().bind(image);
		
		getChildren().add(image);
		ViewPlatform.JavaFXScene.addListener((obs, oldValue, newValue) -> registerKeyInputs(newValue));
	}
	
	public void registerKeyInputs(Scene rootScene){
		// camera motion
		rootScene.setOnKeyPressed(e -> {
			pressed.add(e.getCode());
			ViewPlatform.getSceneInputManager().onKeyPressed(e);
		});
		rootScene.setOnKeyReleased(e -> {
			if(pressed.contains(e.getCode())){
				ViewPlatform.getSceneInputManager().onKeyReleased(e);
				pressed.remove(e.getCode());
			}
		});
	}
	
	private void configureDragAndDrop(){
		setOnDragDetected(e -> {});
        
        setOnDragEntered(e -> {});
        
        setOnDragExited(e -> {});
        
        setOnDragOver(e -> {
			if(Dragpool.containsType(Blueprint.class))
        		e.acceptTransferModes(TransferMode.ANY);
			e.consume();
        });
        
        setOnDragDropped(e -> {
				if(Dragpool.containsType(Blueprint.class))
					presenter.createEntityAt(Dragpool.grabContent(Blueprint.class), new Point2D(e.getX(), this.getHeight()-e.getY()));
		});
	}
}
