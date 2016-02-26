package logic.processor.logic.interaction;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import component.motion.Touching;

public class TouchingClearingProc extends BaseProcessor {

	@Override
	protected void registerSets() {
		registerDefault(Touching.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		removeComp(e, Touching.class);
	}
}
