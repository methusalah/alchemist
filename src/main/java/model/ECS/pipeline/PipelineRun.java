package model.ECS.pipeline;

import com.jme3.app.state.AppStateManager;

public class PipelineRun {
	private final Pipeline pipeline;
	private final AppStateManager stateManager;
	private final Runnable runnable;

	public PipelineRun(Pipeline pipeline, AppStateManager stateManager, Runnable runnable) {
		this.pipeline = pipeline;
		this.stateManager = stateManager;
		this.runnable = runnable;
	}

	public Pipeline getPipeline() {
		return pipeline;
	}

	public AppStateManager getStateManager() {
		return stateManager;
	}

	public Runnable getRunnable() {
		return runnable;
	}
	
	
}
