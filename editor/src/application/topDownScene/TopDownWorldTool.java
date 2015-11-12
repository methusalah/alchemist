package application.topDownScene;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityId;

import application.topDownScene.state.SceneSelectorState;
import application.topDownScene.state.WorldToolState;
import controller.ECS.DataAppState;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.world.EntityInstancierTool;
import model.world.Tool;
import util.LogUtil;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import util.event.scene.ToolChangedEvent;
import util.geometry.geom2d.Point2D;

public class TopDownWorldTool implements SceneInputListener {
	
	private final JmeForImageView jme;
	private boolean hasTool = false;

	public TopDownWorldTool(JmeForImageView jme) {
		this.jme = jme;
		EventManager.register(this);
	}

	
	@Subscribe
	public void onToolChangedEvent(ToolChangedEvent e){
		jme.enqueue(app -> setTool(app, e));
		hasTool = true;
	}
	
	@Override
	public void onMousePressed(MouseEvent e){
		if(hasTool && e.getButton() == MouseButton.PRIMARY)
			jme.enqueue(app -> setToolActionStart(app));
	}

	@Override
	public void onMouseMoved(MouseEvent e){
		//if(hasTool)
			jme.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	@Override
	public void onMouseDragged(MouseEvent e){
	}
	
	@Override
	public void onMouseReleased(MouseEvent e){
		if(hasTool && e.getButton() == MouseButton.PRIMARY){
			jme.enqueue(app -> setToolActionStop(app));
			jme.enqueue(app -> setToolSingleAction(app));
		} else {
			jme.enqueue(app -> selectEntity(app));
		}
	}
	
	@Override
	public void onMouseScroll(ScrollEvent e){
	}

	@Override
	public void onKeyPressed(KeyEvent e){
	}
	
	@Override
	public void onKeyReleased(KeyEvent e){
	}
	
	static private boolean setSceneMouseCoord(SimpleApplication app, Point2D coord) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(SceneSelectorState.class).setCoordInScreenSpace(coord);
		return true;
	}

	static private boolean setToolActionStart(SimpleApplication app) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(WorldToolState.class).setActionStart();
		return true;
	}

	static private boolean setToolActionStop(SimpleApplication app) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(WorldToolState.class).setActionStop();
		return true;
	}
	
	static private boolean setToolSingleAction(SimpleApplication app) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(WorldToolState.class).setSingleAction();
		return true;
	}

	static private boolean setTool(SimpleApplication app, ToolChangedEvent e) {
		AppStateManager stateManager = app.getStateManager();
		Tool t;
		t = new EntityInstancierTool(stateManager.getState(DataAppState.class).getWorldData(), e.getBp());
		stateManager.getState(WorldToolState.class).setTool(t);
		
		return true;
	}

	static private boolean selectEntity(SimpleApplication app) {
		AppStateManager stateManager = app.getStateManager();
		EntityId pointed = stateManager.getState(SceneSelectorState.class).getPointedEntity();
		if(pointed != null)
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					EventManager.post(new EntitySelectionChanged(pointed));
				}
			});
		return true;
	}

}
