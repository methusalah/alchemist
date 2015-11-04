package application;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.Model;
import util.event.EventManager;
import util.event.scene.AppClosedEvent;
import util.event.scene.RunEvent;
import util.geometry.geom2d.Point2D;
import view.Overview;
import app.AppFacade;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;

import controller.DraggableCamera;
import controller.ECS.EntityDataAppState;
import controller.ECS.EntitySystem;

public class TopDownSceneController {

	private JmeForImageView jme;
	private boolean zPressed = false, sPressed = false, qPressed = false, dPressed = false;
	Point2D camVelocity = Point2D.ORIGIN;
	Point2D oldCoord = null;

	
	public TopDownSceneController(Model model, Overview view) {
		view.sceneViewer.setController(this);
		jme = new JmeForImageView();
		jme.enqueue((app) -> createScene(app, model.getEntityData()));
		jme.bind(view.sceneViewer.getImage());
		EventManager.register(this);
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
		if(e.getButton() == MouseButton.SECONDARY)
			oldCoord = new Point2D(e.getX(), e.getY());
	}
	
	public void onMouseDragged(MouseEvent e){
		if(e.getButton() == MouseButton.SECONDARY){
			Point2D newCoord = new Point2D(e.getX(), e.getY());
			Point2D vec = newCoord.getSubtraction(oldCoord);
			jme.enqueue((app) -> rotateCam(app, vec));
			oldCoord = newCoord;
		}
	}
	
	public void onMouseReleased(MouseEvent e){
		
	}
	
	public void onMouseScroll(ScrollEvent e){
		jme.enqueue((app) -> zoomCam(app, e.getDeltaY()/e.getMultiplierY()));
	}

	public void onKeyPressed(KeyEvent e){
		if(e.getCode() == KeyCode.Z && !zPressed){
			camVelocity = camVelocity.getAddition(0, 1);
			zPressed = true;
		} else if(e.getCode() == KeyCode.S && !sPressed){
			camVelocity = camVelocity.getAddition(0, -1);
			sPressed = true;
		} else if(e.getCode() == KeyCode.Q && !qPressed){
			camVelocity = camVelocity.getAddition(-1, 0);
			qPressed = true;
		} else if(e.getCode() == KeyCode.D && !dPressed){
			camVelocity = camVelocity.getAddition(1, 0);
			dPressed = true;
		}
		jme.enqueue((app) -> setCamVelocity(app, camVelocity));
	}
	
	public void onKeyReleased(KeyEvent e){
		if(e.getCode() == KeyCode.Z){
			camVelocity = camVelocity.getAddition(0, -1);
			zPressed = false;
		} else if(e.getCode() == KeyCode.S){
			camVelocity = camVelocity.getAddition(0, 1);
			sPressed = false;
		} else if(e.getCode() == KeyCode.Q){
			camVelocity = camVelocity.getAddition(1, 0);
			qPressed = false;
		} else if(e.getCode() == KeyCode.D){
			camVelocity = camVelocity.getAddition(-1, 0);
			dPressed = false;
		}
		jme.enqueue((app) -> setCamVelocity(app, camVelocity));
	}
	
	
	
	
	
	static private boolean rotateCam(SimpleApplication app, Point2D vec){
		DraggableCamera cam = app.getStateManager().getState(DraggableCamera.class);
		cam.rotateCamera((float)vec.x, app.getCamera().getUp());
		cam.rotateCamera((float)vec.y, app.getCamera().getLeft());
		return true;
	}
	
	static private boolean zoomCam(SimpleApplication app, double amount){
		DraggableCamera cam = app.getStateManager().getState(DraggableCamera.class);
		cam.moveCamera((float)amount, false);
		return true;
	}
	
	static private boolean setCamVelocity(SimpleApplication app, Point2D vec){
		DraggableCamera cam = app.getStateManager().getState(DraggableCamera.class);
		cam.setVelocity(vec);
		return true;
	}

	static private boolean createScene(SimpleApplication app, EntityData ed) {
		AppFacade.setApp(app);
		AppStateManager stateManager = app.getStateManager();
		
		DraggableCamera cam = new DraggableCamera(app.getCamera());
		cam.setRotationSpeed(0.001f);
		cam.setMoveSpeed(1f);
		
		stateManager.attach(cam);
		stateManager.attach(new EntityDataAppState(ed));
		
		EntitySystem es = new EntitySystem(ed);
		stateManager.attach(es);
		es.initVisuals(true);
		es.initAudio(false);
		es.initCommand(false);
		es.initLogic(false);
		return true;
	}

}
