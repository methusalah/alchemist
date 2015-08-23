package controller;

import util.entity.ProcessorPool;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

public class ProcessorAppState extends AbstractAppState {
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
	}
	
	@Override
	public void update(float tpf) {
		ProcessorPool.update(tpf);
	}
	
	@Override
	public void cleanup() {
		super.cleanup();
		ProcessorPool.detachAll();
	}
}
