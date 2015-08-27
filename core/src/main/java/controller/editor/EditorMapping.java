package controller.editor;

import app.AppFacade;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

import controller.Mapping;

public class EditorMapping extends Mapping {
	protected final static String SWITCH_CTRL_1 = "ctrl1";
	protected final static String SWITCH_CTRL_2 = "ctrl2";
	protected final static String SWITCH_CTRL_3 = "ctrl3";

	protected final static String PRIMARY_ACTION = "lc";
	protected final static String SECONDARY_ACTION = "rc";
	protected final static String TOGGLE_GRID = "GridDisplay";
	protected final static String TOGGLE_SOWER = "togglesower";
	protected final static String STEP_SOWER = "stepsower";

	protected final static String SET_CLIFF_TOOL = "setclifftool";
	protected final static String SET_HEIGHT_TOOL = "setheighttool";
	protected final static String SET_ATLAS_TOOL = "setatlastool";
	protected final static String SET_RAMP_TOOL = "setramptool";
	protected final static String SET_UNIT_TOOL = "setunittool";

	protected final static String TOGGLE_PENCIL_SHAPE = "pencilshape";
	protected final static String TOGGLE_PENCIL_MODE = "pencilmode";
	protected final static String INC_SELECTOR_RADIUS = "selectorradius+";
	protected final static String DEC_SELECTOR_RADIUS = "selectorradius-";

	protected final static String TOGGLE_OPERATION = "toggleoperation";
	protected final static String TOGGLE_SET = "toggleset";

	protected final static String INC_AIRBRUSH_FALLOF = "incairbrushfallof";
	protected final static String DEC_AIRBRUSH_FALLOF = "decairbrushfallof";

	protected final static String TOGGLE_LIGHT_COMP = "togglelightcomp";
	protected final static String INC_DAYTIME = "incdaytime";
	protected final static String DEC_DAYTIME = "decdaytime";
	protected final static String COMPASS_EAST = "compasseast";
	protected final static String COMPASS_WEST = "compasswest";
	protected final static String INC_INTENSITY = "incintensity";
	protected final static String DEC_INTENSITY = "decintensity";
	protected final static String TOGGLE_SPEED = "togglespeed";
	protected final static String DEC_RED = "decred";
	protected final static String DEC_GREEN = "decgreen";
	protected final static String DEC_BLUE = "decblue";
	protected final static String RESET_COLOR = "resetcolor";

	protected final static String SAVE = "save";
	protected final static String LOAD = "load";
	protected final static String NEW = "new";
	protected final static String REPORT = "report";
	
	public EditorMapping() {
		mapping = new String[] { SWITCH_CTRL_1, SWITCH_CTRL_2, SWITCH_CTRL_3,

				PRIMARY_ACTION, SECONDARY_ACTION, TOGGLE_PENCIL_SHAPE, TOGGLE_PENCIL_MODE, INC_SELECTOR_RADIUS, DEC_SELECTOR_RADIUS, SET_CLIFF_TOOL, SET_HEIGHT_TOOL,
				SET_ATLAS_TOOL, SET_RAMP_TOOL, SET_UNIT_TOOL,

				TOGGLE_GRID, TOGGLE_SOWER, STEP_SOWER, TOGGLE_SET, TOGGLE_OPERATION, INC_AIRBRUSH_FALLOF, DEC_AIRBRUSH_FALLOF,

				TOGGLE_LIGHT_COMP, INC_DAYTIME, DEC_DAYTIME, COMPASS_EAST, COMPASS_WEST, INC_INTENSITY, DEC_INTENSITY, TOGGLE_SPEED, DEC_RED, DEC_GREEN,
				DEC_BLUE, RESET_COLOR, SAVE, LOAD, NEW, REPORT};
	}

	@Override
	protected void register() {
		AppFacade.getInputManager().addMapping(SWITCH_CTRL_1, new KeyTrigger(KeyInput.KEY_F1));
		AppFacade.getInputManager().addMapping(SWITCH_CTRL_2, new KeyTrigger(KeyInput.KEY_F2));
		AppFacade.getInputManager().addMapping(SWITCH_CTRL_3, new KeyTrigger(KeyInput.KEY_F3));

		AppFacade.getInputManager().addMapping(PRIMARY_ACTION, new MouseButtonTrigger(0));
		AppFacade.getInputManager().addMapping(SECONDARY_ACTION, new MouseButtonTrigger(1));

		AppFacade.getInputManager().addMapping(TOGGLE_PENCIL_SHAPE, new KeyTrigger(KeyInput.KEY_A));
		AppFacade.getInputManager().addMapping(TOGGLE_PENCIL_MODE, new KeyTrigger(KeyInput.KEY_Z));
		AppFacade.getInputManager().addMapping(INC_SELECTOR_RADIUS, new KeyTrigger(KeyInput.KEY_Q));
		AppFacade.getInputManager().addMapping(DEC_SELECTOR_RADIUS, new KeyTrigger(KeyInput.KEY_W));
		AppFacade.getInputManager().addMapping(SET_CLIFF_TOOL, new KeyTrigger(KeyInput.KEY_1));
		AppFacade.getInputManager().addMapping(SET_HEIGHT_TOOL, new KeyTrigger(KeyInput.KEY_2));
		AppFacade.getInputManager().addMapping(SET_ATLAS_TOOL, new KeyTrigger(KeyInput.KEY_3));
		AppFacade.getInputManager().addMapping(SET_RAMP_TOOL, new KeyTrigger(KeyInput.KEY_4));
		AppFacade.getInputManager().addMapping(SET_UNIT_TOOL, new KeyTrigger(KeyInput.KEY_5));

		AppFacade.getInputManager().addMapping(TOGGLE_GRID, new KeyTrigger(KeyInput.KEY_G));
		AppFacade.getInputManager().addMapping(TOGGLE_SOWER, new KeyTrigger(KeyInput.KEY_H));
		AppFacade.getInputManager().addMapping(STEP_SOWER, new KeyTrigger(KeyInput.KEY_B));
		AppFacade.getInputManager().addMapping(TOGGLE_OPERATION, new KeyTrigger(KeyInput.KEY_E));
		AppFacade.getInputManager().addMapping(TOGGLE_SET, new KeyTrigger(KeyInput.KEY_D));

		AppFacade.getInputManager().addMapping(TOGGLE_LIGHT_COMP, new KeyTrigger(KeyInput.KEY_NUMPAD7));
		AppFacade.getInputManager().addMapping(INC_DAYTIME, new KeyTrigger(KeyInput.KEY_NUMPAD8));
		AppFacade.getInputManager().addMapping(DEC_DAYTIME, new KeyTrigger(KeyInput.KEY_NUMPAD5));
		AppFacade.getInputManager().addMapping(COMPASS_EAST, new KeyTrigger(KeyInput.KEY_NUMPAD6));
		AppFacade.getInputManager().addMapping(COMPASS_WEST, new KeyTrigger(KeyInput.KEY_NUMPAD4));
		AppFacade.getInputManager().addMapping(INC_INTENSITY, new KeyTrigger(KeyInput.KEY_ADD));
		AppFacade.getInputManager().addMapping(DEC_INTENSITY, new KeyTrigger(KeyInput.KEY_SUBTRACT));
		AppFacade.getInputManager().addMapping(TOGGLE_SPEED, new KeyTrigger(KeyInput.KEY_NUMPAD9));
		AppFacade.getInputManager().addMapping(DEC_RED, new KeyTrigger(KeyInput.KEY_NUMPAD1));
		AppFacade.getInputManager().addMapping(DEC_GREEN, new KeyTrigger(KeyInput.KEY_NUMPAD2));
		AppFacade.getInputManager().addMapping(DEC_BLUE, new KeyTrigger(KeyInput.KEY_NUMPAD3));
		AppFacade.getInputManager().addMapping(RESET_COLOR, new KeyTrigger(KeyInput.KEY_NUMPAD0));
		AppFacade.getInputManager().addMapping(SAVE, new KeyTrigger(KeyInput.KEY_F5));
		AppFacade.getInputManager().addMapping(LOAD, new KeyTrigger(KeyInput.KEY_F9));
		AppFacade.getInputManager().addMapping(NEW, new KeyTrigger(KeyInput.KEY_F12));
		AppFacade.getInputManager().addMapping(REPORT, new KeyTrigger(KeyInput.KEY_SPACE));
	}
}
