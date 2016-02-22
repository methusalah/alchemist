package model.ECS.pipeline;

import java.util.HashMap;
import java.util.Map;

import com.jme3.app.state.AppStateManager;

public class Pipeline {
	private final String name;
	final boolean inRendererThread;
	
    private static int millisPerTick = 20;
    private static double secondPerTick = (double)millisPerTick/1000;

    int tickCount = 0;
    int waitTime = 0;

	private final Map<Class<? extends Processor>, Processor> processors = new HashMap<>();
	
	Pipeline(String name, boolean inRendererThread) {
		this.name = name;
		this.inRendererThread = inRendererThread;
	}
	
	public void addProcessor(Processor processor){
		if(processors.containsKey(processor.getClass()))
				throw new RuntimeException("The processor " + processor.getClass().getSimpleName() + " is already attached to this pipeline. A single pipeline can hold only one instance of a processor class");
		processors.put(processor.getClass(), processor);
	}
	
	public void removeProcessor(Class<? extends Processor> processorClass){
		processors.remove(processorClass);
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

	public Map<Class<? extends Processor>, Processor> getProcessors() {
		return processors;
	}

	public String getName() {
		return name;
	}
	
	
}
