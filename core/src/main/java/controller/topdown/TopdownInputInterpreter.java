package controller.topdown;

import model.ModelManager;
import util.LogUtil;
import util.event.AppStateChangeEvent;
import util.event.EventManager;
import view.TopdownView;
import controller.InputInterpreter;

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
		case TopdownMapping.FORWARD:
			ModelManager.command.thrust = ModelManager.command.thrust.getAddition(1, 0);
			break;
		case TopdownMapping.BACKWARD:
			ModelManager.command.thrust = ModelManager.command.thrust.getAddition(-1, 0);
			break;
		case TopdownMapping.STRAFFE_LEFT:
			ModelManager.command.thrust = ModelManager.command.thrust.getAddition(0, 1);
			break;
		case TopdownMapping.STRAFFE_RIGHT:
			ModelManager.command.thrust = ModelManager.command.thrust.getAddition(0, -1);
			break;
		case TopdownMapping.PRIMARY:
			ModelManager.command.abilities.add("gun");
			break;
		case TopdownMapping.SECONDARY:
			ModelManager.command.abilities.add("boost");
			break;
		}
	}

	@Override
	protected void onActionReleased(String name, float tpf) {
		switch (name) {
		case TopdownMapping.SWITCH_CTRL_1:
			break;
		case TopdownMapping.SWITCH_CTRL_2:
			EventManager.post(new AppStateChangeEvent(TopdownCtrl.class));
			break;

		case TopdownMapping.PRIMARY:
			for(int i = 0; i<ModelManager.command.abilities.size(); i++)
				if(ModelManager.command.abilities.get(i).equals("gun")){
					ModelManager.command.abilities.remove(i);
				}
			break;
		case TopdownMapping.SECONDARY:
			for(int i = 0; i<ModelManager.command.abilities.size(); i++)
				if(ModelManager.command.abilities.get(i).equals("boost")){
					ModelManager.command.abilities.remove(i);
				}
			break;
		case TopdownMapping.SELECT:
			break;
		case TopdownMapping.FORWARD:
			ModelManager.command.thrust = ModelManager.command.thrust.getAddition(-1, 0);
			break;
		case TopdownMapping.BACKWARD:
			ModelManager.command.thrust = ModelManager.command.thrust.getAddition(1, 0);
			break;
		case TopdownMapping.STRAFFE_LEFT:
			ModelManager.command.thrust = ModelManager.command.thrust.getAddition(0, -1);
			break;
		case TopdownMapping.STRAFFE_RIGHT:
			ModelManager.command.thrust = ModelManager.command.thrust.getAddition(0, 1);
			break;
		case TopdownMapping.HOLD:
			break;
		case TopdownMapping.PAUSE:
			break;
		}
	}
}
