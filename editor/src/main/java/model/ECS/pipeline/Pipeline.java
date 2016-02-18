package main.java.model.ECS.pipeline;

import java.util.ArrayList;
import java.util.List;

public class Pipeline {
	private final boolean onRenderThread;
    private static int millisPerTick = 20;

	private final List<Processor> processors = new ArrayList<>(); 
	
	public Pipeline(boolean onRenderThread) {
		this.onRenderThread = onRenderThread;
	}
	
	public void addProcessor(Processor processor){
		processors.add(processor);
	}

	public boolean isOnRenderThread() {
		return onRenderThread;
	}

	public List<Processor> getProcessors() {
		return processors;
	}
	
	
	
	
	
}
