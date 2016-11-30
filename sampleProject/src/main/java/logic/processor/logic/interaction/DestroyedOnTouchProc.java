package logic.processor.logic.interaction;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import component.lifeCycle.DecayOnTouch;
import component.lifeCycle.ToRemove;
import component.motion.Touching;

public class DestroyedOnTouchProc extends BaseProcessor {

	@Override
	protected void registerSets() {
		registerDefault(DecayOnTouch.class, Touching.class);

	}

	@Override
	protected void onEntityEachTick(Entity e) {
		setComp(e, new ToRemove());
	}

}
