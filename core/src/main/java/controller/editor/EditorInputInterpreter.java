package controller.editor;

import model.ModelManager;
import model.editor.ToolManager;
import util.LogUtil;
import util.event.AppStateChangeEvent;
import util.event.EventManager;
import view.EditorView;

import com.google.inject.Inject;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

import controller.InputInterpreter;
import controller.topdown.TopdownCtrl;

public class EditorInputInterpreter extends InputInterpreter {


	boolean analogUnpressed = false;

	EditorInputInterpreter(EditorView v) {
		super(v, new EditorMapping());
	}

	@Override
	public void onAnalog(String name, float value, float tpf) {
		if (analogUnpressed) {
			ToolManager.releasePencils();
			analogUnpressed = false;
		} else {
			switch (name) {
				case EditorMapping.PRIMARY_ACTION:
					ToolManager.analogPrimaryAction();
					break;
				case EditorMapping.SECONDARY_ACTION:
					ToolManager.analogSecondaryAction();;
					break;
			}
		}
	}

	@Override
	public void onActionPressed(String name, float tpf) {
	}
	
	@Override
	protected void onActionReleased(String name, float tpf) {
		switch (name) {
		case EditorMapping.PRIMARY_ACTION:
			ToolManager.primaryAction();
			analogUnpressed = true;
			break;
		case EditorMapping.SECONDARY_ACTION:
			ToolManager.secondaryAction();
			analogUnpressed = true;
			break;
		case EditorMapping.SWITCH_CTRL_1:
			EventManager.post(new AppStateChangeEvent(EditorCtrl.class));
			break;
		case EditorMapping.SWITCH_CTRL_2:
			EventManager.post(new AppStateChangeEvent(TopdownCtrl.class));
			break;
		case EditorMapping.TOGGLE_PENCIL_SHAPE:
			ToolManager.getActualTool().pencil.toggleShape();
			break;
		case EditorMapping.TOGGLE_PENCIL_MODE:
			ToolManager.getActualTool().pencil.toggleMode();
			break;
		case EditorMapping.INC_SELECTOR_RADIUS:
			ToolManager.getActualTool().pencil.incRadius();
			break;
		case EditorMapping.DEC_SELECTOR_RADIUS:
			ToolManager.getActualTool().pencil.decRadius();
			break;
		case EditorMapping.SET_CLIFF_TOOL:
			ToolManager.setCliffTool();
			break;
		case EditorMapping.SET_HEIGHT_TOOL:
			ToolManager.setHeightTool();
			break;
		case EditorMapping.SET_ATLAS_TOOL:
			ToolManager.setAtlasTool();
			break;
		case EditorMapping.SET_RAMP_TOOL:
			ToolManager.setRampTool();
			break;
		case EditorMapping.SET_UNIT_TOOL:
			ToolManager.setUnitTool();
			break;

		case EditorMapping.TOGGLE_OPERATION:
			ToolManager.toggleOperation();
			break;
		case EditorMapping.TOGGLE_SET:
			ToolManager.toggleSet();
			break;
		case EditorMapping.TOGGLE_GRID:
			getView().editorRend.toggleGrid();
			break;
		case EditorMapping.TOGGLE_LIGHT_COMP:
			ModelManager.getBattlefield().getSunLight().toggleLight();
			break;
		case EditorMapping.TOGGLE_SPEED:
			ModelManager.getBattlefield().getSunLight().toggleSpeed();
			break;
		case EditorMapping.RESET_COLOR:
			ModelManager.getBattlefield().getSunLight().resetColor();
			break;
		case EditorMapping.SAVE:
			ModelManager.saveBattlefield();
			break;
		case EditorMapping.LOAD:
			ModelManager.loadBattlefield();
			break;
		case EditorMapping.NEW:
			ModelManager.setNewBattlefield();
			break;
		case EditorMapping.REPORT:
//			LogUtil.info(EntityPool.toReport());
			break;
	}	}
	
	private EditorView getView(){
		return (EditorView)view;
	}
}
