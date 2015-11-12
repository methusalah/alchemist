package application.topDownScene.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import controller.ECS.DataAppState;
import model.world.Tool;
import model.world.WorldData;

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
	
	public void setSingleAction(){
		actualTool.onSingleAction();
	}
	
	public void setActionStart(){
		actualTool.onActionStart();
	}
	
	public void setActionStop(){
		actualTool.onActionEnd();
	}
	
	@Override
	public void update(float tpf) {
		if(actualTool != null)
			actualTool.setCoord(selector.getPointedCoordInPlan());
	}
	
	
}
