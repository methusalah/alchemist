package processor.logic.interaction;

import com.simsilica.es.Entity;

import component.lifeCycle.DecayOnTouch;
import component.lifeCycle.ToRemove;
import component.motion.Touching;
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
