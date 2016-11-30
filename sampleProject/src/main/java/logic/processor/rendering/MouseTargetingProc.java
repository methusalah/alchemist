package logic.processor.rendering;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.brainless.alchemist.model.state.SceneSelectorState;
import com.brainless.alchemist.model.tempImport.RendererPlatform;

import command.CommandPlatform;

public class MouseTargetingProc extends BaseProcessor {

	@Override
	protected void registerSets() {
	}
	
	@Override
	protected void onUpdated() {
		CommandPlatform.target = RendererPlatform.getStateManager().getState(SceneSelectorState.class).getPointedCoordInPlan();
	}
	

}
