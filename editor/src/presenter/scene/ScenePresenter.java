package presenter.scene;

import model.Command;
import model.state.DraggableCameraState;
import model.state.WorldLocaliserState;
import model.state.WorldToolState;
import model.world.WorldData;
import presenter.EditorPlatform;
import presenter.common.RunState;
import presenter.common.SceneInputManager;
import presenter.common.RunState.State;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;

import controller.ECS.EntitySystem;
import controller.ECS.SceneSelectorState;
import app.AppFacade;

public class ScenePresenter {
	private final EditionInputListener edition;
	private final TopDownCamInputListener camera;
	private final GameInputListener game;
	
	public ScenePresenter() {
		if(EditorPlatform.getScene() == null){
			JmeForImageView jmeScene = new JmeForImageView();
			jmeScene.enqueue((app) -> createScene(app, EditorPlatform.getEntityData(), EditorPlatform.getWorldData(), EditorPlatform.getCommand()));
			EditorPlatform.setScene(jmeScene);
		}
		
		edition = new EditionInputListener(EditorPlatform.getScene());
		camera = new TopDownCamInputListener(EditorPlatform.getScene());
		game = new GameInputListener(EditorPlatform.getScene());
		
		
		EditorPlatform.getSceneInputManager().addListener(camera);
		EditorPlatform.getSceneInputManager().addListener(edition);

		EditorPlatform.getRunStateProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.getState() == RunState.State.Run){
				EditorPlatform.getSceneInputManager().removeListener(edition);
				EditorPlatform.getSceneInputManager().removeListener(camera);
				EditorPlatform.getSceneInputManager().addListener(game);
			} else {
				EditorPlatform.getSceneInputManager().removeListener(game);
				EditorPlatform.getSceneInputManager().addListener(edition);
				EditorPlatform.getSceneInputManager().addListener(camera);
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

	public SceneInputManager getInputManager() {
		return EditorPlatform.getSceneInputManager();
	}
	
}