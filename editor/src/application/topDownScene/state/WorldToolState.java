package application.topDownScene.state;

import model.world.Tool;
import model.world.WorldData;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import controller.ECS.DataAppState;

public class WorldToolState extends AbstractAppState {
	private SceneSelectorState selector;
	private WorldData worldData;
	private Tool actualTool;

	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		selector = stateManager.getState(SceneSelectorState.class);
		worldData = stateManager.getState(DataAppState.class).getWorldData();
	}
	
	public void setTool(Tool tool){
		actualTool = tool;
	}
	
	@Override
	public void update(float tpf) {
		if(actualTool != null){
			actualTool.setCoord(selector.getPointedCoordInPlan());
			actualTool.onUpdate(tpf);
		}
	}

	public Tool getTool() {
		return actualTool;
	}
}
