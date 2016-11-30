package com.brainless.alchemist.model.ECS.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jme3.app.state.AppStateManager;

/**
 * A pipeline is a list of processors that need to be run in a specific order, in the same thread.
 * Pipelines are created by the PipelineManager that manage their creation process. Their constructor is no publicaly visible.
 * For the moment, pipelines are intended to be created at startup and can't be modified. You put processors in and let the engine
 * plug it in and out.
 * 
 * @author Benoît
 *
 */
public class Pipeline {
	private final String name;
	private final AppStateManager stateManager;
	private final List<Processor> processors = new ArrayList<>();

	private Runnable runnable = null;

    private static int millisPerTick = 20;
    private static double secondPerTick = (double)millisPerTick/1000;

    int tickCount = 0;
    int waitTime = 0;

	
	/**
	 * Protected constructor to allow creation only by package members 
	 * @param name
	 * @param stateManager
	 */
	Pipeline(String name, AppStateManager stateManager) {
		this.name = name;
		this.stateManager = stateManager;
	}

	public void addProcessors(List<Processor> processors){
		processors.forEach(p -> addProcessor(p));
	}
	
	public void addProcessors(Processor...processors){
		for(Processor p : processors)
			addProcessor(p);
	}
	
	public void addProcessor(Processor processor){
		if(find(processor.getClass()).isPresent())
				throw new RuntimeException("The processor " + processor.getClass().getSimpleName() + " is already attached to this pipeline. A single pipeline can hold only one instance of a processor class");
		processors.add(processor);
	}
	
	private Optional<Processor> find(Class<? extends Processor> processorClass){
		return processors.stream().filter(p -> p.getClass().equals(processorClass)).findFirst();
	}
	
	/**
	 * Return the tick count since last call of resetIdleStats
	 * Used to measure the idling
	 * @return
	 */
    public int getTickCount() {
		return tickCount;
	}

	/**
	 * Return the total waited time since last call of resetIdleStats
	 * Used to measure the idling
	 * @return
	 */
	public int getWaitTime() {
		return waitTime;
	}
	
	public void resetIdleStats(){
		tickCount = 0;
		waitTime = 0;
	}
	
	/**
	 * Return the time applied to the processors each tick in milliseconds
	 * @return
	 */
	public static int getMillisPerTick() {
		return millisPerTick;
	}

	/**
	 * Set the time to apply to the processors each tick in milliseconds
	 * @return
	 */
	public static void setMillisPerTick(int millisPerTick) {
		Pipeline.millisPerTick = millisPerTick;
		secondPerTick = (double)millisPerTick/1000;
	}

	/**
	 * Return the time applied to the processors each tick in seconds
	 * @return
	 */
	public static double getSecondPerTick() {
		return secondPerTick;
	}

	public List<Processor> getProcessors() {
		return processors;
	}

	public String getName() {
		return name;
	}
	
	AppStateManager getStateManager() {
		return stateManager;
	}

	Runnable getRunnable() {
		return runnable;
	}

	void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}
}
