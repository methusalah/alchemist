package command;

import java.util.ArrayList;
import java.util.List;

import com.brainless.alchemist.model.state.DataState;
import com.brainless.alchemist.model.state.SceneSelectorState;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.view.common.SceneInputListener;
import com.brainless.alchemist.view.tab.scene.customControl.JmeImageView;
import com.jme3.app.SimpleApplication;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import util.geometry.geom2d.Point2D;

public class GameInputListener implements SceneInputListener {
	private static enum ActionType {StartPrimary,
		StartSecondary,
		StopPrimary,
		StopSecondary,
		OncePrimary,
		OnceSecondary
	}

	private final List<KeyCode> pressed = new ArrayList<KeyCode>();
	
	@Override
	public void onMousePressed(MouseEvent e){
		ActionType type;
		switch(e.getButton()){
		case PRIMARY :
			type = ActionType.StartPrimary;
			break;
		case SECONDARY :
			type = ActionType.StartSecondary;
			break;
		default :
			type = null;
		}
		if(type != null)
			RendererPlatform.enqueue(app -> setAction(app, type));
	}

	@Override
	public void onMouseMoved(MouseEvent e){
		RendererPlatform.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	@Override
	public void onMouseReleased(MouseEvent e){
		switch(e.getButton()){
		case PRIMARY :
			RendererPlatform.enqueue(app -> setAction(app, ActionType.StopPrimary));
			RendererPlatform.enqueue(app -> setAction(app, ActionType.OncePrimary));
			break;
		case SECONDARY : 
			RendererPlatform.enqueue(app -> setAction(app, ActionType.StopSecondary));
			RendererPlatform.enqueue(app -> setAction(app, ActionType.OnceSecondary));
			break;
		default:
		}
	}
	
	@Override
	public void onMouseScroll(ScrollEvent e){
	}

	@Override
	public void onKeyPressed(KeyEvent e){
		if(isPressed(e.getCode()))
			return;
		else
			setPressed(e.getCode(), true);
		
		switch(e.getCode()){
		case Z : RendererPlatform.enqueue((app) -> addThrust(app, 1, 0)); break;
		case S : RendererPlatform.enqueue((app) -> addThrust(app, -1, 0)); break;
		case Q : RendererPlatform.enqueue((app) -> addThrust(app, 0, 1)); break;
		case D : RendererPlatform.enqueue((app) -> addThrust(app, 0, -1)); break;
		default:
			break;
		}
	}
	
	@Override
	public void onKeyReleased(KeyEvent e){
		setPressed(e.getCode(), false);
		
		switch(e.getCode()){
		case Z : RendererPlatform.enqueue((app) -> addThrust(app, -1, 0)); break;
		case S : RendererPlatform.enqueue((app) -> addThrust(app, 1, 0)); break;
		case Q : RendererPlatform.enqueue((app) -> addThrust(app, 0, -1)); break;
		case D : RendererPlatform.enqueue((app) -> addThrust(app, 0, 1)); break;
		default:
			break;
		}
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		RendererPlatform.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	static private boolean setSceneMouseCoord(SimpleApplication app, Point2D coord) {
		SceneSelectorState sceneSelector = app.getStateManager().getState(SceneSelectorState.class);
		sceneSelector.setCoordInScreenSpace(coord);

		CommandPlatform.target = sceneSelector.getPointedCoordInPlan();
		return true;
	}

	static private boolean addThrust(SimpleApplication app, double x, double y) {
		CommandPlatform.thrust = CommandPlatform.thrust.getAddition(new Point2D(x, y));
		return true;
	}
	
	static private boolean setAction(SimpleApplication app, ActionType type) {
		switch(type){
		case OncePrimary : break;
		case OnceSecondary : break;
		case StartPrimary : CommandPlatform.abilities.add("gun"); break;
		case StartSecondary : CommandPlatform.abilities.add("boost"); break;
		case StopPrimary : 
			for(int i = 0; i < CommandPlatform.abilities.size(); i++)
				if(CommandPlatform.abilities.get(i).equals("gun")){
					CommandPlatform.abilities.remove(i);
				}
			break;
		case StopSecondary :
			for(int i = 0; i < CommandPlatform.abilities.size(); i++)
				if(CommandPlatform.abilities.get(i).equals("boost")){
					CommandPlatform.abilities.remove(i);
				}
			break;
		}
		return true;
	}
	
	private boolean isPressed(KeyCode keycode){
		return pressed.contains(keycode);
	}
	
	private void setPressed(KeyCode keycode, boolean value){
		if(value)
			pressed.add(keycode);
		else
			pressed.remove(keycode);
	}
}
