package view;

import java.util.ArrayList;
import java.util.List;

import controller.ECS.SceneSelectorState;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import model.ES.serial.Blueprint;
import presenter.EditorPlatform;
import presenter.ScenePresenter;
import presenter.common.EntityNode;
import presenter.common.SceneInputManager;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class SceneView extends Pane {
	private final ScenePresenter presenter;

	private final List<KeyCode> pressed = new ArrayList<KeyCode>();
	
	public SceneView() {
		presenter = new ScenePresenter();
		setFocusTraversable(true);
		ImageView image = new ImageView();
		setStyle("-fx-background-color: gray");
		
		configureDragAndDrop();

		image.fitHeightProperty().bind(heightProperty());
		image.fitWidthProperty().bind(widthProperty());
		image.setStyle("-fx-background-color: blue");

		image.setOnMousePressed(e -> presenter.getInputManager().onMousePressed(e));
		image.setOnMouseDragged(e -> presenter.getInputManager().onMouseDragged(e));
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
	
	private void configureDragAndDrop(){
		setOnDragDetected(e -> {});
        
        setOnDragEntered(e -> {});
        
        setOnDragExited(e -> {});
        
        setOnDragOver(e -> {
			if(Dragpool.containsType(Blueprint.class))
        		e.acceptTransferModes(TransferMode.ANY);
			e.consume();
			EditorPlatform.getScene().enqueue(app -> {
				Point2D planarCoord = app.getStateManager().getState(SceneSelectorState.class).getPointedCoordInPlan(new Point2D(e.getX(), -e.getY()));
				LogUtil.info("pointed = " + new Point2D(e.getX(), this.getHeight()-e.getY()));
				return true;
			});
        });
        
        setOnDragDropped(e -> {
				if(Dragpool.containsType(Blueprint.class))
					presenter.createEntityAt(Dragpool.grabContent(Blueprint.class), new Point2D(e.getX(), this.getHeight()-e.getY()));
		});
	}
}
