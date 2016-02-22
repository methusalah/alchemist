package model.ECS.pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;

import util.LogUtil;

public class Pipeline {
	private final String name;
	private final AppStateManager stateManager;
	private Runnable runnable = null;

    private static int millisPerTick = 20;
    private static double secondPerTick = (double)millisPerTick/1000;

    int tickCount = 0;
    int waitTime = 0;

	private final List<Processor> processors = new ArrayList<>();
	
	Pipeline(String name, AppStateManager stateManager) {
		this.name = name;
		this.stateManager = stateManager;
	}
	
	public void addProcessor(Processor processor){
		if(find(processor.getClass()).isPresent())
				throw new RuntimeException("The processor " + processor.getClass().getSimpleName() + " is already attached to this pipeline. A single pipeline can hold only one instance of a processor class");
		processors.add(processor);
	}
	
	private Optional<Processor> find(Class<? extends Processor> processorClass){
		return processors.stream().filter(p -> p.getClass().equals(processorClass)).findFirst();
	}
	
	public void removeProcessor(Class<? extends Processor> processorClass){
		find(processorClass).ifPresent(p -> processors.remove(p));
	}
	
    public int getTickCount() {
		return tickCount;
	}

	public int getWaitTime() {
		return waitTime;
	}
	
	public void resetIdleStats(){
		tickCount = 0;
		waitTime = 0;
	}
	
	public static int getMillisPerTick() {
		return millisPerTick;
	}

	public static void setMillisPerTick(int millisPerTick) {
		Pipeline.millisPerTick = millisPerTick;
		secondPerTick = (double)millisPerTick/1000;
	}

	public static double getSecondPerTick() {
		return secondPerTick;
	}

	public List<Processor> getProcessors() {
		return processors;
	}

	public String getName() {
		return name;
	}
	
	public AppStateManager getStateManager() {
		return stateManager;
	}

	public Runnable getRunnable() {
		return runnable;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}
}
