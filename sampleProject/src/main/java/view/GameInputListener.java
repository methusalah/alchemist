package view;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.SimpleApplication;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.state.SceneSelectorState;
import view.common.SceneInputListener;
import view.tab.scene.customControl.JmeImageView;

public class GameInputListener implements SceneInputListener {
	private static enum ActionType {StartPrimary,
		StartSecondary,
		StopPrimary,
		StopSecondary,
		OncePrimary,
		OnceSecondary
	}

	private final List<KeyCode> pressed = new ArrayList<KeyCode>();
	private final JmeImageView jme;
	
	public GameInputListener(JmeImageView jme) {
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
			jme.enqueue(app -> setAction(app, type));
	}

	@Override
	public void onMouseMoved(MouseEvent e){
		jme.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	@Override
	public void onMouseReleased(MouseEvent e){
		switch(e.getButton()){
		case PRIMARY :
			jme.enqueue(app -> setAction(app, ActionType.StopPrimary));
			jme.enqueue(app -> setAction(app, ActionType.OncePrimary));
			break;
		case SECONDARY : 
			jme.enqueue(app -> setAction(app, ActionType.StopSecondary));
			jme.enqueue(app -> setAction(app, ActionType.OnceSecondary));
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
		if(isPressed(e.getCode()))
			return;
		else
			setPressed(e.getCode(), true);
		
		switch(e.getCode()){
		case Z : jme.enqueue((app) -> addThrust(app, 1, 0)); break;
		case S : jme.enqueue((app) -> addThrust(app, -1, 0)); break;
		case Q : jme.enqueue((app) -> addThrust(app, 0, 1)); break;
		case D : jme.enqueue((app) -> addThrust(app, 0, -1)); break;
		default:
			break;
		}
	}
	
	@Override
	public void onKeyReleased(KeyEvent e){
		setPressed(e.getCode(), false);
		
		switch(e.getCode()){
		case Z : jme.enqueue((app) -> addThrust(app, -1, 0)); break;
		case S : jme.enqueue((app) -> addThrust(app, 1, 0)); break;
		case Q : jme.enqueue((app) -> addThrust(app, 0, -1)); break;
		case D : jme.enqueue((app) -> addThrust(app, 0, 1)); break;
		default:
			break;
		}
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		jme.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	static private boolean setSceneMouseCoord(SimpleApplication app, Point2D coord) {
		SceneSelectorState sceneSelector = app.getStateManager().getState(SceneSelectorState.class);
		sceneSelector.setCoordInScreenSpace(coord);

		Command c = app.getStateManager().getState(DataState.class).getCommand();
		c.target = sceneSelector.getPointedCoordInPlan();
		return true;
	}

	static private boolean addThrust(SimpleApplication app, double x, double y) {
		Command c = app.getStateManager().getState(DataState.class).getCommand();
		c.thrust = c.thrust.getAddition(new Point2D(x, y));
		return true;
	}
	
	static private boolean setAction(SimpleApplication app, ActionType type) {
		Command c = app.getStateManager().getState(DataState.class).getCommand();
		switch(type){
		case OncePrimary : break;
		case OnceSecondary : break;
		case StartPrimary : c.abilities.add("gun"); break;
		case StartSecondary : c.abilities.add("boost"); break;
		case StopPrimary : 
			for(int i = 0; i < c.abilities.size(); i++)
				if(c.abilities.get(i).equals("gun")){
					c.abilities.remove(i);
				}
			break;
		case StopSecondary :
			for(int i = 0; i < c.abilities.size(); i++)
				if(c.abilities.get(i).equals("boost")){
					c.abilities.remove(i);
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
