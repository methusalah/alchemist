package controller.topdown;

import java.util.logging.Logger;

import util.event.AppStateChangeEvent;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import view.EditorView;
import view.TopdownView;
import model.CommandManager;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

import controller.InputInterpreter;
import controller.editor.EditorState;

public class TopdownInputInterpreter extends InputInterpreter {

	private static final Logger logger = Logger.getLogger(TopdownInputInterpreter.class.getName());

	protected final static String SWITCH_CTRL_1 = "ctrl1";
	protected final static String SWITCH_CTRL_2 = "ctrl2";
	protected final static String SWITCH_CTRL_3 = "ctrl3";

	protected final static String SELECT = "select";
	protected final static String ACTION = "action";
	protected final static String MOVE_ATTACK = "moveattack";
	protected final static String MULTIPLE_SELECTION = "multipleselection";
	protected final static String HOLD = "hold";
	protected final static String PAUSE = "pause";

	protected final static int DOUBLE_CLIC_DELAY = 200;// milliseconds
	protected final static int DOUBLE_CLIC_MAX_OFFSET = 5;// in pixels on screen

	boolean multipleSelection = false;
	double dblclicTimer = 0;
	Point2D dblclicCoord;

	TopdownInputInterpreter(TopdownView v) {
		super(v);
		setMappings();
	}

	private void setMappings() {
		mappings = new String[] { SWITCH_CTRL_1, SWITCH_CTRL_2, SWITCH_CTRL_3, SELECT, ACTION, MOVE_ATTACK, MULTIPLE_SELECTION, HOLD, PAUSE };
	}

	@Override
	protected void registerInputs(InputManager inputManager) {
		inputManager.addMapping(SWITCH_CTRL_1, new KeyTrigger(KeyInput.KEY_F1));
		inputManager.addMapping(SWITCH_CTRL_2, new KeyTrigger(KeyInput.KEY_F2));
		inputManager.addMapping(SWITCH_CTRL_3, new KeyTrigger(KeyInput.KEY_F3));
		inputManager.addMapping(SELECT, new MouseButtonTrigger(0));
		inputManager.addMapping(ACTION, new MouseButtonTrigger(1));
		inputManager.addMapping(MOVE_ATTACK, new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping(MULTIPLE_SELECTION, new KeyTrigger(KeyInput.KEY_LCONTROL),
				new KeyTrigger(KeyInput.KEY_RCONTROL));
		inputManager.addMapping(HOLD, new KeyTrigger(KeyInput.KEY_H));
		inputManager.addMapping(PAUSE, new KeyTrigger(KeyInput.KEY_SPACE));

		inputManager.addListener(this, mappings);
	}

	@Override
	protected void unregisterInputs(InputManager inputManager) {
		for (String s : mappings) {
			if (inputManager.hasMapping(s)) {
				inputManager.deleteMapping(s);
			}
		}
		inputManager.removeListener(this);
	}

	@Override
	public void onAnalog(String name, float value, float tpf) {
	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		if (!isPressed) {
			switch (name) {
			case SWITCH_CTRL_1:
				EventManager.post(new AppStateChangeEvent(EditorState.class));
				break;
			case SWITCH_CTRL_2:
				EventManager.post(new AppStateChangeEvent(TopdownState.class));
				break;

				case MULTIPLE_SELECTION:
					CommandManager.setMultipleSelection(false);
					break;
				case SELECT:
					break;
				case ACTION:
					break;
				case MOVE_ATTACK:
					break;
				case HOLD:
					break;
				case PAUSE:
					break;
			}
		} else {
			// input pressed
			switch(name){
				case MULTIPLE_SELECTION:
					break;
				case SELECT:
					break;
			}
		}
	}
}
