package view.tab.scene;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.FXAAFilter;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.EdgeFilteringMode;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import model.EditorPlatform;
import model.ECS.blueprint.Blueprint;
import model.state.DraggableCameraState;
import model.state.InstrumentUpdateState;
import model.state.SceneSelectorState;
import presentation.sceneView.SceneViewPresenter;
import presentation.sceneView.SceneViewViewer;
import util.geometry.geom2d.Point2D;
import view.ViewPlatform;
import view.common.SceneInputManager;
import view.util.Dragpool;

public class SceneView extends Pane implements SceneViewViewer{
	private final SceneViewPresenter presenter;
	private final List<KeyCode> pressed = new ArrayList<KeyCode>();
	private final TopDownCamInputListener camera;

	public SceneView() {
		presenter = new SceneViewPresenter(this);
		
		camera = new TopDownCamInputListener(EditorPlatform.getScene());
		ViewPlatform.getSceneInputManager().addListener(camera);

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
		EditorPlatform.getScene().bind(image);
		
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
			EditorPlatform.getScene().enqueue(app -> {
				Point2D planarCoord = app.getStateManager().getState(SceneSelectorState.class).getPointedCoordInPlan(new Point2D(e.getX(), -e.getY()));
				return true;
			});
        });
        
        setOnDragDropped(e -> {
				if(Dragpool.containsType(Blueprint.class))
					presenter.createEntityAt(Dragpool.grabContent(Blueprint.class), new Point2D(e.getX(), this.getHeight()-e.getY()));
		});
	}
}
