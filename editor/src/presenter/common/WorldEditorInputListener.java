package presenter.common;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;

import controller.ECS.SceneSelectorState;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import presenter.WorldEditorPresenter;
import util.geometry.geom2d.Point2D;

public class WorldEditorInputListener implements SceneInputListener {
	private static enum ActionType {StartPrimary,
		StartSecondary,
		StopPrimary,
		StopSecondary,
		OncePrimary,
		OnceSecondary
	}
	
	private final WorldEditorPresenter presenter;
	
	public WorldEditorInputListener(WorldEditorPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void onMousePressed(MouseEvent e){
		presenter.beginAction();
		if(e.getButton() == MouseButton.PRIMARY)
			presenter.doPrimaryAction();
		else if(e.getButton() == MouseButton.SECONDARY)
			presenter.doSecondaryAction();

//		ActionType type;
//		switch(e.getButton()){
//		case PRIMARY : type = ActionType.StartPrimary; break;
//		case SECONDARY : type = ActionType.StartSecondary; break;
//		default : type = null;
//		}
//		if(type != null)
//			jme.enqueue(app -> setToolAction(app, type));
	}

	@Override
	public void onMouseMoved(MouseEvent e){
		presenter.setNewMousePosition(new Point2D(e.getX(), e.getY()));

//		jme.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	@Override
	public void onMouseReleased(MouseEvent e){
//		switch(e.getButton()){
//		case PRIMARY :
//			jme.enqueue(app -> setToolAction(app, ActionType.StopPrimary));
//			jme.enqueue(app -> setToolAction(app, ActionType.OncePrimary));
//			break;
//		case SECONDARY : 
//			jme.enqueue(app -> setToolAction(app, ActionType.StopSecondary));
//			jme.enqueue(app -> setToolAction(app, ActionType.OnceSecondary));
//			break;
//		default:
//			break;
//		}
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
		presenter.setNewMousePosition(new Point2D(e.getX(), e.getY()));
		if(e.getButton() == MouseButton.PRIMARY)
			presenter.doPrimaryAction();
		else if(e.getButton() == MouseButton.SECONDARY)
			presenter.doSecondaryAction();
	}

	static private boolean setSceneMouseCoord(SimpleApplication app, Point2D coord) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(SceneSelectorState.class).setCoordInScreenSpace(coord);
		return true;
	}

//	static private boolean setToolAction(SimpleApplication app, ActionType type) {
//		Tool t = app.getStateManager().getState(WorldToolState.class).getTool();
//		if(t != null){
//			switch(type){
//			case OncePrimary : t.onPrimarySingleAction(); break;
//			case OnceSecondary : t.onSecondarySingleAction(); break;
//			case StartPrimary : t.onPrimaryActionStart(); break;
//			case StartSecondary : t.onSecondaryActionStart(); break;
//			case StopPrimary : t.onPrimaryActionEnd(); break;
//			case StopSecondary : t.onSecondaryActionEnd(); break;
//			}
//		} else {
//			AppStateManager stateManager = app.getStateManager();
//			EntityId pointed = stateManager.getState(SceneSelectorState.class).getPointedEntity();
//			if(pointed != null){
//				EntityNode pointedNode = EditorPlatform.getEntityData().getNode(pointed);
//				Platform.runLater(() -> EditorPlatform.getSelectionProperty().set(pointedNode));
//			}
//		}
//		return true;
//	}
}
