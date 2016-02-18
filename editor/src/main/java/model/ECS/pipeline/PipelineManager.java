package main.java.model.ECS.pipeline;

import com.jme3.app.state.AppStateManager;

import main.java.model.tempImport.AppFacade;

public class PipelineManager {
	public void addPipeline(Pipeline pipeline){
		if(pipeline.isOnRenderThread())
			pipeline.getProcessors().forEach((p)-> AppFacade.getStateManager().attach(p));
		else {
			AppStateManager logicStateManager = new AppStateManager(null);
			pipeline.getProcessors().forEach((p)-> logicStateManager.attach(p));
			Thread thread = new Thread(() -> logicStateManager.update(0.05f));
			
		}
			
		
	}
}
