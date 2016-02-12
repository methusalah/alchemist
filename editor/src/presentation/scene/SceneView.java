package presentation.scene;

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

import app.AppFacade;
import controller.ECS.DataState;
import controller.ECS.EntitySystem;
import controller.ECS.SceneSelectorState;
import controller.regionPaging.RegionPager;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import model.Command;
import model.EditorPlatform;
import model.ES.component.motion.PlanarStance;
import model.ES.serial.Blueprint;
import model.state.DraggableCameraState;
import model.state.InstrumentUpdateState;
import model.state.WorldLocaliserState;
import presentation.Dragpool;
import presentation.common.SceneInputManager;
import presentation.common.TopDownCamInputListener;
import presentation.scene.customControl.JmeImageView;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class SceneView extends Pane {

	private final List<KeyCode> pressed = new ArrayList<KeyCode>();

	public SceneView() {
		// presentation
		if(EditorPlatform.getScene() == null){
			JmeImageView jmeScene = new JmeImageView();
			jmeScene.enqueue((app) -> createScene(app, EditorPlatform.getEntityData(), EditorPlatform.getCommand()));
			EditorPlatform.setScene(jmeScene);
		}
		
		camera = new TopDownCamInputListener(EditorPlatform.getScene());
		EditorPlatform.getSceneInputManager().addListener(camera);

		// view
		ImageView image = new ImageView();
		setStyle("-fx-background-color: gray");
		
		configureDragAndDrop();

		image.fitHeightProperty().bind(heightProperty());
		image.fitWidthProperty().bind(widthProperty());
		image.setStyle("-fx-background-color: blue");

		image.setOnMousePressed(e -> getInputManager().onMousePressed(e));
		image.setOnMouseDragged(e -> getInputManager().onMouseDragged(e));
		image.setOnMouseReleased(e -> getInputManager().onMouseReleased(e));
		image.setOnMouseMoved(e -> getInputManager().onMouseMoved(e));
		image.setOnScroll(e -> getInputManager().onMouseScroll(e));
		EditorPlatform.getScene().bind(image);
		
		getChildren().add(image);
		
	}
	
	// VIEW LOGIC
	public void registerKeyInputs(Scene rootScene){
		// camera motion
		rootScene.setOnKeyPressed(e -> {
			getInputManager().onKeyPressed(e);
			pressed.add(e.getCode());
		});
		rootScene.setOnKeyReleased(e -> {
			if(pressed.contains(e.getCode())){
				getInputManager().onKeyReleased(e);
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
					createEntityAt(Dragpool.grabContent(Blueprint.class), new Point2D(e.getX(), this.getHeight()-e.getY()));
		});
	}
	
	// PRESENTATION LOGIC
	private static final int SHADOWMAP_SIZE = 4096;

	private final TopDownCamInputListener camera;
	static private boolean createScene(SimpleApplication app, EntityData ed, Command command) {
		AppFacade.setApp(app);
		app.getViewPort().addProcessor(new FilterPostProcessor(app.getAssetManager()));
		
		AppStateManager stateManager = app.getStateManager();
		
		stateManager.attach(new InstrumentUpdateState());

		stateManager.attach(new RegionPager());
		stateManager.getState(RegionPager.class).setEnabled(true);
		
		DraggableCameraState cam = new DraggableCameraState(app.getCamera());
		cam.setRotationSpeed(0.001f);
		cam.setMoveSpeed(1f);
		stateManager.attach(cam);

		stateManager.attach(new SceneSelectorState());
		stateManager.attach(new WorldLocaliserState());
		
		EntitySystem es = new EntitySystem(ed, command);
		stateManager.attach(es);
		es.initVisuals(true);
		es.initAudio(false);
		es.initCommand(false);
		es.initLogic(false);

		// adding filters
		DirectionalLightShadowFilter sf = new DirectionalLightShadowFilter(AppFacade.getAssetManager(), SHADOWMAP_SIZE, 1);
		sf.setEnabled(false);
		sf.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
		sf.setShadowZExtend(SHADOWMAP_SIZE);
		AppFacade.getFilterPostProcessor().addFilter(sf);

		BloomFilter bf = new BloomFilter(BloomFilter.GlowMode.Objects);
		//bf.setEnabled(false);
		AppFacade.getFilterPostProcessor().addFilter(bf);
		
		FXAAFilter fxaa = new FXAAFilter();
		//fxaa.setEnabled(false);
		fxaa.setReduceMul(0.9f);
		fxaa.setSpanMax(5f);
		fxaa.setSubPixelShift(0f);
		fxaa.setVxOffset(10f);
		AppFacade.getFilterPostProcessor().addFilter(fxaa);

		return true;
	}
	
	public SceneInputManager getInputManager() {
		return EditorPlatform.getSceneInputManager();
	}
	
	public void createEntityAt(Blueprint blueprint, Point2D screenCoord){
		EditorPlatform.getScene().enqueue(app -> {
			EntityData ed = app.getStateManager().getState(DataState.class).getEntityData(); 
			EntityId newEntity = blueprint.createEntity(ed, null);
			PlanarStance stance = ed.getComponent(newEntity, PlanarStance.class); 
			if(stance != null){
				Point2D planarCoord = app.getStateManager().getState(SceneSelectorState.class).getPointedCoordInPlan(screenCoord);
				ed.setComponent(newEntity, new PlanarStance(planarCoord, stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
			}
			return true;
		});
	}
	
}
