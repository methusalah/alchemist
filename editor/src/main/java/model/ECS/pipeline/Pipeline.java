package main.java.model.ECS.pipeline;

import java.util.HashMap;
import java.util.Map;

import com.jme3.app.state.AppStateManager;

public class Pipeline {
	private final AppStateManager manager;
	private final String name;
	
    private static int millisPerTick = 20;
    private static double secondPerTick = (double)millisPerTick/1000;

    int tickCount = 0;
    int waitTime = 0;

	private final Map<Class<? extends Processor>, Processor> processors = new HashMap<>();
	
	Pipeline(AppStateManager manager, String name) {
		this.name = name;
		this.manager = manager;
	}
	
	public void addProcessor(Processor processor){
		if(processors.containsKey(processor.getClass()))
				throw new RuntimeException("The processor " + processor.getClass().getSimpleName() + " is already attached to this pipeline. A single pipeline can hold only one instance of a processor class");
		processors.put(processor.getClass(), processor);
		manager.attach(processor);
	}
	
	public void removeProcessor(Class<? extends Processor> processorClass){
		processors.remove(processorClass);
		manager.detach(manager.getState(processorClass));
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
}
