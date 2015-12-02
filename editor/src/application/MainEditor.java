package application;
	
import presenter.WorldEditorPresenter;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;

import controller.ECS.EntitySystem;
import app.AppFacade;
import application.topDownScene.TopDownSceneController;
import application.topDownScene.state.DraggableCameraState;
import application.topDownScene.state.SceneSelectorState;
import application.topDownScene.state.WorldLocaliserState;
import application.topDownScene.state.WorldToolState;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import model.world.HeightMapTool;
import model.world.WorldData;
import util.LogUtil;
import view.Overview;


public class MainEditor extends Application {
	Model model;
	Controller controller;
	TopDownSceneController topDownScenecontroller;
	Overview view;
	private JmeForImageView jme;
	WorldEditorPresenter worldEditorPresenter;

	@Override
	public void start(Stage primaryStage) {
		LogUtil.init();
		model = new Model();
		jme = new JmeForImageView();
		jme.enqueue((app) -> createScene(app, model.getEntityData(), model.getWorld()));
		worldEditorPresenter = new WorldEditorPresenter(jme, model.getWorld());
		view = new Overview(primaryStage, model, worldEditorPresenter);

		topDownScenecontroller = new TopDownSceneController(jme, view, worldEditorPresenter);
		controller = new Controller(model, view);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	static private boolean createScene(SimpleApplication app, EntityData ed, WorldData world) {
		AppFacade.setApp(app);
		AppStateManager stateManager = app.getStateManager();
		
		DraggableCameraState cam = new DraggableCameraState(app.getCamera());
		cam.setRotationSpeed(0.001f);
		cam.setMoveSpeed(1f);
		stateManager.attach(cam);

		stateManager.attach(new SceneSelectorState());
		stateManager.attach(new WorldToolState());
		stateManager.attach(new WorldLocaliserState());
		
		stateManager.getState(WorldToolState.class).setTool(new HeightMapTool(world));
		
		EntitySystem es = new EntitySystem(ed, world);
		stateManager.attach(es);
		es.initVisuals(true);
		es.initAudio(false);
		es.initCommand(false);
		es.initLogic(false);
		return true;
	}

}
