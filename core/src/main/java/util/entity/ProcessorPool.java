package util.entity;

import java.util.HashMap;
import java.util.Map;

public class ProcessorPool {
	
	private static Map<Class<? extends Processor>, Processor> processors = new HashMap<>();
	private static Map<Class<? extends Processor>, Processor> enabledProcessors = new HashMap<>();

	private ProcessorPool(){
		
	}
	
	public static void attach(Class<? extends Processor> processorClass){
		if(processors.containsKey(processorClass))
			throw new RuntimeException(processorClass.getSimpleName()+" is already attached.");
		Processor s;
		try {
			s = processorClass.newInstance();
			processors.put(processorClass, s);
			s.setAttached();
			setEnabled(processorClass, true);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void detach(Class<? extends Processor> processorClass){
		if(!processors.containsKey(processorClass))
			throw new RuntimeException(processorClass.getSimpleName()+" is not attached.");
		setEnabled(processorClass, false);
		processors.get(processorClass).setDetached();
		processors.remove(processorClass);
	}
	
	public static void detachAll(){
		for(Processor p : processors.values()){
			setEnabled(p.getClass(), false);
			detach(p.getClass());
		}
	}
	
	public static void setEnabled(Class<? extends Processor> processorClass, boolean enable){
		if(!processors.containsKey(processorClass))
			throw new RuntimeException(processorClass.getSimpleName()+" is not attached.");
		Processor s = processors.get(processorClass); 
		if(enable){
			s.enable();
			enabledProcessors.put(processorClass, s);
		} else {
			s.disable();
			enabledProcessors.remove(processorClass);
		}
	}
	
	public static void update(float elapsedTime){
		for(Processor s : enabledProcessors.values())
			s.update(elapsedTime);
	}
	
	
	
	
	
	
}
