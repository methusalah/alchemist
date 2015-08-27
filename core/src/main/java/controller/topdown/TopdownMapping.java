package controller.topdown;

import app.AppFacade;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

import controller.Mapping;

public class TopdownMapping extends Mapping {
	final static String SWITCH_CTRL_1 = "ctrl1";
	final static String SWITCH_CTRL_2 = "ctrl2";
	final static String SWITCH_CTRL_3 = "ctrl3";

	final static String SELECT = "select";
	final static String THRUST = "action";
	final static String MOVE_ATTACK = "moveattack";
	final static String MULTIPLE_SELECTION = "multipleselection";
	final static String HOLD = "hold";
	final static String PAUSE = "pause";
	
	public TopdownMapping() {
		mapping = new String[] { SWITCH_CTRL_1, SWITCH_CTRL_2, SWITCH_CTRL_3, SELECT, THRUST, MOVE_ATTACK, MULTIPLE_SELECTION, HOLD, PAUSE };
	}

	@Override
	protected void register() {
		AppFacade.getInputManager().addMapping(SWITCH_CTRL_1, new KeyTrigger(KeyInput.KEY_F1));
		AppFacade.getInputManager().addMapping(SWITCH_CTRL_2, new KeyTrigger(KeyInput.KEY_F2));
		AppFacade.getInputManager().addMapping(SWITCH_CTRL_3, new KeyTrigger(KeyInput.KEY_F3));
		AppFacade.getInputManager().addMapping(SELECT, new MouseButtonTrigger(0));
		AppFacade.getInputManager().addMapping(THRUST, new MouseButtonTrigger(1));
		AppFacade.getInputManager().addMapping(MOVE_ATTACK, new KeyTrigger(KeyInput.KEY_A));
		AppFacade.getInputManager().addMapping(MULTIPLE_SELECTION, new KeyTrigger(KeyInput.KEY_LCONTROL),
				new KeyTrigger(KeyInput.KEY_RCONTROL));
		AppFacade.getInputManager().addMapping(HOLD, new KeyTrigger(KeyInput.KEY_H));
		AppFacade.getInputManager().addMapping(PAUSE, new KeyTrigger(KeyInput.KEY_SPACE));
	}
}
