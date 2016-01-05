package application.topDownScene;

import util.event.EventManager;
import util.event.scene.AppClosedEvent;
import util.event.scene.RunEvent;
import view.Overview;
import application.EditorPlatform;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3x.jfx.injfx.JmeForImageView;

import controller.ECS.EntitySystem;

public class TopDownSceneController {

	private final JmeForImageView jme;
	private final SceneInputManager inputManager = new SceneInputManager();
	
	private final TopDownCamera camera;
	private final EditionInputListener edition;
	private final GameInputListener game;
	
	public TopDownSceneController(Overview view) {
		this.jme = EditorPlatform.getScene();
		view.sceneViewer.setInputManager(inputManager);
		jme.bind(view.sceneViewer.getImage());
		EventManager.register(this);
		
		camera = new TopDownCamera(jme);
		edition = new EditionInputListener(jme);
		game = new GameInputListener(jme);
		
		
		inputManager.addListener(camera);
		inputManager.addListener(edition);
	}

	
	@Subscribe
	public void onAppClosedEvent(AppClosedEvent e){
		jme.stop(false);
	}
	
	@Subscribe
	public void onRunEvent(RunEvent e){
		if(e.value){
			inputManager.removeListener(edition);
			inputManager.removeListener(camera);
			inputManager.addListener(game);
			jme.enqueue(app -> startGame(app));
		} else {
			inputManager.removeListener(game);
			inputManager.addListener(edition);
			inputManager.addListener(camera);
			jme.enqueue(app -> stopGame(app));
		}
	}

	static public boolean startGame(SimpleApplication app){
		AppStateManager stateManager = app.getStateManager();
		EntitySystem es = stateManager.getState(EntitySystem.class);
		
		es.initVisuals(true);
		es.initAudio(true);
		es.initCommand(true);
		es.initLogic(true);
		return true;
	}
	

	static public boolean stopGame(SimpleApplication app){
		AppStateManager stateManager = app.getStateManager();
		EntitySystem es = stateManager.getState(EntitySystem.class);
		
		es.initVisuals(true);
		es.initAudio(false);
		es.initCommand(false);
		es.initLogic(false);
		return true;
	}
	
}
