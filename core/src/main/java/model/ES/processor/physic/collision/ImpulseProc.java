package model.ES.processor.physic.collision;

import model.ES.component.physic.Impulse;
import model.ES.component.physic.Physic;
import model.ES.component.planarMotion.PlanarMotionCapacity;
import model.ES.component.planarMotion.PlanarStance;
import controller.entityAppState.Processor;

public class ImpulseProc extends Processor {

	@Override
	protected void registerSets() {
		register(PlanarStance.class, Impulse.class);
		register(PlanarStance.class, Physic.class, PlanarMotionCapacity.class);
	}

}
