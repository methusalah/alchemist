package commonLogic;

import model.Command;
import model.state.SceneSelectorState;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

public class CommandState extends AbstractAppState {
	private final Command command;
	private SceneSelectorState sceneSelector;
	
	public CommandState(Command command) {
		this.command = command;
	}
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		sceneSelector = stateManager.getState(SceneSelectorState.class);
	}
	
	@Override
	public void update(float tpf) {
		command.target = sceneSelector.getPointedCoordInPlan();
	}

	public Command getCommand() {
		return command;
	}
}
