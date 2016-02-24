package ECS.processor.logic.interaction;

import com.simsilica.es.Entity;

import ECS.component.motion.Touching;
import model.ECS.pipeline.Processor;

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
