package application.topDownScene;

import presenter.WorldEditorPresenter;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;

import app.AppFacade;
import application.topDownScene.state.DraggableCameraState;
import application.topDownScene.state.SceneSelectorState;
import application.topDownScene.state.WorldLocaliserState;
import application.topDownScene.state.WorldToolState;
import controller.ECS.DataAppState;
import controller.ECS.EntitySystem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.Model;
import model.world.HeightMapTool;
import model.world.WorldData;
import util.event.EventManager;
import util.event.scene.AppClosedEvent;
import util.event.scene.RunEvent;
import util.geometry.geom2d.Point2D;
import view.Overview;

public class TopDownSceneController {

	private final JmeForImageView jme;
	private final SceneInputManager inputManager = new SceneInputManager();
	
	public TopDownSceneController(JmeForImageView jme, Overview view, WorldEditorPresenter worldEditorPresenter) {
		this.jme = jme;
		view.sceneViewer.setInputManager(inputManager);
		jme.bind(view.sceneViewer.getImage());
		EventManager.register(this);
		
		inputManager.addListener(new TopDownCamera(jme));
		inputManager.addListener(worldEditorPresenter);
	}

	
	@Subscribe
	public void onAppClosedEvent(AppClosedEvent e){
		jme.stop(false);
	}
	
	@Subscribe
	public void onRunEvent(RunEvent e){
		if(e.value)
			jme.enqueue(app -> startGame(app));
		else
			jme.enqueue(app -> stopGame(app));
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
