package logic.processor.logic.interaction;

import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.Entity;

import component.motion.Touching;

public class TouchingClearingProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Touching.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		removeComp(e, Touching.class);
	}
}
