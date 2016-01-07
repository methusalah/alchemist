package presenter;

import model.Command;
import model.state.DraggableCameraState;
import model.state.WorldLocaliserState;
import model.state.WorldToolState;
import model.world.WorldData;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;

import controller.ECS.EntitySystem;
import controller.ECS.SceneSelectorState;
import app.AppFacade;
import application.EditorPlatform;
import application.topDownScene.EditionInputListener;
import application.topDownScene.GameInputListener;
import application.topDownScene.SceneInputManager;
import application.topDownScene.TopDownCamera;


public class ScenePresenter {
	private final EditionInputListener edition;
	private final TopDownCamera camera;
	private final GameInputListener game;
	
	public ScenePresenter(SceneInputManager inputManager) {
		if(EditorPlatform.getScene() == null){
			JmeForImageView scene = new JmeForImageView();
			scene.enqueue((app) -> createScene(app, EditorPlatform.getEntityData(), EditorPlatform.getWorldData(), EditorPlatform.getCommand()));
			EditorPlatform.setScene(scene);
		}
		
		edition = new EditionInputListener(EditorPlatform.getScene());
		camera = new TopDownCamera(EditorPlatform.getScene());
		game = new GameInputListener(EditorPlatform.getScene());
		
		
		inputManager.addListener(camera);
		inputManager.addListener(edition);

		EditorPlatform.getRunStateProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.getState() == RunState.State.Run){
				inputManager.removeListener(edition);
				inputManager.removeListener(camera);
				inputManager.addListener(game);
			} else {
				inputManager.removeListener(game);
				inputManager.addListener(edition);
				inputManager.addListener(camera);
			}
		});
	}
	
	static private boolean createScene(SimpleApplication app, EntityData ed, WorldData world, Command command) {
		AppFacade.setApp(app);
		AppStateManager stateManager = app.getStateManager();
		
		DraggableCameraState cam = new DraggableCameraState(app.getCamera());
		cam.setRotationSpeed(0.001f);
		cam.setMoveSpeed(1f);
		stateManager.attach(cam);

		stateManager.attach(new SceneSelectorState());
		stateManager.attach(new WorldToolState());
		stateManager.attach(new WorldLocaliserState());
		
		EntitySystem es = new EntitySystem(ed, world, command);
		stateManager.attach(es);
		es.initVisuals(true);
		es.initAudio(false);
		es.initCommand(false);
		es.initLogic(false);
		return true;
	}
	
	public JmeForImageView getScene(){
		return EditorPlatform.getScene();
	}
	
}
