package model.ES.processor.motion;

import model.ES.component.motion.PlanarDesiredMotion;
import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarMotionCapacity;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlayerOrder;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class PossibleMotionProc extends Processor {
	
	@Override
	protected void registerSets() {
		register(PlanarDesiredMotion.class, PlanarMotionCapacity.class, PlanarInertia.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime){
		PlanarDesiredMotion motion = e.get(PlanarDesiredMotion.class);
		PlanarInertia inertia = e.get(PlanarInertia.class);
		PlanarMotionCapacity capacity = e.get(PlanarMotionCapacity.class);
		MotionAdapter adapter = new MotionAdapter(motion, capacity, inertia);

		adapter.adaptSpeed();
		adapter.adaptRotation();
		
		setComp(e, adapter.getResultingMotion());
		removeComp(e, PlanarDesiredMotion.class);
	}
}
