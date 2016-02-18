package controller.topdown;

import util.LogUtil;
import util.event.AppStateChangeEvent;
import util.event.EventManager;
import view.TopdownView;
import controller.InputInterpreter;
import model.tempImport.Command;

public class TopdownInputInterpreter extends InputInterpreter {

	private Command command; 
	
	TopdownInputInterpreter(TopdownView v) {
		super(v, new TopdownMapping());
	}

	public void setCommand(Command command) {
		this.command = command;
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
			command.thrust = command.thrust.getAddition(1, 0);
			break;
		case TopdownMapping.BACKWARD:
			command.thrust = command.thrust.getAddition(-1, 0);
			break;
		case TopdownMapping.STRAFFE_LEFT:
			command.thrust = command.thrust.getAddition(0, 1);
			break;
		case TopdownMapping.STRAFFE_RIGHT:
			command.thrust = command.thrust.getAddition(0, -1);
			break;
		case TopdownMapping.PRIMARY:
			command.abilities.add("gun");
			break;
		case TopdownMapping.SECONDARY:
			command.abilities.add("boost");
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
			for(int i = 0; i<command.abilities.size(); i++)
				if(command.abilities.get(i).equals("gun")){
					command.abilities.remove(i);
				}
			break;
		case TopdownMapping.SECONDARY:
			for(int i = 0; i<command.abilities.size(); i++)
				if(command.abilities.get(i).equals("boost")){
					command.abilities.remove(i);
				}
			break;
		case TopdownMapping.SELECT:
			break;
		case TopdownMapping.FORWARD:
			command.thrust = command.thrust.getAddition(-1, 0);
			break;
		case TopdownMapping.BACKWARD:
			command.thrust = command.thrust.getAddition(1, 0);
			break;
		case TopdownMapping.STRAFFE_LEFT:
			command.thrust = command.thrust.getAddition(0, -1);
			break;
		case TopdownMapping.STRAFFE_RIGHT:
			command.thrust = command.thrust.getAddition(0, 1);
			break;
		case TopdownMapping.HOLD:
			break;
		case TopdownMapping.PAUSE:
			break;
		}
	}
}
