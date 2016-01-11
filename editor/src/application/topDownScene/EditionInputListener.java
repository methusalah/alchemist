package application.topDownScene;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityId;

import application.EditorPlatform;
import controller.ECS.SceneSelectorState;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.state.WorldToolState;
import model.world.Tool;
import presenter.EntityNode;
import util.geometry.geom2d.Point2D;

public class EditionInputListener implements SceneInputListener {
	private static enum ActionType {StartPrimary,
		StartSecondary,
		StopPrimary,
		StopSecondary,
		OncePrimary,
		OnceSecondary
	}

	private final JmeForImageView jme;
	
	public EditionInputListener(JmeForImageView jme) {
		this.jme = jme;
	}

	@Override
	public void onMousePressed(MouseEvent e){
		ActionType type;
		switch(e.getButton()){
		case PRIMARY : type = ActionType.StartPrimary; break;
		case SECONDARY : type = ActionType.StartSecondary; break;
		default : type = null;
		}
		if(type != null)
			jme.enqueue(app -> setToolAction(app, type));
	}

	@Override
	public void onMouseMoved(MouseEvent e){
		jme.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	@Override
	public void onMouseReleased(MouseEvent e){
		switch(e.getButton()){
		case PRIMARY :
			jme.enqueue(app -> setToolAction(app, ActionType.StopPrimary));
			jme.enqueue(app -> setToolAction(app, ActionType.OncePrimary));
			break;
		case SECONDARY : 
			jme.enqueue(app -> setToolAction(app, ActionType.StopSecondary));
			jme.enqueue(app -> setToolAction(app, ActionType.OnceSecondary));
			break;
		default:
			break;
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
	
	@Override
	public void onMouseDragged(MouseEvent e) {
	}

	static private boolean setSceneMouseCoord(SimpleApplication app, Point2D coord) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(SceneSelectorState.class).setCoordInScreenSpace(coord);
		return true;
	}

	static private boolean setToolAction(SimpleApplication app, ActionType type) {
		Tool t = app.getStateManager().getState(WorldToolState.class).getTool();
		if(t != null){
			switch(type){
			case OncePrimary : t.onPrimarySingleAction(); break;
			case OnceSecondary : t.onSecondarySingleAction(); break;
			case StartPrimary : t.onPrimaryActionStart(); break;
			case StartSecondary : t.onSecondaryActionStart(); break;
			case StopPrimary : t.onPrimaryActionEnd(); break;
			case StopSecondary : t.onSecondaryActionEnd(); break;
			}
		} else {
			AppStateManager stateManager = app.getStateManager();
			EntityId pointed = stateManager.getState(SceneSelectorState.class).getPointedEntity();
			if(pointed != null){
				EntityNode pointedNode = EditorPlatform.getEntityData().getNode(pointed);
				Platform.runLater(() -> EditorPlatform.getSelectionProperty().set(pointedNode));
			}
		}
		return true;
	}
}
