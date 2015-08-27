package controller.topdown;

import java.util.logging.Logger;

import util.LogUtil;
import util.event.AppStateChangeEvent;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import view.EditorView;
import view.TopdownView;
import model.CommandManager;
import model.ModelManager;
import app.AppFacade;
import app.CosmoVania;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

import controller.InputInterpreter;
import controller.editor.EditorCtrl;

public class TopdownInputInterpreter extends InputInterpreter {

	TopdownInputInterpreter(TopdownView v) {
		super(v, new TopdownMapping());
	}

	@Override
	public void onAnalog(String name, float value, float tpf) {
		switch(name){
		}
	}

	@Override
	protected void onActionPressed(String name, float tpf) {
		switch(name){
		case TopdownMapping.THRUST:
			ModelManager.command.thrust = true;
		}
	}

	@Override
	protected void onActionReleased(String name, float tpf) {
		switch (name) {
		case TopdownMapping.SWITCH_CTRL_1:
			EventManager.post(new AppStateChangeEvent(EditorCtrl.class));
			break;
		case TopdownMapping.SWITCH_CTRL_2:
			EventManager.post(new AppStateChangeEvent(TopdownCtrl.class));
			break;

		case TopdownMapping.MULTIPLE_SELECTION:
			CommandManager.setMultipleSelection(false);
			break;
		case TopdownMapping.SELECT:
			break;
		case TopdownMapping.THRUST:
			ModelManager.command.thrust = false;
			break;
		case TopdownMapping.MOVE_ATTACK:
			AppFacade.getApp().toggleProcessorTrace();
			break;
		case TopdownMapping.HOLD:
			break;
		case TopdownMapping.PAUSE:
			break;
		}
	}
}
