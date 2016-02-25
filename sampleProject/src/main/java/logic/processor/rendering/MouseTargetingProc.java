package logic.processor.rendering;

import command.CommandPlatform;
import model.ECS.pipeline.Processor;
import model.state.SceneSelectorState;
import model.tempImport.RendererPlatform;

public class MouseTargetingProc extends Processor {

	@Override
	protected void registerSets() {
	}
	
	@Override
	protected void onUpdated() {
		CommandPlatform.target = RendererPlatform.getStateManager().getState(SceneSelectorState.class).getPointedCoordInPlan();
	}
	

}
