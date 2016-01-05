package model.state;

import model.world.Tool;
import model.world.WorldData;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import controller.ECS.DataState;
import controller.ECS.SceneSelectorState;

public class WorldToolState extends AbstractAppState {
	private SceneSelectorState selector;
	private WorldData worldData;
	private Tool actualTool;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		selector = stateManager.getState(SceneSelectorState.class);
		worldData = stateManager.getState(DataState.class).getWorldData();
	}
	
	public void setTool(Tool tool){
		tool.setSelector(selector);
		actualTool = tool;
	}
	
	@Override
	public void update(float tpf) {
		if(actualTool != null){
			actualTool.onUpdate(tpf);
		} else {
		}
	}

	public Tool getTool() {
		return actualTool;
	}
}
