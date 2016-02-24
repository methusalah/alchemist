package ECS.processor.logic.interaction;

import com.simsilica.es.Entity;

import ECS.component.lifeCycle.DecayOnTouch;
import ECS.component.lifeCycle.ToRemove;
import ECS.component.motion.Touching;
import model.ECS.pipeline.Processor;

public class DestroyedOnTouchProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(DecayOnTouch.class, Touching.class);

	}

	@Override
	protected void onEntityEachTick(Entity e) {
		setComp(e, new ToRemove());
	}

}
