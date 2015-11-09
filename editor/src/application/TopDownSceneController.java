package application;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;

import app.AppFacade;
import controller.DraggableCamera;
import controller.ECS.EntityDataAppState;
import controller.ECS.EntitySystem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.Model;
import model.world.World;
import util.event.EventManager;
import util.event.scene.AppClosedEvent;
import util.event.scene.RunEvent;
import view.Overview;

public class TopDownSceneController {

	private JmeForImageView jme;
	
	private final TopDownCamera camera;
	private final TopDownToolController tool;
	
	public TopDownSceneController(Model model, Overview view) {
		view.sceneViewer.setController(this);
		jme = new JmeForImageView();
		jme.enqueue((app) -> createScene(app, model.getEntityData(), model.getWorld()));
		jme.bind(view.sceneViewer.getImage());
		EventManager.register(this);
		
		camera = new TopDownCamera(jme);
		tool = new TopDownToolController(jme);
	}

	
	@Subscribe
	public void onAppClosedEvent(AppClosedEvent e){
		jme.stop(false);
	}
	
	@Subscribe
	public void onRunEvent(RunEvent e){
		if(e.value)
			jme.enqueue((app) -> startGame(app));
		else
			jme.enqueue((app) -> stopGame(app));
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
	
	public void onMousePressed(MouseEvent e){
		camera.onMousePressed(e);
		
	}
	
	public void onMouseReleased(MouseEvent e){
		
	}

	public void onMouseMoved(MouseEvent e){
		
	}
	
	public void onMouseDragged(MouseEvent e){
		camera.onMouseDragged(e);
	}
	
	public void onMouseScroll(ScrollEvent e){
		camera.onMouseScroll(e);
	}

	public void onKeyPressed(KeyEvent e){
		camera.onKeyPressed(e);
	}
	
	public void onKeyReleased(KeyEvent e){
		camera.onKeyReleased(e);
	}

	static private boolean createScene(SimpleApplication app, EntityData ed, World world) {
		AppFacade.setApp(app);
		AppStateManager stateManager = app.getStateManager();
		
		DraggableCamera cam = new DraggableCamera(app.getCamera());
		cam.setRotationSpeed(0.001f);
		cam.setMoveSpeed(1f);
		
		stateManager.attach(cam);
		stateManager.attach(new EntityDataAppState(ed));
		
		EntitySystem es = new EntitySystem(ed, world);
		stateManager.attach(es);
		es.initVisuals(true);
		es.initAudio(false);
		es.initCommand(false);
		es.initLogic(false);
		return true;
	}

}
